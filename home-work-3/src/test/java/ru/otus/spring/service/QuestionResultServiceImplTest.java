package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Check that question result service")
class QuestionResultServiceImplTest {

    @MockBean
    private MessageSourceService messageSourceService;
    @MockBean
    private UIService uiService;
    @Autowired
    QuestionResultService questionResultService;

    private static final String INPUT_ANSWER ="1";
    private static final String MOCK_MESSAGE ="mock message";
    private static final Question QUESTION
            = new Question(MOCK_MESSAGE, List.of("1", "2"), 1);

    @Test
    @DisplayName("is the same as ui read input")
    void checkInputIsEqualQuestionResult(){
        Mockito.when(uiService.read(Mockito.anyString())).thenReturn(INPUT_ANSWER);
        Mockito.when(messageSourceService.getMessage(Mockito.anyString())).thenReturn(MOCK_MESSAGE);
        QuestionResult questionResult = questionResultService.answerQuestion(QUESTION, null);
        Assertions.assertThat(questionResult.getStudentAnswer()).isEqualTo(Integer.parseInt(INPUT_ANSWER));
    }
}
