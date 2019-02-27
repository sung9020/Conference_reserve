
var main = {
    setReservation : function () {
        var request =  new Object();
        request.conferenceRoomName = $("#conferenceRoom").val();
        request.user = $("#user").val();
        request.reserveDate = $("#reserveDate").val();
        request.startTime = $("startTime").val();
        request.endTime = $("endTime").val();
        request.type = $(":radio[name='conferenceType']:checked").val();
        request.repeatCount = Number($("#repeatCount").val());

        $.ajax({
            type: 'PUT',
            url: '/reservation',
            contentType : "application/json",
            data: requestData,
            success: function (data) {
                alert(data.msg);
            },
            error: function (request,status, error) {
                alert(error);
            }
        })

    },
    getReservation : function () {
        var requestData =  new Object();


        $.ajax({
            type: 'POST',
            url: '/reservation',
            contentType : "application/json",
            data: requestData,
            success: function (data) {
                window.meta = data.meta;
                window.documents = data.documents;

                var source = $('#list-template').html();
                var template = Handlebars.compile(source);
                var html = template(data);
                $('tbody').empty().append(html);

            },
            error: function (request,status, error) {
                alert(error);
            }
        })

    }
}