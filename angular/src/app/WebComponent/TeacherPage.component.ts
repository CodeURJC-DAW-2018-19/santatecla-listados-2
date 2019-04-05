import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";
import {Item} from "../item/item.model";
import {Question} from "../question/question.model";
import {QuestionService} from "../question/question.service";
import {ItemService} from "../item/item.service";
import {TdDialogService} from "@covalent/core";
import {MatDialog, MatDialogRef} from "@angular/material";


@Component({
    templateUrl: 'TeacherPage.template.html'

})

export class TeacherPageComponent implements OnInit{
    @ViewChild('addItem') buttonAddItemDialog: TemplateRef<any>;
    dialogRef: MatDialogRef<any, any>;
    concept: Concept;
    items: Item[];
    questions: Question[];
    item:Item;
    id:number;
    pageNumberQuestion: number;
    noMoreQuestion: boolean;
    maxPageQuestion: number;
    pageNumberItem: number;
    noMoreItem: boolean;
    maxPageItem: number;
    constructor(private _dialogService: TdDialogService,private router: Router,
                private activatedRoute: ActivatedRoute,
                private conceptService: ConceptService,
                private questionService:QuestionService,
                private itemService: ItemService,
                public dialog: MatDialog) {

    }

    ngOnInit(): void {
        this.id = this.activatedRoute.snapshot.params['id'];
        this.concept = {name: '', topic: {name: ''}};
        this.concept.id=this.id;
        this.item={name:'',concept:{name:'', topic: {name: ''}},correct:false};
        this.items=[];
        this.pageNumberQuestion = 0;
        this.noMoreQuestion = false;
        this.questions=[];
        this.questionService.getSizeQuestion(this.id).subscribe(
            (res:any)=>{
                this.maxPageQuestion=res;
                this.refreshQuestion();
            },
            error => console.log(error)
        );
        this.pageNumberItem = 0;
        this.noMoreItem = false;
        this.items=[];
        this.itemService.getSizeItem(this.id).subscribe(
            (res:any)=>{
                this.maxPageItem=res;
                this.refreshItem();
            },
            error => console.log(error)
        );
    }

    updateQuestion(question:Question,t:boolean){
        console.log(t);
        console.log(question);
        question.corrected = t;
        if (t){
            this.concept.hits+=1;
            this.concept.pendings-=1;
        }else{
            this.concept.errors+=1;
            this.concept.pendings-=1;
        }
        console.log(this.concept);
        this.questionService.updateQuestion(question).subscribe(
            (_:Question) => {
                this.conceptService.updateConcept(this.concept).subscribe(
                    (c:Concept) => {
                        this.concept=c;
                        this.refreshQuestion();
                    },error => console.error(error)
                );

            },error=> console.error(error)
        );
    }
    private refreshQuestion() {
        this.conceptService.getConcept(this.concept.id).subscribe(
            (c: Concept) => {
                this.concept = c;
                if (this.pageNumberQuestion < this.maxPageQuestion) {
                    this.questionService.getQuestionByConceptIdAndNotCorrected(this.concept.id,this.pageNumberQuestion).subscribe(
                        (questions: Question[]) =>
                        {
                            this.questions = questions
                        },
                        error => console.log(error)
                    );
                }else{
                    this.noMoreQuestion=true;
                }
            },
            error => console.log(error)
        );
    }
    private refreshItem() {
        this.conceptService.getConcept(this.concept.id).subscribe(
            (c: Concept) => {
                this.concept = c;
                if (this.pageNumberItem < this.maxPageItem) {
                    this.itemService.getItemByConceptId(this.concept.id,this.pageNumberItem).subscribe(
                        (item: any) =>
                        {
                            this.items = item
                        },
                        error => console.log(error)
                    );
                }else{
                    this.noMoreItem=true;
                }
            },
            error => console.log(error)
        );
    }
    loadMoreQuestion(){
        this.pageNumberQuestion++;
        this.refreshQuestion();
    }
    loadMoreItem(){
        this.pageNumberItem++;
        this.refreshItem();
    }
    deleteItem(id: number) {
        this._dialogService.openConfirm({
            message: '¿Estás seguro de eliminar este item?',
            title: 'Confirmar eliminación',
            width: '500px',
            height: '175px'
        }).afterClosed().subscribe((accept: boolean) => {
            if (accept) {
                this.itemService.removeItem(id).subscribe(
                    (_: any) => {
                        this.refreshItem();
                    }, error => {
                        console.log(error);
                    }
                );
            }
        });

    }
    openDialog(): void {
        this.dialogRef = this.dialog.open(this.buttonAddItemDialog, {
            width: '400px',
            height: '250px'
        });

        this.dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');

        });
    }
    saveItem() {
        this.conceptService.getConcept(this.id).subscribe(
            (res: any) => {
                this.item.concept=res;
                this.itemService.addItem(this.item).subscribe(
                    (_: any) => {
                        this.dialogRef.close();
                        this.refreshItem();
                    }, error => {
                        console.log(error);
                    }
                );
            }, error => {
                console.log(error);
            }
        );

    }
}
