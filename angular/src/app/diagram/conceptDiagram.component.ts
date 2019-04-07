import {Component, Inject, Input} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DiagramService} from "./diagram.service";
import {Diagram} from "./diagram.model";
import {ActivatedRoute} from "@angular/router";



@Component ({
    templateUrl: "./conceptDiagram.component.html",

})

export class ConceptDiagramComponent{
    config: Object;
    id: number;


    constructor(
        private dialogRef: MatDialogRef<ConceptDiagramComponent>,
        private diagramService: DiagramService,
        @Inject(MAT_DIALOG_DATA) public data: any
    ){

        console.log(this.data);
        this.id = this.data;
        this.diagramService
            .getConceptDiagram(this.id)
            .subscribe(
                (data: Diagram) => this.addDataToConceptDiagram(data),
                error => console.log(error)
            );
    }

    addDataToConceptDiagram(data: Diagram) {
        let name: string;
        let errors: number;
        let hits: number;
        let pendings: number;

        name = data.name;
        errors = data.errors;
        hits = data.hits;
        pendings = data.pendings;
        this.config = {
            toolbox:{},
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    type: "shadow" // by default is line, optional：'line' | 'shadow'
                }
            },
            legend: {
                data: ["Aciertos", "Sin Contestar", "Fallos"]
            },

            xAxis: {
                type: "value"
            },
            series: [
                {
                    name: "Aciertos",
                    type: "bar",
                    stack: "Tema",
                    color: "#329B49",
                    label: {
                        normal: {
                            show: true,
                            position: "inside"
                        }
                    },
                    data: [hits]
                },
                {
                    name: "Sin Contestar",
                    type: "bar",
                    stack: "Tema",
                    color: "#95928E",
                    label: {
                        normal: {
                            show: true,
                            position: "inside"
                        }
                    },
                    data: [pendings]
                },
                {
                    name: "Fallos",
                    type: "bar",
                    stack: "Tema",
                    color: "#DC3522",
                    label: {
                        normal: {
                            show: true,
                            position: "inside"
                        }
                    },
                    data: [errors]
                }
            ],
            yAxis: {
                data: [name]
            },
        }

        console.log(this.config);
    }

    close() {
        this.dialogRef.close();
    }

}