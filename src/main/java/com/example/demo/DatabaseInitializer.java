package com.example.demo;

import javax.annotation.PostConstruct;

import com.example.demo.Answer.Answer;
import com.example.demo.Answer.AnswerRepository;
import com.example.demo.Concept.Concept;
import com.example.demo.Concept.ConceptRepository;
import com.example.demo.Item.Item;
import com.example.demo.Item.ItemRepository;
import com.example.demo.Question.Question;
import com.example.demo.Question.QuestionRepository;
import com.example.demo.Topic.Topic;
import com.example.demo.Topic.TopicRepository;
import com.example.demo.User.User;
import com.example.demo.User.UserComponent;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer {

	private static int numTopic; //en add de profesor debe de incrementarse
	@Autowired
	private UserComponent userComponent;
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
		Concept c1 = new Concept("Generics", "StudentConcept.mustache");
		Concept c2 = new Concept("Iteradores", "StudentConcept.mustache");
		Concept c3 = new Concept("JUNIT", "StudentConcept.mustache");
		Concept c4 = new Concept("Árboles LCRS", "StudentConcept.mustache");
		Concept c5 = new Concept("Árboles N-Arios", "StudentConcept.mustache");
		Concept c6 = new Concept("Árboles Binarios", "StudentConcept.mustache");
		Concept c7 = new Concept("Doble Hashing", "StudentConcept.mustache");
		Concept c8 = new Concept("Prueba Lineal", "StudentConcept.mustache");
		Concept c9 = new Concept("Prueba Cuadrátic", "StudentConcept.mustache");
		Question q1 = new Question("¿Que es un tipo generico?", "PType0", false);
		Question q2 = new Question("¿Donde hay que usar los tipos genericos?", "PType3", true);
		Answer a1 = new Answer("Respuesta Abierta 0", false);
		Answer a2 = new Answer("Respuesta Cerrada 1", true);
		Item i1 = new Item("Patron Singleton",false);
		Item i2 = new Item ("No tengo muy claro que poner",true);
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


	}


}
