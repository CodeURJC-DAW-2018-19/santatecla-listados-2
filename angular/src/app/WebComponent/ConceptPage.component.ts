import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";
import {QuestionService} from "../question/question.service";
import {Question} from "../question/question.model";
import {TdDialogService} from '@covalent/core';
import {MatDialog, MatDialogRef} from "@angular/material";
import {Answer} from "../answer/answer.model";
import {AnswerService} from "../answer/answer.service";
import {error} from "@angular/compiler/src/util";


// @ts-ignore
@Component({
    templateUrl: 'ConceptPage.template.html'
})

export class ConceptPageComponent implements OnInit {
    concept: Concept;
    id: number;
    conceptQuestionsCorrected: Question [];
    pageNumber: number;
    conceptQuestionsNotCorrected: Question [];
    createdquestion: Question;
    newAnswer: Answer;
    answerString: string = "";
    answerBoolean:string;

    @ViewChild('FirstModal') buttonFirstModalDialog: TemplateRef<any>;
    dialogFirstModal: MatDialogRef<any, any>;

    @ViewChild('SecondModal') buttonSecondModalDialog: TemplateRef<any>;
    dialogSecondModal: MatDialogRef<any, any>;

    @ViewChild('ThirdModal') buttonThirdModalDialog: TemplateRef<any>;
    dialogThirdModal: MatDialogRef<any, any>;

    @ViewChild('FourthModal') buttonFourthModalDialog: TemplateRef<any>;
    dialogFourthModal: MatDialogRef<any, any>;


    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private conceptService: ConceptService,
                private questionService: QuestionService,
                private _dialogService: TdDialogService,
                public dialog: MatDialog,
                private answerService: AnswerService
    ) {
        this.activatedRoute.params.subscribe(params => {
            this.id = params['id'];
        })
    }

    ngOnInit(): void {
        this.pageNumber = 0;
        this.newAnswer = {openAnswer: ''};
        this.refresh();
    }

    private refresh() {
        this.conceptService.getConcept(this.id).subscribe(
            (res: any) => {
                console.log(res);
                this.concept = res;
            },
            error => console.log(error)
        );
        this.questionService.getQuestionsByConcept(this.id, this.pageNumber, true).subscribe(
            (questions: Question[]) => this.conceptQuestionsCorrected = questions,
            error => console.log(error)
        );
        this.questionService.getQuestionsByConcept(this.id, this.pageNumber, false).subscribe(
            (questions: Question[]) => this.conceptQuestionsNotCorrected = questions,
            error => console.log(error)
        );
    }

    private newQuestion() {
        console.log(this.id);
        this.questionService.addQuestion(this.id).subscribe(
            (res: any) => {
                if ((<Question>res).corrected) {
                    this.createdquestion = res;
                    this.conceptQuestionsCorrected.push(this.createdquestion);
                    this.loadNewQuestionModal();
                } else {
                    this.createdquestion = res;
                    this.conceptQuestionsNotCorrected.push(this.createdquestion);
                    this.loadNewQuestionModal();
                }
            },
            error => console.log(error)
        );
    }

    openDialogFirstModal() {
        this.dialogFirstModal = this.dialog.open(this.buttonFirstModalDialog, {
            width: '250px',
            height: '250px'
        });
    }

    openDialogSecondModal() {
        this.dialogSecondModal = this.dialog.open(this.buttonSecondModalDialog, {
            width: '250px',
            height: '250px'
        });
    }

    openDialogThirdModal() {
        this.dialogThirdModal = this.dialog.open(this.buttonThirdModalDialog, {
            width: '250px',
            height: '250px'
        });

    }

    openDialogFourthModal() {
        this.dialogFourthModal = this.dialog.open(this.buttonFourthModalDialog, {
            width: '250px',
            height: '250px'
        });

    }

    loadNewQuestionModal() {
        console.log(this.createdquestion.type);
        if (this.createdquestion.type == 0) {
            this.answerString  = "";
            this.openDialogFirstModal();
        } else if (this.createdquestion.type == 1) {
            this.answerBoolean  = "";
            this.openDialogSecondModal();
        } else if (this.createdquestion.type == 2) {
            this.answerString  = "";
            this.openDialogFirstModal();
        } else if (this.createdquestion.type == 3) {
            this.answerString  = "";
            this.openDialogFourthModal();
        }
    }

    saveAnswerFirst() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerString).subscribe(
            (res:any) =>{
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogFirstModal.close();
                this.refresh();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    saveAnswerSecond() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerBoolean).subscribe(
            (res:any) =>{
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogSecondModal.close();
                this.refresh();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    saveAnswerFourth() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerString).subscribe(
            (res:any) =>{
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogFourthModal.close();
                this.refresh();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    addText(string: string){
        console.log(this.answerString);
        this.answerString += string;
        this.answerString += "add";
    }

}