package com.bezkoder.spring.jpa.h2.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bezkoder.spring.jpa.h2.controller.helper.JsonHelper;
import com.bezkoder.spring.jpa.h2.model.Tutorial;
import com.bezkoder.spring.jpa.h2.repository.TutorialRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TutorialControllerMvcTest {

	private static final long ID = 1;
	private static final String NAME = "NAME";

	private static final String TUTORIAL_URL = "/api/tutorials";
	private static final String TUTORIAL_ID_URL = "/api/tutorials/{id}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TutorialRepository tutorialRepository;

	// @Test
	public void should_find_all_tutorials_returns_200() throws Exception {
		// Given
		Tutorial tutorial = givenTutorial();
		List<Tutorial> tutorials = Collections.singletonList(tutorial);
		when(tutorialRepository.findAll()).thenReturn(tutorials);
		// When
		String expected = JsonHelper.toJson(tutorials).orElse("");
		// Then
		mockMvc.perform(get(TUTORIAL_URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(expected));
	}

	// @Test
	public void should_find_tutorial_by_id_returns_200() throws Exception {
		// Given
		Tutorial tutorial = givenTutorial();
		when(tutorialRepository.findById(ID)).thenReturn(Optional.of(tutorial));
		// When
		String expected = JsonHelper.toJson(tutorial).orElse("");
		// Then
		mockMvc.perform(get(TUTORIAL_ID_URL, ID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(expected));
	}

	@Test
	public void should_delete_tutorial_by_id_returns_204() throws Exception {
		// Given
		doNothing().when(tutorialRepository).deleteById(ID);
		// When
		// Then
		mockMvc.perform(delete(TUTORIAL_ID_URL, ID).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	private Tutorial givenTutorial() {
		Tutorial tutorial = new Tutorial();
		tutorial.setId(ID);
		tutorial.setTitle(NAME);
		return tutorial;
	}
}
