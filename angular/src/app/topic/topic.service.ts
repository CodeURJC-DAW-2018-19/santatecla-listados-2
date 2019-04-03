import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {Topic} from './topic.model';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';

const BASE_URL = '/api/topics/';

@Injectable()
export class TopicService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getTopics():Observable<Topic[]> {
        return this.http.get<any>(BASE_URL,{ withCredentials: true })
            .pipe(map(result=> result.content), catchError( error => this.handleError(error)));
    }

    getTopic(id: number):Observable<Topic> {
        return this.http.get<{topic:Topic}>(BASE_URL +"/"+ id,{ withCredentials: true })
            .pipe(map(response => response.topic),catchError(error => this.handleError(error)));
    }

    addTopic(topic:Topic):Observable<Topic> {
        return this.http.post<{topic:Topic}>(BASE_URL, topic,{ withCredentials: true })
            .pipe(map(response => response.topic), catchError(error => this.handleError(error)));
    }

    removeTopic(topic: Topic):Observable<Topic> {
        return this.http.delete<{topic:Topic}>(BASE_URL +"/"+ topic.id,{ withCredentials: true })
            .pipe(map(response => response.topic), catchError(error => this.handleError(error)));
    }

    updateTopic(topic:Topic):Observable<Topic> {
        return this.http.put<{topic:Topic}>(BASE_URL +"/"+ topic.id, topic,{ withCredentials: true })
            .pipe(map(response => response.topic),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}