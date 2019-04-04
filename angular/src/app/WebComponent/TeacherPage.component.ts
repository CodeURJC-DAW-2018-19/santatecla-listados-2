import {Component, OnInit} from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";
import {Item} from "../item/item.model";
import {Question} from "../question/question.model";
import {ItemService} from "../item/item.service";
import {TdDialogService} from "@covalent/core";


@Component({
    templateUrl: 'TeacherPage.template.html'

})

export class TeacherPageComponent implements OnInit{
    concept: Concept;
    items: Item[];
    questions: Question[];
    id:number;
    constructor(private _dialogService: TdDialogService,private router: Router,
                private activatedRoute: ActivatedRoute,
                private conceptService: ConceptService,private itemService: ItemService) {

    }

    ngOnInit(): void {
        this.id = this.activatedRoute.snapshot.params['id'];
        this.concept = {name: '', topic: {name: ''}};
        this.concept.id=this.id;
        this.items=[];
        this.questions=[];
        this.refresh();

    }

    private refresh() {
        this.conceptService.getConcept(this.concept.id).subscribe(
            (c: Concept) => {
                this.concept = c;
                this.items = Array.from(c.items);
                this.questions = Array.from(c.questions);
            },
            error => console.log(error)
        );
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
                        this.refresh();
                    }, error => {
                        console.log(error);
                    }
                );
            }
        });

    }
}
