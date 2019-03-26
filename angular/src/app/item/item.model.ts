import {concept} from '../concept/concept.component';

export interface Item{
    id?:number;
    name:string;
    correct:boolean;
    concept:concept;

}
