import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';
import {Question} from "./question.model";

const BASE_URL = '/api/questions/';

@Injectable()
export class QuestionService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getQuestions():Observable<Set<Question>> {
        return this.http.get<{questions: Set<Question>}>(BASE_URL,{ withCredentials: true })
            .pipe(map(response=> response.questions), catchError( error => this.handleError(error)));
    }

    getQuestion(id: number):Observable<Question> {
        return this.http.get<{q:Question}>(BASE_URL +"/"+ id,{ withCredentials: true })
            .pipe(map(response => response.q),catchError(error => this.handleError(error)));
    }

    getQuestionsByConcept(id:number, page:number, corrected:boolean):Observable<Question[]>{
        return this.http.get<any>(BASE_URL+'concept/'+id +'/'+corrected+ "?page=" + page, {withCredentials: true})
            .pipe(map(result => result.content), catchError(error => this.handleError(error)));
    }
    getQuestionByConceptIdAndNotCorrected(id:number,page:number):Observable<Question[]>{
        return this.http.get<any>(BASE_URL+'concept/'+id +"/getPage"+"?page=" + page, {withCredentials: true})
            .pipe(map(result => result.content), catchError(error => this.handleError(error)));
    }
    addQuestion(id:number){
        return this.http.post(BASE_URL+ id, {withCredentials:true})
    }

    removeQuestion(question: Question):Observable<Question> {
        return this.http.delete<{q:Question}>(BASE_URL +"/"+ question.id,{ withCredentials: true })
            .pipe(map(response => response.q), catchError(error => this.handleError(error)));
    }

    updateQuestion(question:Question):Observable<Question> {
        return this.http.put<{q:Question}>(BASE_URL +"/"+ question.id, question,{ withCredentials: true })
            .pipe(map(response => response.q),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
    getSizeQuestion(id: number){
        return this.http.get(BASE_URL + "sizeQuestion/" + id);
    }
    /*addQuestion(question:Question):Observable<Question> {
    return this.http.post<{q:Question}>(BASE_URL, question,{ withCredentials: true })
        .pipe(map(response => response.q), catchError(error => this.handleError(error)));
}*/

}
