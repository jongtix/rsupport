var index = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
        $('#btn-update').on('click', function() {
            _this.update();
        });
        $('#btn-delete').on('click', function() {
            _this.delete();
        });


        //활성화된 페이지 표시
        var currentPage = $('#currentPage').val();

        $(function () {
            $('.page-item').each(function () {
                if ($(this).attr('name') == currentPage) {
                    $(this).addClass('active');
                }
            });
        })


    },
    save : function() {
        var data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val(),
        };

        var currentPage = $('#currentPage').val();

        $.ajax({
            type: 'POST',
            url: '/api/announcement',
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            alert('글이 등록되었습니다.');
            window.location.href = '/announcement/view/' + data.id;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
        };
        var currentPage = $('#currentPage').val();

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/announcement/' + id + '?currentPage=' + currentPage,
            dataType: 'json',
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(data)
        }).done(function(data) {
            alert('글이 수정 되었습니다.');
            window.location.href = '/announcement/view/' + data.id + '?currentPage=' + currentPage;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var id = $('#id').val();
        var currentPage = $('#currentPage').val();

        var check = confirm('공지사항(id: ' + id + ')을 삭제 하시겠습니까?');

        if (check) {

            $.ajax({
                type: 'DELETE',
                url: '/api/announcement/' + id,
                dataType: 'json',
                contentType: 'application/json; charset=UTF-8',
            }).done(function() {
                alert('글이 삭제 되었습니다.');
                window.location.href = '/announcement/list?currentPage=' + currentPage;
            }).fail(function(error) {
                alert(JSON.stringify(error));
            });

        } else {
            return false;
        }
    }
}

index.init();