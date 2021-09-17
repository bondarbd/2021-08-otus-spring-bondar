package ru.otus.spring.repository;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.spring.configuration.ApplicationConfiguration;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exeption.QuestionReadingException;

import java.nio.charset.StandardCharsets;
import java.util.List;

@DisplayName("Check question load from CSV file")
class QuestionRepositoryCSVTest {

    private static final String QUESTION = "Is it true?";
    private static final String ANSWER_1 = "yes";
    private static final String ANSWER_2 = "no";
    private static final Integer RIGHT_ANSWER = 1;
    private static final String LIST_SEPARATOR = ";";
    private static final String COLUMN_SEPARATOR = ",";
    private static final String FULL_QUESTION
            = QUESTION
            + COLUMN_SEPARATOR
            + ANSWER_1
            + LIST_SEPARATOR
            + ANSWER_2
            + COLUMN_SEPARATOR
            + RIGHT_ANSWER;
    private static final String NOT_VALID_QUESTION
            = QUESTION
            + ANSWER_2
            + COLUMN_SEPARATOR
            + RIGHT_ANSWER;
    private static final String FILE_NAME = "mock-file";

    @Test
    @SneakyThrows
    @DisplayName("Question successfully parsed from csv")
    void parseQuestionFromCSV() {
        ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
        ApplicationConfiguration applicationConfiguration = Mockito.mock(ApplicationConfiguration.class);
        Question questionMock = new Question(QUESTION, List.of(ANSWER_1, ANSWER_2), RIGHT_ANSWER);

        Mockito.when( resourceLoader.getResource("classpath:" + FILE_NAME))
                .thenReturn(new ByteArrayResource(FULL_QUESTION.getBytes(StandardCharsets.UTF_8)));
        Mockito.when(applicationConfiguration.getTestFileName()).thenReturn(FILE_NAME);

        QuestionRepository questionRepository = new QuestionRepositoryImpl(applicationConfiguration, resourceLoader);

        List<Question> questionListCSV = questionRepository.getQuestionList();
        Assertions.assertThat(questionListCSV.get(0)).hasToString(questionMock.toString());
    }

    @Test
    @SneakyThrows
    @DisplayName("Question repository throw Exception while not valid csv")
    void checkQuestionParseExceptionThrow(){
        ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
        ApplicationConfiguration applicationConfiguration = Mockito.mock(ApplicationConfiguration.class);

        Mockito.when( resourceLoader.getResource("classpath:" + FILE_NAME))
                .thenReturn(new ByteArrayResource(NOT_VALID_QUESTION.getBytes(StandardCharsets.UTF_8)));
        Mockito.when(applicationConfiguration.getTestFileName()).thenReturn(FILE_NAME);
        QuestionRepository questionRepository = new QuestionRepositoryImpl(applicationConfiguration, resourceLoader);

        Assertions.assertThatExceptionOfType(QuestionReadingException.class)
                .isThrownBy(questionRepository::getQuestionList);
    }

    @Test
    @SneakyThrows
    @DisplayName("Question repository throw Exception while file not found")
    void checkIOExceptionWrappedWhileQuestionParse(){
        ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
        ApplicationConfiguration applicationConfiguration = Mockito.mock(ApplicationConfiguration.class);

        Mockito.when( resourceLoader.getResource("classpath:"))
                .thenReturn(new ByteArrayResource(NOT_VALID_QUESTION.getBytes(StandardCharsets.UTF_8)));
        Mockito.when(applicationConfiguration.getTestFileName()).thenReturn(FILE_NAME);
        QuestionRepository questionRepository = new QuestionRepositoryImpl(applicationConfiguration, resourceLoader);

        Assertions.assertThatExceptionOfType(QuestionReadingException.class)
                .isThrownBy(questionRepository::getQuestionList);
    }
}
