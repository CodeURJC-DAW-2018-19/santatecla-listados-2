function sendAnswerCorrection(circle,answer,conceptname){
    var urlPage = conceptname+"/"+answer+"/"+circle;
    $.ajax({
        url: urlPage
    })
}<