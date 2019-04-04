import {Component, ChangeDetectorRef, AfterViewInit, ViewChild, TemplateRef} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {MatIconRegistry, MatDialog, MatDialogRef} from '@angular/material';
import { TdMediaService, tdRotateAnimation } from '@covalent/core';
import {LoginService} from "./logIn/logIn.service";
import {Router} from "@angular/router";

@Component({
    selector: 'my-app',
    templateUrl: './app.component.html',
    animations: [tdRotateAnimation],
})
export class AppComponent implements AfterViewInit {
    @ViewChild('loginDialog') loginDialog: TemplateRef<any>;
    dialogLog: MatDialogRef<any, any>;
    constructor(
        public media: TdMediaService,
        public dialog: MatDialog,
        private _changeDetectorRef: ChangeDetectorRef,
        private _iconRegistry: MatIconRegistry,
        private _domSanitizer: DomSanitizer,
        private loginService: LoginService,
        public router: Router
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
    logOut() {
        this.loginService.logOut().subscribe(
            (response) => {
                this.router.navigate(['/']);
            },
            (error) => console.log('Error when trying to log out: ' + error),
        );
    }
    ngAfterViewInit(): void {
        // broadcast to all listener observables when loading the page
        this.media.broadcast();
        this._changeDetectorRef.detectChanges();
    }

}