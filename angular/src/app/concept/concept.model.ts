import {Topic} from '../topic/topic.model';
import {Question} from "../question/question.model";
import {Item} from "../item/item.model";

export interface Concept{
    id?: number;
    name: string;
    html?: string;
    errors?: number;
    hits?: number;
    pendings?: number;
    topic:Topic;
    questions?:Set<Question>;
    items?:Set<Item>;
    //images?:Set<Image>;
}
