
var pagination = {

    limit : 0,
    row : 0,
    createPage : function(pageableCount){
        window.currentPageLimit = Math.floor(pageableCount / pagination.row);

        var source = $('#page-template').html();
        var template = Handlebars.compile(source);

        if (currentPageLimit > 0){
            if (currentPageLimit <= pagination.limit){
                var html = template({start:1, end:currentPageLimit});
                $('#pageSection').empty().append(html);

                $('.page-item.prev').addClass("disabled");
                $('.page-item.next').addClass("disabled");

                $(".page-item.item:first").addClass("active");
            } else {
                var html = template({start:1,end:pagination.limit});
                $('#pageSection').empty().append(html);
                $('.page-item.prev').addClass("disabled")

                $(".page-item.item:first").addClass("active");
            }
        }  else{
            $('#pageSection').empty()
        }
    },
    next : function () {
        // current page group
        var lastPage = Number($(".page-item.item:last").text());
        var nextPage = lastPage + 1;
        var rest = currentPageLimit - lastPage;
        var source = $('#page-template').html();
        var template = Handlebars.compile(source);

        if (rest > pagination.limit){
            var html = template({start:nextPage, end:(nextPage + pagination.limit)});
            $('#pageSection').empty().append(html);
            $(".page-item.item:first").addClass("active");
            var page = $(".page-item.item:first").text();


        }else{
            var html = template({start:nextPage,end:(nextPage + rest)});
            $('#pageSection').empty().append(html);
            $(".page-item.item:first").addClass("active");
            $('.page-item.next').addClass("disabled");

        }
        main.getPage();

        return false;
    },
    prev : function(){
        var firstPage = Number($(".page-item.item:first").text());
        var start = firstPage - pagination.limit;

        var source = $('#page-template').html();
        var template = Handlebars.compile(source);

        if (start == 1){
            var html = template({start:1, end:pagination.limit});
            $('#pageSection').empty().append(html);
            $('.page-item.prev').addClass("disabled");
        } else{
            var html = template({start:start, end:(start + pagination.limit - 1)});
            $('#pageSection').empty().append(html);
        }


        $(".page-item.item:first").addClass("active");
        main.getPage();

        return false;
    },
    setClickEvent: function () {

        $(document).on("click", "a.page-link.prev" , function(event) {
            pagination.prev();

        });


        $(document).on("click", "a.page-link.next" , function(event) {
            pagination.next();

        });


        $(document).on("click", "a.page-link.item" , function(event) {
            var page = Number(event.target.text);
            var first = Number($(".page-item.item:first").text());
            var order = page - first;
            $(".page-item.item").removeClass("active");
            $(".page-item.item").eq(order).addClass("active");
            main.getPage();
        });
    }
}