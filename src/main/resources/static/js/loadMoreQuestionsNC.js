var nextPage = 0;
function loadMoreQuestions(nameConcept) {
    $("#loadGif").html('<img src="/GIF/loader.gif" style="width: 75px;float: right;height: 50px;">');
    var urlPage = "/loadMoreQuestionsNC/"+nameConcept+"?page=" + nextPage;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $("#list-group").append(data);
        $("#loadGif").html('');
        nextPage++;
    })
}