import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {Concept} from './concept.model';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';
import {Question} from "../question/question.model";

const BASE_URL = '/api/concepts/';

@Injectable()
export class ConceptService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getConcepts():Observable<Set<Concept>> {
        return this.http.get<{concepts: Set<Concept>}>(BASE_URL,{ withCredentials: true })
            .pipe(map(result=> result.concepts), catchError( error => this.handleError(error)));
    }

    getConcept(id: number){
        return this.http.get(BASE_URL+ id, {withCredentials:true})
    }

    addConcept(concept:Concept):Observable<Concept> {
        return this.http.post<{c:Concept}>(BASE_URL, concept,{ withCredentials: true })
            .pipe(map(response => response.c), catchError(error => this.handleError(error)));
    }

    removeConcept(id:number):Observable<Concept> {
        return this.http.delete<{c:Concept}>(BASE_URL + id,{ withCredentials: true })
            .pipe(map(response => response.c), catchError(error => this.handleError(error)));
    }

    updateConcept(concept:Concept):Observable<Concept> {
        return this.http.put<{c:Concept}>(BASE_URL + concept.id, concept,{ withCredentials: true })
            .pipe(map(response => response.c),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
