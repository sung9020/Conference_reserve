
var main = {
    search : function () {
        var jsonParam =  main.makeParam();

        $.ajax({
            type: 'POST',
            url: '/search',
            contentType : "application/json",
            data: jsonParam,
            success: function (data) {
                window.meta = data.meta;
                window.documents = data.documents;

                var source = $('#list-template').html();
                var template = Handlebars.compile(source);
                var html = template(data);
                $('tbody').empty().append(html);


                main.viewKeywordRank();
                pagination.createPage(meta.pageable_count);

            },
            error: function (request,status, error) {
                alert(error);
            }
        })

    },
    getPage : function () {
        var jsonParam =  main.makeParam();

        $.ajax({
            type: 'POST',
            url: '/page',
            contentType : "application/json",
            data: jsonParam,
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

    },
    makeParam : function () {
        var RequestDto = new Object();
        var query = $("#keyword").val();
        var page = Number($(".page-item.item.active").text());
        var size = pagination.row;

        RequestDto.query =query;
        if(page == 0){
            RequestDto.page = 1;
        }else{
            RequestDto.page = page;
        }
        RequestDto.size = size;

        var jsonParam = JSON.stringify(RequestDto);
        return jsonParam;
    },
    viewKeywordRank : function () {
        $.ajax({
            type: 'POST',
            url: '/keywordrank',
            contentType : "application/json",
            success: function (data) {

                var source = $('#keywordRank-template').html();
                var template = Handlebars.compile(source);
                var html = template({keywordRankDtoList:data});
                $('#keywordSection').empty().append(html);

            },
            error: function (request,status, error) {
                alert(error);
            }
        })

    },
    makeClickEvent : function () {
        $(document).on("click", "a.place" , function(event) {
            var id = event.target.getAttribute("id");

            $.each(window.documents, function( index, value ) {
                if (value.id == id){
                    var source = $('#detail-template').html();
                    var template = Handlebars.compile(source);
                    var html = template(value);
                    $('.modal-content').empty().append(html);
                    $('.modal').modal('show');
                    return false;
                }
            });
        });
    }

}

var util = {
    getUrlParam : function (name) {
            var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
            if (results==null){
                return null;
            }
            else{
                return results[1] || 0;
            }
        }
    }

