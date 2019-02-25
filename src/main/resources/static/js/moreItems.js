var nextPage = 0;
function loadMoreItems(nameConcept) {
    $("#loadGif").html('<img src="/GIF/loader.gif" style="width: 75px;float: right;height: 50px;">');
    var urlPage = "/loadMoreItems/"+nameConcept+"?page=" + nextPage;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $("#list").append(data);
        $("#loadGif").html('');
        nextPage++;
    })
}