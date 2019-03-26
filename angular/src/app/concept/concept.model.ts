export interface Concept{
    id?: number;
    name: string;
    html: string;
    errors: number;
    hits: number;
    pendings: number;
    topic:Topic;
    questions?:Set<Question>;
    items?:Set<items>;
    images?:Set<Image>;
}
