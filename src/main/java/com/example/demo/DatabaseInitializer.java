package com.example.demo;

import javax.annotation.PostConstruct;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.concept.Concept;
import com.example.demo.concept.ConceptRepository;
import com.example.demo.item.Item;
import com.example.demo.item.ItemRepository;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import com.example.demo.topic.Topic;
import com.example.demo.topic.TopicRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer {

	private static int numTopic =1;
	@Autowired
	private ConceptRepository conceptRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct
	public void init() {
		Topic t1 = new Topic("Introducción",numTopic++);
		Topic t2 = new Topic("Árboles",numTopic++);
		Topic t3 = new Topic("Mapas",numTopic++);
		Concept c1 = new Concept("Generics", "StudentConcept.html");
		Concept c2 = new Concept("Iteradores", "StudentConcept.html");
		Concept c3 = new Concept("JUNIT", "StudentConcept.html");
		Concept c4 = new Concept("Árboles LCRS", "StudentConcept.html");
		Concept c5 = new Concept("Árboles N-Arios", "StudentConcept.html");
		Concept c6 = new Concept("Árboles Binarios", "StudentConcept.html");
		Concept c7 = new Concept("Doble Hashing", "StudentConcept.html");
		Concept c8 = new Concept("Prueba Lineal", "StudentConcept.html");
		Concept c9 = new Concept("Prueba Cuadrática", "StudentConcept.html");
		Question q1 = new Question("¿Que es un tipo generico?", 0, false);
		Question q13 = new Question("¿Que es un tipo generico?", 0, false);
		Question q3 = new Question("¿Que es un tipo generico?", 0, false);
		Question q4 = new Question("¿Que es un tipo generico?", 0, false);
		Question q5 = new Question("¿Que es un tipo generico?", 0, false);
		Question q6 = new Question("¿Que es un tipo generico?", 0, false);
		Question q7 = new Question("¿Que es un tipo generico?", 0, false);
		Question q8 = new Question("¿Que es un tipo generico?", 0, false);
		Question q9 = new Question("¿Que es un tipo generico?", 0, false);
		Question q10 = new Question("¿Que es un tipo generico?", 0, false);
		Question q11= new Question("¿Que es un tipo generico?", 0, false);
		Question q12 = new Question("¿Que es un tipo generico?", 0, false);


		Question q2 = new Question("¿Donde hay que usar los tipos genericos?", 3, true);
		Answer a1 = new Answer("Verdadero ", false);
		Answer a13 = new Answer("Verdadero ", false);
		Answer a3 = new Answer("Verdadero ", false);
		Answer a4 = new Answer("Verdadero ", false);
		Answer a5 = new Answer("Verdadero ", false);
		Answer a6 = new Answer("Verdadero ", false);
		Answer a7 = new Answer("Verdadero ", false);
		Answer a8 = new Answer("Verdadero ", false);
		Answer a9 = new Answer("Verdadero ", false);
		Answer a10 = new Answer("Verdadero ", false);
		Answer a11 = new Answer("Verdadero ", false);
		Answer a12 = new Answer("Verdadero ", false);
		Answer a2 = new Answer("Falseo ", true);

		Item i1 = new Item("item1",false);
		Item i2 = new Item ("item2",true);
		Item i3 = new Item("item3",false);
		Item i4 = new Item ("item4",true);
		Item i5 = new Item("item1",false);
		Item i6 = new Item ("item2",true);
		Item i7 = new Item("item3",false);
		Item i8 = new Item ("item4",true);
		Item i9 = new Item("item1",false);
		Item i10 = new Item ("item2",true);
		Item i11 = new Item("item3",false);
		Item i12 = new Item ("item4",true);
		User u1= new User("Alberto","123456789","Albertocalib_8","ROLE_STUDENT");
		User u2= new User("Alberto","123456789","a.canal.2016","ROLE_TEACHER");
		User u3= new User("Haritz","123456789","yo","ROLE_TEACHER");
		userRepository.save(u1);
		userRepository.save(u2);
		userRepository.save(u3);


		t1.setConcept(c1);
		t1.setConcept(c2);
		t1.setConcept(c3);
		c1.setTopic(t1);
		c2.setTopic(t1);
		c3.setTopic(t1);

		t2.setConcept(c4);
		t2.setConcept(c5);
		t2.setConcept(c6);
		c4.setTopic(t2);
		c5.setTopic(t2);
		c6.setTopic(t2);

		t3.setConcept(c7);
		t3.setConcept(c8);
		t3.setConcept(c9);
		c7.setTopic(t3);
		c8.setTopic(t3);
		c9.setTopic(t3);

		c1.setQuestion(q1);
		c1.setQuestion(q13);
		c1.setQuestion(q3);
		c1.setQuestion(q4);
		c1.setQuestion(q5);
		c1.setQuestion(q6);
		c1.setQuestion(q7);
		c1.setQuestion(q8);
		c1.setQuestion(q9);
		c1.setQuestion(q10);
		c1.setQuestion(q11);
		c1.setQuestion(q12);
		c1.setQuestion(q2);
		q1.setConcept(c1);
		q13.setConcept(c1);
		q3.setConcept(c1);
		q4.setConcept(c1);
		q5.setConcept(c1);
		q6.setConcept(c1);
		q7.setConcept(c1);
		q8.setConcept(c1);
		q9.setConcept(c1);
		q10.setConcept(c1);
		q11.setConcept(c1);
		q12.setConcept(c1);
		q2.setConcept(c1);

		q1.setAnswer(a1);
		q13.setAnswer(a13);
		q3.setAnswer(a3);
		q4.setAnswer(a4);
		q5.setAnswer(a5);
		q6.setAnswer(a6);
		q7.setAnswer(a7);
		q8.setAnswer(a8);
		q9.setAnswer(a9);
		q10.setAnswer(a10);
		q11.setAnswer(a11);
		q12.setAnswer(a12);
		q2.setAnswer(a2);
		a1.setQuestion(q1);
		a13.setQuestion(q13);
		a3.setQuestion(q3);
		a4.setQuestion(q4);
		a5.setQuestion(q5);
		a6.setQuestion(q6);
		a7.setQuestion(q7);
		a8.setQuestion(q8);
		a9.setQuestion(q9);
		a10.setQuestion(q10);
		a11.setQuestion(q11);
		a12.setQuestion(q12);
		a2.setQuestion(q2);

		c1.setItem(i1);
		c1.setItem(i2);
		c1.setItem(i3);
		c1.setItem(i4);
		c1.setItem(i5);
		c1.setItem(i6);
		c1.setItem(i7);
		c1.setItem(i8);
		c1.setItem(i9);
		c1.setItem(i10);
		c1.setItem(i11);
		c1.setItem(i12);
		i1.setConcept(c1);
		i2.setConcept(c1);
		i3.setConcept(c1);
		i4.setConcept(c1);
		i5.setConcept(c1);
		i6.setConcept(c1);
		i7.setConcept(c1);
		i8.setConcept(c1);
		i9.setConcept(c1);
		i10.setConcept(c1);
		i11.setConcept(c1);
		i12.setConcept(c1);
		c1.setPendings(c1.getPendings()+12);
		c1.setHits(c1.getHits()+1);
		c1.getTopic().setPendings(c1.getTopic().getPendings()+12);
		c1.getTopic().setHits(c1.getTopic().getHits()+1);

		topicRepository.save(t1);
		topicRepository.save(t2);
		topicRepository.save(t3);
		conceptRepository.save(c1);
		conceptRepository.save(c2);
		conceptRepository.save(c3);
		conceptRepository.save(c4);
		conceptRepository.save(c5);
		conceptRepository.save(c6);
		conceptRepository.save(c7);
		conceptRepository.save(c8);
		conceptRepository.save(c9);
		questionRepository.save(q1);
		questionRepository.save(q13);
		questionRepository.save(q3);
		questionRepository.save(q4);
		questionRepository.save(q5);
		questionRepository.save(q6);
		questionRepository.save(q7);
		questionRepository.save(q8);
		questionRepository.save(q9);
		questionRepository.save(q10);
		questionRepository.save(q11);
		questionRepository.save(q12);
		questionRepository.save(q2);
		answerRepository.save(a1);
		answerRepository.save(a13);
		answerRepository.save(a2);
		answerRepository.save(a3);
		answerRepository.save(a4);
		answerRepository.save(a5);
		answerRepository.save(a6);
		answerRepository.save(a7);
		answerRepository.save(a8);
		answerRepository.save(a9);
		answerRepository.save(a10);
		answerRepository.save(a11);
		answerRepository.save(a12);
		answerRepository.save(a2);
		itemRepository.save(i1);
		itemRepository.save(i2);
		itemRepository.save(i3);
		itemRepository.save(i4);
		itemRepository.save(i5);
		itemRepository.save(i6);
		itemRepository.save(i7);
		itemRepository.save(i8);
		itemRepository.save(i9);
		itemRepository.save(i10);
		itemRepository.save(i11);
		itemRepository.save(i12);
	}


}
