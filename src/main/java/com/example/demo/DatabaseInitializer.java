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
		Question q1 = new Question("¿Que es un tipo generico?", "PType0", false);
		Question q2 = new Question("¿Donde hay que usar los tipos genericos?", "PType3", true);
		Answer a1 = new Answer("Verdadero ", false);
		Answer a2 = new Answer("Falseo ", true);

		Item i1 = new Item("item1",false);
		Item i2 = new Item ("item2",true);
		Item i3 = new Item("item3",false);
		Item i4 = new Item ("item4",true);
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
		c1.setQuestion(q2);
		q1.setConcept(c1);
		q2.setConcept(c1);

		q1.setAnswer(a1);
		q2.setAnswer(a2);
		a1.setQuestion(q1);
		a2.setQuestion(q2);

		c1.setItem(i1);
		c1.setItem(i2);
		c1.setItem(i3);
		c1.setItem(i4);
		i1.setConcept(c1);
		i2.setConcept(c1);
		i3.setConcept(c1);
		i4.setConcept(c1);
		c1.setPendings(c1.getPendings()+1);
		c1.setHits(c1.getHits()+1);
		c1.getTopic().setPendings(c1.getTopic().getPendings()+1);
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
		questionRepository.save(q2);
		answerRepository.save(a1);
		answerRepository.save(a2);
		itemRepository.save(i1);
		itemRepository.save(i2);
		itemRepository.save(i3);
		itemRepository.save(i4);
	}


}
