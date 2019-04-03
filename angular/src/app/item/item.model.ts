import {Concept} from '../concept/concept.model';

export interface Item{
    id?:number;
    name:string;
    correct:boolean;
    concept:Concept;

}
