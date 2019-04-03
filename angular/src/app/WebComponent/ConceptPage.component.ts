import {Component } from "@angular/core";
import {Concept} from "../concept/concept.model";
import {Router, ActivatedRoute} from "@angular/router";
import {ConceptService} from "../concept/concept.service";


@Component({
    templateUrl:'ConceptPage.template.html'

})

export class ConceptPageComponent {
        concept: Concept;

        constructor (private router: Router,
                     private activatedRoute:ActivatedRoute,
                     private conceptService: ConceptService){
            const id = activatedRoute.snapshot.params['id'];
            conceptService.getConcept(id).subscribe(
                (concept) => (this.concept=concept),
                error => console.log(error)
            )
        }
}