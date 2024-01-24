$(function () {
    let tasks = $('#tasks');

    $('.pages a').on('click', function (e) {
        e.preventDefault();
        load($(this).data('id'));
    });

    tasks.on('click', '.edit', function (e) {
        e.preventDefault();
        edit($(this).data('id'));
    });

    tasks.on('click', '.save', function (e) {
        e.preventDefault();
        save($(this).data('id'));
    });

    tasks.on('click', '.delete', function (e) {
        e.preventDefault();
        del($(this).data('id'));
    });

    $('#task .save').on('click', function (e) {
        e.preventDefault();
        add();
    });

    $('#new_link').on('click', function (e) {
        e. preventDefault();
        $('#new_task').slideToggle();
    });
});
