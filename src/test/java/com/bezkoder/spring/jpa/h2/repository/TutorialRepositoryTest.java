package com.bezkoder.spring.jpa.h2.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.bezkoder.spring.jpa.h2.model.Tutorial;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TutorialRepositoryTest {

	private static final String NAME = "NAME";
	private static final String UPDATED_NAME = "UPDATED_NAME";

	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void should_save_tutorial() {
		// Given
		Tutorial tutorial = givenTutorial();
		tutorial.setId(0);
		// When
		tutorial = tutorialRepository.save(tutorial);
		// Then
		Tutorial actual = testEntityManager.find(Tutorial.class, tutorial.getId());
		assertEquals(actual, tutorial);
	}

	@Test
	public void should_find_tutorial_by_id() {
		// Given
		Tutorial tutorial = givenTutorial();
		tutorial = testEntityManager.persist(tutorial);
		// When
		Optional<Tutorial> actual = tutorialRepository.findById(tutorial.getId());
		// Then
		assertNotEquals(actual, Optional.empty());
		assertEquals(actual.get(), tutorial);
	}

	@Test
	public void should_find_all_tutorials() {
		// Given
		Tutorial tutorial = givenTutorial();
		tutorial = testEntityManager.persist(tutorial);
		// When
		List<Tutorial> tutorials = tutorialRepository.findAll();
		// Then
		assertThat(tutorials).contains(tutorial);
	}

	@Test
	public void should_delete_tutorial_by_id() {
		// Given
		Tutorial tutorial = givenTutorial();
		tutorial = testEntityManager.persist(tutorial);
		// When
		tutorialRepository.deleteById(tutorial.getId());
		// Then
		Tutorial actual = testEntityManager.find(Tutorial.class, tutorial.getId());
		assertNull(actual);
	}

	@Test
	public void should_update_tutorial() {
		// Given
		Tutorial tutorial = givenTutorial();
		tutorial = testEntityManager.persist(tutorial);
		Tutorial updatedTutorial = givenUpdatedTutorial();
		updatedTutorial.setId(tutorial.getId());
		// When
		updatedTutorial = tutorialRepository.save(updatedTutorial);
		// Then
		Tutorial actual = testEntityManager.find(Tutorial.class, tutorial.getId());
		assertEquals(actual, updatedTutorial);

	}

	private Tutorial givenTutorial() {
		Tutorial tutorial = new Tutorial();
		tutorial.setId(0);
		tutorial.setTitle(NAME);
		return tutorial;
	}

	private Tutorial givenUpdatedTutorial() {
		Tutorial tutorial = new Tutorial();
		tutorial.setTitle(UPDATED_NAME);
		return tutorial;
	}
}
