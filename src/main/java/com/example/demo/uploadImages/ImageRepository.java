package com.example.demo.uploadImages;
import com.example.demo.concept.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    Optional<Image> findById(Integer integer);
    List<Image> findAllByConceptId(Integer integer);
    Image findByTitle(String s);
    List<Image> findByConcept(Concept c);
}
