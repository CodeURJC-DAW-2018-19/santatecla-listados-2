import {Injectable} from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {Topic} from './topic.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';

const BASE_URL = '/api/topics/';

@Injectable()
export class TopicService {

    constructor(private loginService: LoginService, private http: HttpClient) {
    }

    getTopics(pag:number): Observable<Topic[]> {
        return this.http.get<any>(BASE_URL+"?page=" + pag, {withCredentials: true})
            .pipe(map(result => result.content), catchError(error => this.handleError(error)));
    }

    getTopic(id: number) {
        return this.http.get(BASE_URL+id,{withCredentials:true});
    }

    addTopic(topic: Topic): Observable<Topic> {
        const body = JSON.stringify(topic);
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
        });

        return this.http
            .post<Topic>(BASE_URL, body, {headers})
            .pipe(catchError((error) => this.handleError(error)));

    }
    getSizeTopic(){
       return this.http.get(BASE_URL+"size");
    }
    removeTopic(id: number): Observable<Topic> {
        return this.http.delete<{ topic: Topic }>(BASE_URL  + id, {withCredentials: true})
            .pipe(map(response => response.topic), catchError(error => this.handleError(error)));
    }

    updateTopic(topic: Topic): Observable<Topic> {
        return this.http.put<{ topic: Topic }>(BASE_URL + "/" + topic.id, topic, {withCredentials: true})
            .pipe(map(response => response.topic), catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }

}
