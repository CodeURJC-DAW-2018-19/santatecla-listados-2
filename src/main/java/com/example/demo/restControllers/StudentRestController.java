package com.example.demo.restControllers;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerService;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptService;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionService;
import com.example.demo.uploadImages.ImageRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentRestController {
    @Autowired
    private ConceptService conceptService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    interface QuestionDetails extends Question.BasicInfo, Question.ConceptAndAnswer, Concept.BasicInfo, Answer.BasicInfo{}
    interface AnswerDetails extends Answer.BasicInfo, Answer.AnswerObject, Question.BasicInfo{}

    //Question region


    @JsonView(QuestionDetails.class)
    @PostMapping("/{conceptName}/{questionText}/{questionType}/{corrected}")
    public Question newConcreteQuestion(@PathVariable String conceptName, @PathVariable String questionText, @PathVariable String questionType, @PathVariable boolean corrected){
        Question question = new Question();
        Concept concept = conceptService.findOne(conceptName);
        question.setQuestion(questionText);
        question.setType(questionType);
        question.setCorrected(corrected);
        question.setConcept(concept);
        concept.setQuestion(question);

        conceptService.save(concept);

        return question;
    }

    @JsonView(QuestionDetails.class)
    @GetMapping("/questions/all")
    public List<Question> getQuestions(){
        return questionService.findAll();
    }


    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/updateText/{id}/{newText}", method = RequestMethod.PUT)
    public Question updateQuestionText(@PathVariable int id, @PathVariable String newText){
        Question question = questionService.findOne(id);
        question.setQuestion(newText);
        questionService.save(question);
        return question;
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/updateCorrected/{id}/{corrected}", method = RequestMethod.PUT)
    public Question updateQuestionCorrected(@PathVariable int id, @PathVariable boolean corrected){
        Question question = questionService.findOne(id);
        question.setCorrected(corrected);
        questionService.save(question);
        return question;
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/delete/{id}", method = RequestMethod.DELETE)
    public Question deleteQuestion(@PathVariable int id){
        Question question = questionService.findOne(id);
        questionService.delete(id);
        return question;
    }

    // end region

    //Answer region

    @JsonView(AnswerDetails.class)
    @PostMapping("/question/{questionId}/newAnswer/{openAnswer}/{mark}")
    public Answer newAnswer(@PathVariable int questionId, @PathVariable String openAnswer, @PathVariable boolean mark){
        Answer answer = new Answer();
        Question question = questionService.findOne(questionId);
        answer.setOpenAnswer(openAnswer);
        answer.setMark(mark);
        answer.setQuestion(question);
        question.setAnswer(answer);
        questionService.save(question);

        return answer;
    }

    @JsonView(AnswerDetails.class)
    @GetMapping("/answers/all")
    public List<Answer> getAnswers(){
        return answerService.findAll();
    }

    @JsonView(AnswerDetails.class)
    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable int id){
        Answer answer = answerService.findOne(id);
        return answer;
    }


    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/answer/updateOpenAnswer/{id}/{openAnswer}", method = RequestMethod.PUT)
    public Answer updateOpenAnswer(@PathVariable int id, @PathVariable String openAnswer){
        Answer answer = answerService.findOne(id);
        answer.setOpenAnswer(openAnswer);
        answerService.save(answer);
        return answer;
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/answer/updateAnswerMark/{id}/{mark}", method = RequestMethod.PUT)
    public Answer updateAnswerMark(@PathVariable int id, @PathVariable boolean mark){
        Answer answer = answerService.findOne(id);
        answer.setMark(mark);
        answerService.save(answer);
        return answer;
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/question{questionId}/deleteAnswer/{id}", method = RequestMethod.DELETE)
    public Answer deleteAnswer(@PathVariable int id, @PathVariable int questionId) {
        Question question = questionService.findOne(questionId);
        question.setAnswer(null);
        Answer answer = answerService.findOne(id);
        answerService.delete(id);
        return answer;
    }
    //end region

}
