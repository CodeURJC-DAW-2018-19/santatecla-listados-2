import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {Item} from './item.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';
import {environment} from "../../environments/environment";

const BASE_URL = environment.apiEndpoint + "/items/";

@Injectable()
export class ItemService {

    constructor(private loginService: LoginService,private http: HttpClient) { }

    getItems():Observable<Set<Item>> {
        return this.http.get<{items: Set<Item>}>(BASE_URL,{ withCredentials: true })
            .pipe(map(response=> response.items), catchError( error => this.handleError(error)));
    }

    getItem(id: number):Observable<Item> {
        return this.http.get<{item:Item}>(BASE_URL +"/"+ id,{ withCredentials: true })
            .pipe(map(response => response.item),catchError(error => this.handleError(error)));
    }

    addItem(item:Item): Observable<Item> {
        const body = JSON.stringify(item);
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
        });

        return this.http
            .post<Item>(BASE_URL, body, {headers})
            .pipe(catchError((error) => this.handleError(error)));

    }

    removeItem(id:number):Observable<Item> {
        return this.http.delete<{item:Item}>(BASE_URL + id,{ withCredentials: true })
            .pipe(map(response => response.item), catchError(error => this.handleError(error)));
    }

    updateItem(item:Item):Observable<Item> {
        return this.http.put<{item:Item}>(BASE_URL + item.id, item,{ withCredentials: true })
            .pipe(map(response => response.item),catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
    getItemByConceptId(id:number,page:number):Observable<Item>{
        return this.http.get<any>(BASE_URL+'concept/'+id +"/getPage"+"?page=" + page, {withCredentials: true})
            .pipe(map(result => result.content), catchError(error => this.handleError(error)));
    }
    getSizeItem(id:number){
        return this.http.get(BASE_URL + "sizeItem/" + id);
    }
}
