import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {UserR} from "./user.model";
import {Observable, throwError} from "rxjs";
import {environment} from "../../environments/environment";

const BASE_URL = environment.apiEndpoint + "/users/";

export interface User {
    id?: number;
    name: string;
    rol: string;
    authdata: string;
}

@Injectable()
export class LoginService {

    isLogged = false;
    isAdmin = false;
    isStudent=false;
    isGuest=false;
    user: User;
    auth: string;

    constructor(private http: HttpClient) {
        let user = JSON.parse(localStorage.getItem('currentUser'));
        if (user) {
            console.log('Logged user');
            this.setCurrentUser(user);
        }
    }

    logIn(user: string, pass: string) {
        let auth = window.btoa(user + ':' + pass);

        const headers = new HttpHeaders({
            Authorization: 'Basic ' + auth,
            'X-Requested-With': 'XMLHttpRequest',
        });

        return this.http.get<User>(BASE_URL+'logIn', { headers })
            .pipe(map(user => {
                if (user) {
                    this.setCurrentUser(user);
                    user.authdata = auth;
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
                return user;
            }));
    }

    logOut() {

        return this.http.get(BASE_URL+'logOut').pipe(
            map(response => {
                this.removeCurrentUser();
                return response;
            }),
        );
    }

    private setCurrentUser(user: User) {
        this.isLogged = true;
        this.user = user;
        this.isAdmin = (this.user.rol === 'ROLE_TEACHER');
        this.isStudent  = (this.user.rol === 'ROLE_STUDENT');
        this.isGuest=!this.isStudent && !this.isAdmin;
    }

    removeCurrentUser() {
        localStorage.removeItem('currentUser');
        this.isLogged = false;
        this.isAdmin = false;
        this.isStudent = false;
        this.isGuest=false;
    }
    register(user: UserR):Observable<UserR> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
        });
        const body = JSON.stringify(user);
        return this.http.post<UserR>(BASE_URL+"register",body,{headers})
            .pipe(
                map(response => response),
                catchError(error => this.handleError(error))
            );
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
