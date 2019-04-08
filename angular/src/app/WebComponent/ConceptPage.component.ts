import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";
import {QuestionService} from "../question/question.service";
import {Question} from "../question/question.model";
import {TdDialogService} from '@covalent/core';
import {MatDialogRef} from "@angular/material";
import {Answer} from "../answer/answer.model";
import {AnswerService} from "../answer/answer.service";
import {error} from "@angular/compiler/src/util";
import {connectableObservableDescriptor} from "rxjs/internal/observable/ConnectableObservable";
import {ConceptDiagramComponent} from "../diagram/conceptDiagram.component";
import {MatDialog} from "@angular/material";
import {Image} from "../image/image.model";
import {ImageService} from "../image/image.service";


// @ts-ignore
@Component({
    templateUrl: 'ConceptPage.template.html'
})

export class ConceptPageComponent implements OnInit {
    concept: Concept;
    id: number;
    conceptQuestionsCorrected: Question [];
    pageNumberTrue: number;
    pageNumberFalse: number;
    maxPageTrue: number;
    maxPageFalse: number;
    noMoreCorrected: boolean;
    noMoreNotCorrected: boolean;
    conceptQuestionsNotCorrected: Question [];
    createdquestion: Question;
    newAnswer: Answer;
    answerString: string = "";
    answerBoolean: string;
    images: Image[];

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
                private diagramDialog: MatDialog,
                private _dialogService: TdDialogService,
                public dialog: MatDialog,
                private answerService: AnswerService,
                private imageService: ImageService
    ) {
        this.activatedRoute.params.subscribe(params => {
            this.id = params['id'];
        })
    }


    ngOnInit(): void {
        this.pageNumberTrue = 0;
        this.pageNumberFalse = 0;
        this.noMoreCorrected = false;
        this.noMoreNotCorrected = false;
        this.refresh();
        this.questionService.getSizeQuestionCorrected(this.id).subscribe(
            (res: any) => {
                this.maxPageTrue = res;
                console.log(this.maxPageTrue);
                this.refreshCorrected();
            },error => console.log(error)
        );
        this.questionService.getSizeQuestion(this.id).subscribe(
            (res: any) => {
                this.maxPageFalse = res;
                this.refreshNotCorrected();
            },
            error => console.log(error)
        );
        this.newAnswer = {openAnswer: ''};
    }

    private refresh() {
        this.conceptService.getConcept(this.id).subscribe(
            (res: any) => {
                this.concept = res;
            },
            error => console.log(error)
        );
        this.imageService.getImagesById(this.id).subscribe(
            (images: Image[]) => this.images = images,
            error => console.log(error)
        )
    }

     newQuestion() {
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
            width: '500px',
            height: '220px'
        });
    }

    openDialogSecondModal() {
        this.dialogSecondModal = this.dialog.open(this.buttonSecondModalDialog, {
            width: '500px',
            height: '220px'
        });
    }

    openDialogThirdModal() {
        this.dialogThirdModal = this.dialog.open(this.buttonThirdModalDialog, {
            width: '500px',
            height: '450px'
        });

    }

    openDialogFourthModal() {
        this.dialogFourthModal = this.dialog.open(this.buttonFourthModalDialog, {
            width: '600px',
            height: '250px'
        });

    }

    loadNewQuestionModal() {
        console.log(this.createdquestion.type);
        if (this.createdquestion.type == 0) {
            this.answerString = "";
            this.openDialogFirstModal();
        } else if (this.createdquestion.type == 1) {
            this.answerBoolean = "";
            this.openDialogSecondModal();
        } else if (this.createdquestion.type == 2) {
            this.answerString = "";
            this.openDialogFirstModal();
        } else if (this.createdquestion.type == 3) {
            this.answerString = "";
            this.openDialogFourthModal();
        }
    }

    saveAnswerFirst() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerString).subscribe(
            (res: any) => {
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogFirstModal.close();
                this.refreshCorrected();
                this.refreshNotCorrected();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    saveAnswerSecond() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerBoolean).subscribe(
            (res: any) => {
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogSecondModal.close();
                this.refreshCorrected();
                this.refreshNotCorrected();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    saveAnswerFourth() {
        this.answerService.addAnswer(this.createdquestion.id, this.answerString).subscribe(
            (res: any) => {
                this.createdquestion.answer = res;
                console.log((<Answer>res).openAnswer);
                this.dialogFourthModal.close();
                this.refreshCorrected();
                this.refreshNotCorrected();
            }, error1 => {
                console.log(error1);
            }
        )
    }

    addText(string: string) {
        console.log(this.answerString);
        this.answerString += string;
        this.answerString += "add";
    }


    showConceptDiagram() {
        this.diagramDialog.open(ConceptDiagramComponent, {
            height: "625px",
            width: "825px",
            data: this.id
        });
    }

    private refreshCorrected() {
        if (this.pageNumberTrue < this.maxPageTrue) {
            this.questionService.getQuestionsByConcept(this.id, this.pageNumberTrue, true).subscribe(
                (questions: Question[]) => {
                    this.conceptQuestionsCorrected = questions;
                    console.log(this.conceptQuestionsCorrected)
                },
                error => console.log(error)
            );
        } else {
            this.noMoreCorrected = true;
        }
    }

    private refreshNotCorrected() {
        if (this.pageNumberFalse < this.maxPageFalse) {
            this.questionService.getQuestionsByConcept(this.id, this.pageNumberFalse, false).subscribe(
                (questions: Question[]) => {
                    this.conceptQuestionsNotCorrected = questions;
                },
                error => console.log(error)
            );
        } else {
            this.noMoreNotCorrected = true;
        }
    }

    loadMoreCorrected() {
        this.pageNumberTrue++;
        this.refreshCorrected();
    }

    loadMoreNotCorrected() {
        this.pageNumberFalse++;
        this.refreshNotCorrected();
    }


}
