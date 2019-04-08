var nextPage = 0;
function loadMoreTopic() {
    $("#loadGif").html('<img src="/GIF/loader.gif" style="width: 75px;float: right;height: 50px;">');
    var urlPage = "/TopicMoreButton?page=" + nextPage;
    $.ajax({
        url: urlPage
    }).done(function (data) {
        $("#accordion").append(data);
        $("#loadGif").html('');
        nextPage++;
    })
}
