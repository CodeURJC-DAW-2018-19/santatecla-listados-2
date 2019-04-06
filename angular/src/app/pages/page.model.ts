import { SortPage } from './sortPage.model';

export interface Page<T>{
    size: number;
    number: number;
    totalElements: number;
    last: boolean;
    totalPages: number;
    sort: SortPage;
    first: boolean;
    numberOfElements: number;
    content: T[];
}