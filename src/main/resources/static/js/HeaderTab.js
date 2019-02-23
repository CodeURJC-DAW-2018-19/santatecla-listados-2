var list = [];
var id = 0;
function addHeaderConcept() {
    var urlPage = "header";
    var ref = $(this).getElementById("headerConcept")
    $.ajax({
        url: urlPage
    }).done(function (ref) {
        if(list.size()<9) {
            $("#conceptList").append("");
            list.add(id);
            id++;
        }
    })
}
