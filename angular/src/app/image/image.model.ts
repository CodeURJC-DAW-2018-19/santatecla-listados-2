import {Concept} from "../concept/concept.model";

export interface Image{
    id?: number;
    title: string;
    base64: string;
    concept:Concept;
}
