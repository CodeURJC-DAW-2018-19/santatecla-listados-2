import {Component, OnInit} from "@angular/core";
import {Topic} from "../topic/topic.model";
import {TopicService} from "../topic/topic.service";
import {Router} from "@angular/router";

@Component({
    templateUrl:'MainStudent.template.html'
})

export class MainStudentComponent implements OnInit{
    topics: Topic[];

    constructor(private topicService:TopicService, private router:Router) {
    }

    ngOnInit(): void {
        this.refresh();
    }

    private refresh(){
        this.topicService.getTopics().subscribe(
            topics => this.topics = topics,
            error => console.log(error)
        );
    }
}