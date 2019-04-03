import { Routes, RouterModule } from '@angular/router';
import {ConceptPageComponent} from "./WebComponent/ConceptPage.component";
import {MainStudentComponent} from "./WebComponent/MainStudent.component";
import {LoginComponent} from "./logIn/logIn.component";


const appRoutes = [
    { path: 'concept/:id', component: ConceptPageComponent},

    { path: 'student', component: MainStudentComponent},

    { path:'', redirectTo:'/logIn', pathMatch:'full'},

    { path: 'logIn', component: LoginComponent},

];

export const routing = RouterModule.forRoot(appRoutes);
