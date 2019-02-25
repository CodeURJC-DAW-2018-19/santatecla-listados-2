function createItem(circle,texto,name) {
    var text = document.getElementById(texto).value;
    urlPage = "/MainPage/Teacher/"+name+"/save/"+text+"/"+circle;
    $.ajax({
        url: urlPage
    })
}