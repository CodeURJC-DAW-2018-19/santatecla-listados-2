import {Concept} from './concept.model';
import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {ConceptService} from './concept.service';

@Component({
    selector: 'app-child',
    template: './concept.component.ts',
})

export class concept {

    concept: Concept;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private service: ConceptService) {
        let id = activatedRoute.snapshot.params['id'];
        service.getConcept(id).subscribe(
            concept => this.concept = concept,
            error => console.error(error)
        );
    }

    removeConcept() {
        let okResponse = window.confirm("Do you want to remove this Concept?");
        if (okResponse) {
            this.service.removeConcept(this.concept).subscribe(
                concept => this.router.navigate(['/api/concepts']),
                error => console.error(error)
            )
        }
    }

    editConcept() {
        this.router.navigate(['/api/concepts/edit', this.concept.id ]);
    }

    gotoConcepts() {
        this.router.navigate(['/api/concepts']);
    }
}
