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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/questions/all")
    public List<Question> getQuestions(){
        return questionService.findAll();
    }

    @JsonView(QuestionDetails.class)
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id){

        Question question = questionService.findOne(id);

        if (question != null){
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/newQuestion", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Question newQuestion(@RequestBody Question question) {

        questionService.save(question);

        return question;
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/updateQuestion/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestion(@PathVariable int id, @RequestBody Question updatedQuestion){
        Question question = questionService.findOne(id);

        if(question != null){
            updatedQuestion.setId(id);
            questionService.save(updatedQuestion);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id){
        Question question = questionService.findOne(id);

        if (question != null){
            questionService.delete(id);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


        //New Question using UrRL parameters
    @JsonView(QuestionDetails.class)
    @PostMapping("/{conceptName}/{questionText}/{questionType}/{corrected}")
    public ResponseEntity<Question> newConcreteQuestion(@PathVariable String conceptName, @PathVariable String questionText, @PathVariable String questionType, @PathVariable boolean corrected){
        Concept concept = conceptService.findOne(conceptName);

        if (concept != null) {
            Question question = new Question();
            question.setQuestion(questionText);
            question.setType(questionType);
            question.setCorrected(corrected);
            question.setConcept(concept);
            concept.setQuestion(question);

            conceptService.save(concept);
            return new ResponseEntity<>(question, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        //Update Questions using  URL parameters
    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/updateText/{id}/{newText}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestionText(@PathVariable int id, @PathVariable String newText){
        Question question = questionService.findOne(id);

        if (question != null){
            question.setQuestion(newText);
            questionService.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(QuestionDetails.class)
    @RequestMapping(value = "/question/updateCorrected/{id}/{corrected}", method = RequestMethod.PUT)
    public ResponseEntity<Question> updateQuestionCorrected(@PathVariable int id, @PathVariable boolean corrected){
        Question question = questionService.findOne(id);

        if (question != null){
            question.setCorrected(corrected);
            questionService.save(question);
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // end region

    //Answer region

    @JsonView(AnswerDetails.class)
    @GetMapping("/answers/all")
    public List<Answer> getAnswers(){
        return answerService.findAll();
    }

    @JsonView(AnswerDetails.class)
    @GetMapping("/answer/{id}")
    public ResponseEntity<Answer> getAnswer(@PathVariable int id){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/newAnswer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Answer newAnswer(@RequestBody Answer answer) {

        answerService.save(answer);

        return answer;
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/updateAnswer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateAnswer(@PathVariable int id, @RequestBody Answer updatedAnswer){
        Answer answer = answerService.findOne(id);

       if(answer != null){
           updatedAnswer.setId(id);
           answerService.save(updatedAnswer);
           return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/question{questionId}/deleteAnswer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Answer> deleteAnswer(@PathVariable int id, @PathVariable int questionId) {
        Question question = questionService.findOne(questionId);
        Answer answer = answerService.findOne(id);
        if (question != null) {
            if (answer != null) {
                question.setAnswer(null);
                answerService.delete(id);
                return new ResponseEntity<>(answer, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        //New Answers using UrRL parameters
    @JsonView(AnswerDetails.class)
    @PostMapping("/question/{questionId}/newAnswer/{openAnswer}/{mark}")
    public ResponseEntity<Answer> newConcreteAnswer(@PathVariable int questionId, @PathVariable String openAnswer, @PathVariable boolean mark){

        Question question = questionService.findOne(questionId);

        if (question != null) {
            Answer answer = new Answer();
            answer.setOpenAnswer(openAnswer);
            answer.setMark(mark);
            answer.setQuestion(question);
            question.setAnswer(answer);
            questionService.save(question);
            return new ResponseEntity<>(answer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

        //Update Answers using  URL parameters
    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/answer/updateOpenAnswer/{id}/{openAnswer}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateOpenAnswer(@PathVariable int id, @PathVariable String openAnswer){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            answer.setOpenAnswer(openAnswer);
            answerService.save(answer);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(AnswerDetails.class)
    @RequestMapping(value = "/answer/updateAnswerMark/{id}/{mark}", method = RequestMethod.PUT)
    public ResponseEntity<Answer> updateAnswerMark(@PathVariable int id, @PathVariable boolean mark){
        Answer answer = answerService.findOne(id);

        if(answer != null){
            answer.setMark(mark);
            answerService.save(answer);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //end region
}
