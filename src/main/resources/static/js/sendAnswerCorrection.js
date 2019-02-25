function sendAnswerCorrection(circle,answer,conceptname){
    urlPage = conceptname+"/"+answer+"/"+circle;
    $.ajax({
        url: urlPage
    })
}