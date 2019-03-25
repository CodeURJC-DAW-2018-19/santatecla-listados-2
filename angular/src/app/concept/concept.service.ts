import {Injectable} from '@angular/core';

import {throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import 'rxjs/Rx';

import {Concept} from './concept.model';
import {HttpClient} from '@angular/common/http';

const BASE_URL = 'http://127.0.0.1:8080/api/concepts/';

@Injectable()
export class ConceptService {

    constructor(private http: HttpClient) {
    }

    getConcepts() {
        try {
            return this.http.get(BASE_URL);
        } catch {
            catchError(error => this.handleError(error));
        }

    }

    getConcept(id: number) {
        try {
            return this.http.get(BASE_URL + '/' + id);
        } catch {
            catchError(error => this.handleError(error));
        }

    }

    addConcept(concept: Concept) {
        try {
            return this.http.post(BASE_URL, concept);
        } catch {
            catchError(error => this.handleError(error));
        }
    }

    removeConcept(concept: Concept) {
        try {
            return this.http.delete(BASE_URL + '/' + concept.id);
        } catch {
            catchError(error => this.handleError(error));
        }
    }

    updateConcept(concept: Concept) {
        try {
            return this.http.put(BASE_URL + '/' + concept.id, concept);
        } catch {
            catchError(error => this.handleError(error));
        }
    }

    private handleError(error: any) {
        console.error(error);
        return throwError('Server error (' + error.status + '): ' + error.text());
    }
}
