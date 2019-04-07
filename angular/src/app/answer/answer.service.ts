import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import 'rxjs/Rx';
import {Answer} from './answer.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from "../logIn/logIn.service";
import {environment} from "../../environments/environment";

const BASE_URL = environment.apiEndpoint + "/answers/";

@Injectable()
export class AnswerService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getAnswers():Observable<Set<Answer>> {
        return this.http.get<{answer: Set<Answer>}>(BASE_URL,{ withCredentials: true })
            .pipe(map(response=> response.answer), catchError( error => this.handleError(error)));
    }

    getAnswer(id: number):Observable<Answer> {
        return this.http.get<{a:Answer}>(BASE_URL +"/"+ id,{ withCredentials: true })
            .pipe(map(response => response.a),catchError(error => this.handleError(error)));
    }

    addAnswer(id:number,answer:string):Observable<Answer> {
        const body = JSON.stringify(answer);
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
        });
        return this.http.post<Answer>(BASE_URL+id, body,{ withCredentials: true , headers})
            .pipe(catchError(error => this.handleError(error)));
    }

    removeAnswer(answer: Answer):Observable<Answer> {
        return this.http.delete<{a:Answer}>(BASE_URL +"/"+ answer.id,{ withCredentials: true })
            .pipe(map(response => response.a), catchError(error => this.handleError(error)));
    }

    updateAnswer(answer:Answer):Observable<Answer> {
        return this.http.put<{a:Answer}>(BASE_URL +"/"+ answer.id, answer,{ withCredentials: true })
            .pipe(map(response => response.a),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
