function addTextItem() {
    $("#list").append('<li class="list-group-item">\n' +
        '                <div class="row">\n' +
        '                    <div class="col-md-6">\n' +
        '                        <div class="form-group bmd-form-group">\n' +
        '                            <input type="text" class="form-control">\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="col-md-6" style="position: relative;top: 27px ;left: 40px; ">\n' +
        '                        <div class="form-check-inline form-check-radio">\n' +
        '                            <label class="form-check-label">\n' +
        '                                <input class="form-check-input" type="radio" name="exampleR"\n' +
        '                                       id="exampleR" value="option1" >\n' +
        '                                CORRECTO<span class="circle">\n' +
        '                                                <span class="check"></span></span>\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                        <div class="form-check-inline form-check-radio" style="position: relative;left: 40px;">\n' +
        '                            <label class="form-check-label">\n' +
        '                                <input class="form-check-input" type="radio" name="exampleRa"\n' +
        '                                       id="exampleRa"\n' +
        '                                       value="option2">\n' +
        '                                INCORRECTO\n' +
        '                                <span class="circle"><span class="check"></span></span>\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li>');

}