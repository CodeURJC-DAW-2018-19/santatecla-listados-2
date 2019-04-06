import {User} from "./user.model";
import {catchError, map} from "rxjs/operators";
import {throwError} from "rxjs";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
@Injectable()

export class RegisterService {
    constructor(private http: HttpClient) {
    }

    register(user: User) {
        return this.http.post<{ u: User }>("api/users/register", user, {withCredentials: false})
            .pipe(map(response => response.u), catchError(error => this.handleError(error)));
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
