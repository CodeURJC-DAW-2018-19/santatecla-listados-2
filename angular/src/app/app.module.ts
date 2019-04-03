import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { routing }  from './app.routing';
import {TopicService} from "./topic/topic.service";
import {ConceptService} from "./concept/concept.service";
import {ItemService} from "./item/item.service";
import {LoginService} from "./logIn/logIn.service";
import {QuestionService} from "./question/question.service";
import {MainStudentComponent} from "./WebComponent/MainStudent.component";
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
    MatAutocompleteModule,
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
import {HttpClientModule} from "@angular/common/http";
import {NgxChartsModule} from "@swimlane/ngx-charts";
import {LoginComponent} from "./logIn/logIn.component";

@NgModule({
    declarations: [AppComponent,MainStudentComponent,ConceptPageComponent, LoginComponent],
    imports: [BrowserModule, FormsModule, HttpModule, JsonpModule, routing, CovalentLayoutModule, CovalentMediaModule, CovalentSearchModule, MatIconModule, MatDialogModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
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
        /** Additional **/
        NgxChartsModule,
        routing,],
    bootstrap: [AppComponent],
    providers: [TopicService,ConceptService,ItemService,LoginService,QuestionService]
})
export class AppModule { }
