var nextPage = 0; 
function moreCorrectWord(nameConcept) {
    $("#loadGif").html('<img src="/GIF/loader.gif" style="width: 75px;float: right;height: 50px;">');
    var urlPage = "/MoreQuestions/"+nameConcept+"?page=" + nextPage;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $("#questionTable").append(data);
        $("#loadGif").html('');
        nextPage++;
    })
}