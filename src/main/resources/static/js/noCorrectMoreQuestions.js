var nextPagea = 0;
function moreNoCorrectWord(nameConcept) {
    $("#loadGif").html('<img src="/GIF/loader.gif" style="width: 75px;float: right;height: 50px;">');
    var urlPage = "/MoreQuestionsNo/"+nameConcept+"?page=" + nextPagea;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $("#noCorrectContainer").append(data);
        $("#loadGif").html('');
        nextPagea++;
    })
}