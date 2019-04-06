import {Component, ChangeDetectorRef, AfterViewInit, ViewChild, TemplateRef} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {MatIconRegistry, MatDialog, MatDialogRef} from '@angular/material';
import { TdMediaService, tdRotateAnimation } from '@covalent/core';
import {LoginService} from "./logIn/logIn.service";
import {Router} from "@angular/router";
import {User} from "./logIn/user.model";
import {RegisterService} from "./logIn/register.service";

@Component({
    selector: 'my-app',
    templateUrl: './app.component.html',
    animations: [tdRotateAnimation],
})
export class AppComponent implements AfterViewInit {
    @ViewChild('loginDialog') loginDialog: TemplateRef<any>;
    dialogLog: MatDialogRef<any, any>;
    @ViewChild('registerDialog') registerDialog: TemplateRef<any>;
    diaLog: MatDialogRef<any, any>;
    user:User;
    constructor(
        public media: TdMediaService,
        public dialog: MatDialog,
        private _changeDetectorRef: ChangeDetectorRef,
        private _iconRegistry: MatIconRegistry,
        private _domSanitizer: DomSanitizer,
        private loginService: LoginService,
        public router: Router,
        private registerService:RegisterService
    )
    {
        this._iconRegistry.addSvgIconInNamespace(
            'assets',
            'covalent',
            this._domSanitizer.bypassSecurityTrustResourceUrl(
                'https://raw.githubusercontent.com/Teradata/covalent-quickstart/develop/src/assets/icons/covalent.svg',
            ),
        );

    }
    logIn(event: any, user: string, pass: string) {
        event.preventDefault();
        console.log(user);
        console.log(pass);
        this.loginService.logIn(user, pass).subscribe(
            (u) => {
                this.router.navigate(['/student']);
                console.log(u);
                this.dialogLog.close();
            },
            (error) => {
                console.log(error);
                alert('Invalid user or password');
            },
        );
    }
    openLoginDialog() {
        this.dialogLog = this.dialog.open(this.loginDialog, {
            width: '50%',
            height: '50%',
        });
    }
    openRegisterDialog() {
        this.diaLog = this.dialog.open(this.registerDialog, {
            width: '50%',
            height: '50%',
        });
    }
    logOut() {
        this.loginService.logOut().subscribe(
            (response) => {
                this.router.navigate(['/']);
            },
            (error) => console.log('Error when trying to log out: ' + error),
        );
    }
    guest() {
        this.loginService.isGuest=true;
        this.router.navigate(['/student']);
    }
    ngAfterViewInit(): void {
        // broadcast to all listener observables when loading the page
        this.media.broadcast();
        this._changeDetectorRef.detectChanges();
    }
    register(event: any, userName: string, pass: string,name:string,surname:string){
        event.preventDefault();
        this.user={username:userName,surName:surname,name:name,password:pass,rol:"ROLE_STUDENT"};
        this.registerService.register(this.user).subscribe(
            (u:User)=>{
                console.log(u);
                this.diaLog.close();
            },error1 => console.log(error1)
        )
    }

}
