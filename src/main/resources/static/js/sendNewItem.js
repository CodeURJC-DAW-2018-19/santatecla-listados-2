function createItem(circle,number,name) {
    var text = $(document).getElementById("text"+number).getValue();
    urlPage = "/MainPage/Teacher/"+name+"/save/"+text+circle;
    $.ajax({
        url: urlPage
    })
}