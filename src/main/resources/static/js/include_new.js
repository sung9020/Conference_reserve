


var main = {
    setReservation : function () {
        var conferenceInfo = {
            conferenceRoomName : $("#conferenceRoom").val(),
            user : $("#user").val(),
            reserveDate : $("#reserveDate").val(),
            startTime : $("#startTime").val(),
            endTime : $("#endTime").val(),
            conferenceType : $(":radio[name='conferenceType']:checked").val(),
            repeatCount : Number($("#repeatCount").val())
        }

        if (util.vaildReservationData(conferenceInfo)){
            var conferenceInfoToJson = JSON.stringify(conferenceInfo);

            $.ajax({
                type: 'PUT',
                url: '/reservation',
                contentType : "application/json",
                data: conferenceInfoToJson,
                success: function (data) {
                    alert(data.msg);
                    main.getReservation();
                },
                error: function (request,status, error) {
                    alert(error);
                }
            })
            return true;
        }else{
            return false;
        }
    },
    getReservation : function () {
        var requestDate =  new Object();

        requestDate.reserveDate = $("#requestDate").val();

        if (!util.isEmpty(requestDate.reserveDate)){
            var requestDateToJson = JSON.stringify(requestDate);
            $.ajax({
                type: 'POST',
                url: '/reservation',
                contentType : "application/json",
                data: requestDateToJson,
                success: function (data) {
                    //alert(data.msg);
                    main.initTable();
                    main.setDataToTable(data.reservationList);
                },
                error: function (request,status, error) {
                    alert(error);
                }
            })
        }else{
            alert("예약 날짜를 반드시 입력해주세요.");
        }

    },
    setDataToTable : function (data) {

        $.each(data, function (index, value) {

            var startTime = value.startTime.slice(0,5);
            var endTime = value.endTime.slice(0,5);

            var rowIndexforStartTime = 0;
            var rowIndexforEndTime = 0;
            var colIndex = 0;

            $('#reservationTable tbody > tr > th' ).each(function (index, row) {
                if (row.innerText == startTime){
                    rowIndexforStartTime = row.parentNode.rowIndex;

                }
                if (row.innerText == endTime){
                    rowIndexforEndTime = row.parentNode.rowIndex;

                }
            });

            $('#reservationTable thead > tr > th').each(function (index, col) {
                if (col.innerText ==  value.conferenceRoomName){
                    colIndex = col.cellIndex;
                    return false;
                }
            });

            var color =  util.getRandomColor();

            for (var currntIndex = rowIndexforStartTime; currntIndex < rowIndexforEndTime; currntIndex++ ){

                var findRow = $("#reservationTable tbody").find("tr:eq("+ (currntIndex - 1) +")").find("td:eq(" + (colIndex - 1) + ")");

                if (currntIndex == rowIndexforStartTime ){
                    if(value.conferenceType == 1){
                        findRow.html(
                            "(정기회의)" + "<br>"
                            +value.user + "<br>"
                            + startTime + " ~ "
                            + endTime
                        )
                    }else{
                        findRow.html(
                            value.user + "<br>"
                            + startTime + " ~ "
                            + endTime
                        )
                    }
                    findRow.css('background-color', color);
                }else{
                    findRow.css('background-color', color);
                }
            }
        });

    },
    initTable : function () {
        $('#reservationTbody').empty();
        var source = $('#time-template').html();
        var template = Handlebars.compile(source);
        var html = template();
        $('#reservationTbody').append(html);
}

}

var util = {
    vaildReservationData : function (conferenceInfo) {
        if ( util.isEmpty(conferenceInfo.conferenceRoomName) ) {
            alert("회의실을 반드시 선택해주세요.");
            return false;
        }else if (util.isEmpty(conferenceInfo.user)) {
            alert("반드시 예약자를 입력해주세요.");
            return false;
        }else if (util.isEmpty(conferenceInfo.reserveDate)) {
            alert("반드시 예약 일자를 입력해주세요.");
            return false;
        }else if (util.isEmpty(conferenceInfo.startTime)) {
            alert("반드시 시작 시간을 입력해주세요.");
            return false;
        }else if (util.isEmpty(conferenceInfo.endTime)) {
            alert("반드시 종료 시간을 입력해주세요.");
            return false;
        }else if (conferenceInfo.conferenceType == 1){
            if (conferenceInfo.repeatCount <= 0){
                alert("정기 회의 일시에는 반드시 반복횟수를 입력해주세요.")
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    },
    isEmpty : function (str) {
        function isEmpty(str){

            if(typeof str == undefined ||str == null || str == "")
                return true;
            else
                return false ;
        }
    },
    // get pastel color
    getRandomColor : function () {
        var r = (Math.round(Math.random() * 127) + 127).toString(16);
        var g = (Math.round(Math.random() * 127) + 127).toString(16);
        var b = (Math.round(Math.random() * 127) + 127).toString(16);
        return '#' + r + g + b;
    }


}
