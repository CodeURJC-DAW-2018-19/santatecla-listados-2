import {Topic} from '../topic/topic.model';

export interface Concept{
    id?: number;
    name: string;
    html?: string;
    errors?: number;
    hits?: number;
    pendings?: number;
    topic:Topic;
    questions?:Set<Question>;
    items?:Set<Question>;
    images?:Image[];
}
