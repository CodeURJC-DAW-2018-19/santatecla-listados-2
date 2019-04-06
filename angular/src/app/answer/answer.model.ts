import {Question} from "../question/question.model";

export interface Answer {
    id? : number;
    answerTest? : string;
    openAnswer: string;
    mark?: boolean;
}
