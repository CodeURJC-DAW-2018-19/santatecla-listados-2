import { Injectable} from "@angular/core";
import { HttpClient, HttpHeaders} from "@angular/common/http";
import { Diagram } from "./diagram.model";
import {LoginService} from "../logIn/logIn.service";
import {Page} from "../pages/page.model";
import {environment} from "../../environments/environment";


const BASE_URL = environment.apiEndpoint;

@Injectable()
export class DiagramService {

    constructor(private http: HttpClient, public loginService: LoginService){}

    getDiagram(){
        return this.http.get<Page<Diagram>>(BASE_URL + "/diagramInfo", {withCredentials: true} );
    }

    getConceptDiagram(id: number){
        return this.http.get<Diagram>(BASE_URL + "/conceptDiagramInfo/" + id, {withCredentials: false});
    }

}
