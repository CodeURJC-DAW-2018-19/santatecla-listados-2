import {Component, OnInit, TemplateRef, ViewChild} from "@angular/core";
import {Topic} from "../topic/topic.model";
import {TopicService} from "../topic/topic.service";
import {Router} from "@angular/router";
import {LoginService} from "../logIn/logIn.service";
import {MatDialog, MatDialogRef} from "@angular/material";
import {ConceptService} from "../concept/concept.service";
import {Concept} from "../concept/concept.model";
import {TdDialogService} from '@covalent/core';
import {DiagramComponent} from "../diagram/diagram.component";
import {Diagram} from "../diagram/diagram.model";

@Component({
    templateUrl: 'MainStudent.template.html'
})


export class MainStudentComponent implements OnInit {
    topics: Topic[];
    topic: Topic;
    concept: Concept;
    idT: number;
    pageNumber: number;
    maxPage: number;
    noMoreTopic: boolean;
    searchS: string;
    moreButtonOpcion: boolean;
    @ViewChild('addTopic') buttonAddTopicDialog: TemplateRef<any>;
    dialogRef: MatDialogRef<any, any>;
    @ViewChild('addConcept') buttonAddConceptDialog: TemplateRef<any>;
    dialogAC: MatDialogRef<any, any>;


    constructor(private _dialogService: TdDialogService, private topicService: TopicService, private conceptService: ConceptService, private router: Router, private loginService: LoginService, public dialog: MatDialog, public diagramDialog: MatDialog) {
    }

    ngOnInit(): void {
        this.concept = {name: '', topic: {name: ''}};
        this.topic = {name: ''};
        this.pageNumber = 0;
        this.moreButtonOpcion = true;
        this.noMoreTopic = false;
        this.topicService.getSizeTopic().subscribe(
            (res: any) => {
                this.maxPage = res;
                this.refresh();
            },
            error => console.log(error)
        );

    }

    private refresh() {
        if (this.pageNumber < this.maxPage) {
            this.topicService.getTopics(this.pageNumber).subscribe(
                (topics: Topic[]) => {
                    this.topics = topics,
                        console.log(this.topics)
                },
                error => console.log(error)
            );
        } else {
            this.noMoreTopic = true;
        }
    }

    search(event: Event, search: string) {
        this.moreButtonOpcion = false;
        this.pageNumber = 0;
        this.noMoreTopic = false;
        this.searchS = search;
        console.log(this.topics);
        this.topics = [];
        this.topicService.getSizeTopicSearch(search).subscribe(
            (res: any) => {
                this.maxPage = res;
                if (this.pageNumber < this.maxPage) {
                    this.topicService.getSearch(this.searchS, this.pageNumber).subscribe(
                        (topics: Topic[]) => {
                            this.topics = topics;
                        },
                        error => console.log(error)
                    );
                } else {
                    this.noMoreTopic = true;
                }
            },
            error => console.log(error)
        );
    }

    refreshSearch() {
        if (this.pageNumber < this.maxPage) {
            this.topicService.getSearch(this.searchS, this.pageNumber).subscribe(
                (topics: Topic[]) => {
                    this.topics = topics;
                },
                error => console.log(error)
            );
        } else {
            this.noMoreTopic = true;
        }
    }

    loadMoreSearch() {
        this.pageNumber++;
        this.refreshSearch();
    }

    loadMore() {
        this.pageNumber++;
        this.refresh();
    }

    openDialog(): void {
        this.dialogRef = this.dialog.open(this.buttonAddTopicDialog, {
            width: '250px',
            height: '250px'
        });

        this.dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');

        });
    }

    openDialogAddConcept(id: number) {
        this.idT = id;
        this.dialogAC = this.dialog.open(this.buttonAddConceptDialog, {
            width: '250px',
            height: '250px'
        });

    }

    saveConcept() {
        this.topicService.getTopic(this.idT).subscribe(
            (res: any) => {
                this.concept.topic = res;
                this.conceptService.addConcept(this.concept).subscribe(
                    (_: any) => {
                        this.dialogAC.close();
                        this.refresh();
                    }, error => {
                        console.log(error);
                    }
                );
            }, error => {
                console.log(error);
            }
        );

    }

    saveTopic() {
        this.topicService.addTopic(this.topic).subscribe(
            (res: any) => {
                this.topics.push(res);
                this.dialogRef.close();
                this.refresh();
            }, error => {
                console.log(error);
            }
        );
    }

    removeConcept(id: number) {
        this._dialogService.openConfirm({
            message: '¿Estás seguro de eliminar este concepto?',
            title: 'Confirmar eliminación',
            width: '500px',
            height: '175px'
        }).afterClosed().subscribe((accept: boolean) => {
            if (accept) {
                this.conceptService.removeConcept(id).subscribe(
                    (res: any) => {
                        this.refresh();
                    }, error => {
                        console.log(error);
                    }
                );
            }
        });

    }

    removeTopic(id: number) {
        this._dialogService.openConfirm({
            message: '¿Estás seguro de eliminar este tema?',
            title: 'Confirmar eliminación',
            width: '500px',
            height: '175px'
        }).afterClosed().subscribe((accept: boolean) => {
            if (accept) {
                this.topicService.removeTopic(id).subscribe(
                    (res: any) => {
                        this.refresh();
                    }, error => {
                        console.log(error);
                    }
                );
            }
        });

    }

    showDiagram() {
        this.diagramDialog.open(DiagramComponent, {
            height: "625px",
            width: "825px"
        });
    }
}

