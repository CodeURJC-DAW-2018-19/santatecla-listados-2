import { Component, ViewChild, TemplateRef } from '@angular/core';
import { Router } from '@angular/router';

import { MatDialog, MatDialogRef } from '@angular/material';
import {LoginService} from './logIn.service';

@Component({
    templateUrl: './login.component.html',
})
export class LoginComponent {
    @ViewChild('loginDialog') loginDialog: TemplateRef<any>;
    dialogRef: MatDialogRef<any, any>;

    constructor(public dialog: MatDialog, private router: Router, public loginService: LoginService) {}

    logIn(event: any, user: string, pass: string) {
        event.preventDefault();
        console.log(user);
        console.log(pass);
        this.loginService.logIn(user, pass).subscribe(
            (u) => {
                this.router.navigate(['/student']);
                console.log(u);
                this.dialogRef.close();
            },
            (error) => {
                console.log(error);
                alert('Invalid user or password');
            },
        );
    }

    logOut() {
        this.loginService.logOut().subscribe(
            (response) => {
                this.router.navigate(['/']);
            },
            (error) => console.log('Error when trying to log out: ' + error),
        );
    }

    openLoginDialog() {
        this.dialogRef = this.dialog.open(this.loginDialog, {
            width: '50%',
            height: '50%',
        });
    }

    gotoStudent(){
        this.router.navigate(['/student'])
    }
}
