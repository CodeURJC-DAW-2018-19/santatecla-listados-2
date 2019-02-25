function newQuestion(name) {
    var urlPage = "/NewQuestion/" + name;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $('#contained').append(data);
        $('#addQuestion').modal('show');
    })
}