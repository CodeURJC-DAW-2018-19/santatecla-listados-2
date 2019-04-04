import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

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

        return this.http.get<User>('api/users/logIn', { headers })
            .pipe(map(user => {
                console.log(user);
                if (user) {
                    this.setCurrentUser(user);
                    user.authdata = auth;
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }
                console.log(user);
                return user;
            }));
    }

    logOut() {

        return this.http.get('api/users/logOut').pipe(
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
    }

    removeCurrentUser() {
        localStorage.removeItem('currentUser');
        this.isLogged = false;
        this.isAdmin = false;
    }
}
