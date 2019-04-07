import { Injectable } from '@angular/core';

import {Observable, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

import {HttpClient} from '@angular/common/http';
import {LoginService} from '../logIn/logIn.service';
import {Image} from "./image.model";

const BASE_URL = '/api/images';

@Injectable()
export class ImageService {

    constructor(private loginService: LoginService,private http: HttpClient) { }


    getImagesById(id:number) {
        return this.http.get(BASE_URL+"/All/"+id,{ withCredentials: true });
}
    getImage(id: number){
        return this.http.get(BASE_URL+ "/"+id, {withCredentials:true})
    }

    addImage(title:string, concept:string, image:any):Observable<Image> {
        return this.http.post<{i:Image}>(BASE_URL+"/newImage/"+title+"/"+concept, image,{ withCredentials: true })
            .pipe(map(response => response.i), catchError(error => this.handleError(error)));
    }


    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
