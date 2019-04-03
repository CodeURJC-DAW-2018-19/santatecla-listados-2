export interface Answer {
    id? : number;
    answerTest : string;
    openAnswer: string;
    mark: boolean;
    question: Question;
}
