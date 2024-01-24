function load(page) {
    let count = $('#page_count').val();
    window.location.href = location.origin + '/?count=' + count + (page !== undefined ? '&page=' + page : '');
}

function edit(id) {
    let tr = $('#tasks tr[data-id="' + id + '"]'),
        description = tr.find('.description input'),
        status = tr.find('.status select'),
        edit = tr.find('.edit'),
        del = tr.find('.delete');

    $.ajax({
        method: 'get',
        dataType: 'json',
        url: '/tasks/' + id,
        beforeSend: function() {
            $('#tasks .description input').removeClass('active').prop('disabled', true);
            $('#tasks .status select').removeClass('active').prop('disabled', true);
            $('#tasks .save').removeClass('save').addClass('delete');
            $('#tasks .edit').show();
        },
        success: function(data){console.log(data);
            if (data.id) {
                description.addClass('active').prop('disabled', false);
                status.addClass('active').prop('disabled', false);
                edit.hide();
                del.removeClass('delete').addClass('save');
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('Task not found');
        }
    });
}

function save(id) {
    let tr = $('#tasks tr[data-id="' + id + '"]'),
        description = tr.find('.description input'),
        status = tr.find('.status select'),
        edit = tr.find('.edit'),
        save = tr.find('.save'),
        task = {
            id: id,
            description: tr.find('.description input').val(),
            status: tr.find('.status select').val()
        };

    $.ajax({
        method: 'post',
        dataType: 'json',
        url: '/tasks',
        data: task,
        success: function(data){console.log(data);
            if (data.id) {
                description.removeClass('active').prop('disabled', true);
                status.removeClass('active').prop('disabled', true);
                edit.show();
                save.removeClass('save').addClass('delete');
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('Task not found');
        }
    });
}

function add() {
    let task = {
        description: $('#task .description input').val(),
        status: $('#task .status select').val()
    };

    $.ajax({
        method: 'post',
        dataType: 'json',
        url: '/tasks',
        data: task,
        success: function(data){console.log(data);
            if (data.id) {
                location.reload();
            }
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('Wrong task');
        }
    });
}

function del(id) {
    $.ajax({
        method: 'delete',
        dataType: 'json',
        url: '/tasks/' + id,
        success: function(data){console.log(data);
            if (data) window.location.href = '/';
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert('Wrong task');
        }
    });
}
