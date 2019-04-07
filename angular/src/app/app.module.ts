import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpModule, JsonpModule } from '@angular/http';


import { AppComponent } from './app.component';
import { routing }  from './app.routing';
import {TopicService} from "./topic/topic.service";
import {ConceptService} from "./concept/concept.service";
import {ItemService} from "./item/item.service";
import {LoginService} from "./logIn/logIn.service";
import {QuestionService} from "./question/question.service";
import {ConceptPageComponent} from "./WebComponent/ConceptPage.component";
import {CovalentLayoutModule, CovalentMediaModule, CovalentSearchModule} from "@covalent/core";
import {
    MatButtonModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatMenuModule,
    MatInputModule,
    MatButtonToggleModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatDialogModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatTabsModule,
    MatSidenavModule,
    MatTooltipModule,
    MatRippleModule,
    MatRadioModule,
    MatGridListModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSliderModule,
    MatAutocompleteModule, MatDialogRef, MAT_DIALOG_DATA,
} from '@angular/material';
import {
    CovalentCommonModule,
    CovalentExpansionPanelModule,
    CovalentStepsModule,
    CovalentLoadingModule,
    CovalentDialogsModule,
    CovalentPagingModule,
    CovalentNotificationsModule,
    CovalentMenuModule,
    CovalentDataTableModule,
    CovalentMessageModule,
} from '@covalent/core';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterModule} from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {BasicAuthInterceptor} from "./auth/auth.interceptor";
import {ErrorInterceptor} from "./auth/error.interceptor";
import {MainStudentComponent} from "./WebComponent/MainStudent.component";
import {TeacherPageComponent} from "./WebComponent/TeacherPage.component";
import {AnswerService} from "./answer/answer.service";
import {DiagramComponent} from "./diagram/diagram.component";
import {DiagramService} from "./diagram/diagram.service";
import {CovalentBarEchartsModule, CovalentBaseEchartsModule, CovalentTooltipEchartsModule} from "@covalent/echarts";
import {ImageService} from "./image/image.service";
import {ConceptDiagramComponent} from "./diagram/conceptDiagram.component";

@NgModule({
    declarations: [AppComponent,MainStudentComponent,ConceptPageComponent,TeacherPageComponent,DiagramComponent, ConceptDiagramComponent ],
    imports: [BrowserModule, FormsModule, HttpModule, JsonpModule, routing, CovalentLayoutModule, CovalentMediaModule, CovalentSearchModule, MatIconModule, MatDialogModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule.forRoot([]),
        HttpClientModule,
        JsonpModule,
        /** Material Modules */
        MatButtonModule,
        MatListModule,
        MatIconModule,
        MatCardModule,
        MatMenuModule,
        MatInputModule,
        MatSelectModule,
        MatButtonToggleModule,
        MatSlideToggleModule,
        MatProgressSpinnerModule,
        MatDialogModule,
        MatSnackBarModule,
        MatToolbarModule,
        MatTabsModule,
        MatSidenavModule,
        MatTooltipModule,
        MatRippleModule,
        MatRadioModule,
        MatGridListModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatSliderModule,
        MatAutocompleteModule,
        MatInputModule,
        /** Covalent Modules */
        CovalentCommonModule,
        CovalentLayoutModule,
        CovalentMediaModule,
        CovalentExpansionPanelModule,
        CovalentStepsModule,
        CovalentDialogsModule,
        CovalentLoadingModule,
        CovalentSearchModule,
        CovalentPagingModule,
        CovalentNotificationsModule,
        CovalentMenuModule,
        CovalentDataTableModule,
        CovalentMessageModule,
        CovalentTooltipEchartsModule,
        CovalentBaseEchartsModule,
        CovalentBarEchartsModule,
        /** Additional **/
        NgxChartsModule,
        routing,],
    bootstrap: [AppComponent, DiagramComponent, ConceptDiagramComponent ],
    providers: [TopicService,ConceptService,ItemService,LoginService,QuestionService,DiagramService,ImageService,AnswerService,
        {provide:LocationStrategy, useClass: HashLocationStrategy},
        {provide:HTTP_INTERCEPTORS,useClass: BasicAuthInterceptor,multi:true},
        {provide:HTTP_INTERCEPTORS,useClass: ErrorInterceptor,multi:true}
        ]
})

export class AppModule { }
