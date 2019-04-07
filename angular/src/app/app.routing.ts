import { Routes, RouterModule } from '@angular/router';
import {ConceptPageComponent} from "./WebComponent/ConceptPage.component";
import {MainStudentComponent} from "./WebComponent/MainStudent.component";
import {TeacherPageComponent} from "./WebComponent/TeacherPage.component";



const appRoutes = [
    { path: 'concept/:id', component: ConceptPageComponent},

    { path: 'student', component: MainStudentComponent},

    { path: 'teacher/:id', component: TeacherPageComponent},

    { path:'', redirectTo:'/', pathMatch:'full'},

    

];

export const routing = RouterModule.forRoot(appRoutes);
