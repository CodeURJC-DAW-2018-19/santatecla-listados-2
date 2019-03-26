import {Concept} from '../concept/concept.model';

export interface Topic{
    id?:number;
    name:string;
    errors?:number;
    hits?:number;
    pendings?:number;
    concepts?:Set<Concept>;
}




