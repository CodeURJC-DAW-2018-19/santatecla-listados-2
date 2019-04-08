function createItem(circle,texto,name) {
    var text = document.getElementById(texto).value;
    var urlPage = "/MainPage/Teacher/"+name+"/save/"+text+"/"+circle;
    $.ajax({
        url: urlPage
    }).done(function () {
        location.reload();
    })
}