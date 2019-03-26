import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import 'rxjs/Rx';

import {Item} from './item.model';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';

const BASE_URL = 'http://127.0.0.1:8080/api/topics/';

@Injectable()
export class ConceptService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getItems():Observable<Set<Item>> {
        return this.http.get<{items: Set<Item>}>(BASE_URL,{ withCredentials: true })
            .pipe(map(response=> response.items), catchError( error => this.handleError(error)));
    }

    getTopic(id: number):Observable<Item> {
        return this.http.get<{item:Item}>(BASE_URL +"/"+ id,{ withCredentials: true })
            .pipe(map(response => response.item),catchError(error => this.handleError(error)));
    }

    addItem(item:Item):Observable<Item> {
        return this.http.post<{item:Item}>(BASE_URL, item,{ withCredentials: true })
            .pipe(map(response => response.item), catchError(error => this.handleError(error)));
    }

    removeItem(item: Item):Observable<Item> {
        return this.http.delete<{item:Item}>(BASE_URL +"/"+ item.id,{ withCredentials: true })
            .pipe(map(response => response.item), catchError(error => this.handleError(error)));
    }

    updateItem(item:Item):Observable<Item> {
        return this.http.put<{item:Item}>(BASE_URL +"/"+ item.id, item,{ withCredentials: true })
            .pipe(map(response => response.item),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
