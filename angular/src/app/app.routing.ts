import { Routes, RouterModule } from '@angular/router';
import {ConceptPageComponent} from "./WebComponent/ConceptPage.component";
import {MainStudentComponent} from "./WebComponent/MainStudent.component";
import {AppComponent} from "./app.component";


const appRoutes = [
    { path: 'concept/:id', component: ConceptPageComponent},

    { path: 'student', component: MainStudentComponent},

    { path:'', redirectTo:'/', pathMatch:'full'},

    

];

export const routing = RouterModule.forRoot(appRoutes);
