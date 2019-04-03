import {Component } from "@angular/core";
import {Question} from "../question/question.model";


@Component({
    templateUrl:'ConceptPage.template.html'

})

export class ConceptPageComponent {
    Question:Set<Question>;
}