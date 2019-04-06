import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material";
import {DiagramService} from "./diagram.service";
import {Diagram} from "./diagram.model";
import {Page} from "../pages/page.model";


@Component({
    templateUrl: "./diagram.component.html"
})

export class DiagramComponent {
    config: Object;
    constructor(
        private dialogRef: MatDialogRef<DiagramComponent>,
        private diagramService: DiagramService
    ){
        this.diagramService
            .getDiagram()
            .subscribe(
                (data: Page<Diagram>) => this.addDataToDiagram(data),
                error => console.log(error)
            );
    }

    addDataToDiagram(data: Page<Diagram> ){
        let names: string[] = [];
        let errors: number[] = [];
        let hits: number[] = [];
        let pendings: number[] = [];
        for(let i = 0; i < data.content.length; i++){
            names.push(data.content[i].name);
            errors.push(data.content[i].errors);
            hits.push(data.content[i].hits);
            pendings.push(data.content[i].pendings);
        }
        this.config = {

            tooltip: {  trigger: "axis",
                        axisPointer: {
                            type: "shadow" // by default is line, optional：'line' | 'shadow'
                     }},
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
                    data: hits
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
                    data: pendings
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
                    data: errors
                }
            ],
            yAxis: {
                data: names
            },
        }

        console.log(this.config);
    }

    close() {
        this.dialogRef.close();
    }
}



