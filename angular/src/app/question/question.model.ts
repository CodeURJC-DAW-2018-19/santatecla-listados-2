import {Concept} from "../concept/concept.model";
import {Answer} from "../answer/answer.model";

export interface Question {
    question:string,
    id:number,
    corrected:boolean,
    type:number;
    Concept: Concept;
    answer: Answer;
}