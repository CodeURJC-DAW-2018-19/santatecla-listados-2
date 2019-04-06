import {Component, OnInit} from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";
import {QuestionService} from "../question/question.service";
import {Question} from "../question/question.model";
import {connectableObservableDescriptor} from "rxjs/internal/observable/ConnectableObservable";
import {ConceptDiagramComponent} from "../diagram/conceptDiagram.component";
import {MatDialog} from "@angular/material";



// @ts-ignore
@Component({
    templateUrl: 'ConceptPage.template.html'
})

export class ConceptPageComponent implements OnInit {
    concept: Concept;
    id: number;
    conceptQuestionsCorrected: Question [];
    pageNumber:number;
    conceptQuestionsNotCorrected: Question [];


    constructor(private router: Router,
                private activatedRoute: ActivatedRoute,
                private conceptService: ConceptService,
                private questionService: QuestionService,
                private diagramDialog: MatDialog,
        ) {
        this.activatedRoute.params.subscribe(params => {
            this.id = params['id'];
        })
    }



    ngOnInit(): void {
        this.pageNumber = 0;
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
        this.questionService.getQuestionsByConcept(this.id, this.pageNumber,true).subscribe(
            (questions: Question[]) => this.conceptQuestionsCorrected = questions,
            error => console.log(error)
        );
        this.questionService.getQuestionsByConcept(this.id, this.pageNumber,false).subscribe(
            (questions: Question[]) => this.conceptQuestionsNotCorrected = questions,
            error => console.log(error)
        );
    }

    private newQuestion(){
        console.log(this.id);
        this.questionService.addQuestion(this.id).subscribe(
            (res: any) => {
                if((<Question>res).corrected) {
                    this.conceptQuestionsCorrected.push(res)
                }else {
                    this.conceptQuestionsNotCorrected.push(res);
                }
            },
            error => console.log(error)
        );
    }

    showConceptDiagram() {
        this.diagramDialog.open(ConceptDiagramComponent, {
            height: "625px",
            width: "825px",
            data: this.id
        });
    }


}