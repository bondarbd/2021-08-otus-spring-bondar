package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionResult;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@DisplayName("Check test case result")
class TestCaseResultResolverImplTest {

    private static final String MOCK_MESSAGE = "Mock message";
    private static final List<Question> QUESTION_LIST = List.of(
            new Question(MOCK_MESSAGE, new ArrayList<>(), 1),
            new Question(MOCK_MESSAGE, new ArrayList<>(), 1),
            new Question(MOCK_MESSAGE, new ArrayList<>(), 1),
            new Question(MOCK_MESSAGE, new ArrayList<>(), 1));
    private static final Student STUDENT = new Student("Denis", "Bondar");
    private static final Integer FALSE_STUDENT_ANSWER = 2;
    private static final Integer RIGHT_STUDENT_ANSWER = 1;

    @MockBean
    private UIService uiService;

    @MockBean
    private MessageSourceService messageSourceService;

    @Autowired
    private TestCaseResultResolver resultResolver;

    @Test
    @DisplayName("If all answers is correct result resolver is true")
    void verifyTestCaseResultIsTrue(){
        Mockito.when(messageSourceService.getMessage(Mockito.anyString())).thenReturn(MOCK_MESSAGE);
        List<QuestionResult> questionResultList
                = QUESTION_LIST.stream()
                .map(qn -> new QuestionResult(qn, RIGHT_STUDENT_ANSWER))
                .collect(Collectors.toList());
        TestCase testCase = new TestCase(STUDENT, questionResultList);
        Assertions.assertThat(resultResolver.getTestCaseResult(testCase)).isTrue();
    }

    @Test
    @DisplayName("If all answers is incorrect result resolver is false")
    void verifyTestCaseResultIsFalse(){
        Mockito.when(messageSourceService.getMessage(Mockito.anyString())).thenReturn(MOCK_MESSAGE);
        List<QuestionResult> questionResultList
                = QUESTION_LIST.stream()
                .map(qn -> new QuestionResult(qn, FALSE_STUDENT_ANSWER))
                .collect(Collectors.toList());
        TestCase testCase = new TestCase(STUDENT, questionResultList);
        Assertions.assertThat(resultResolver.getTestCaseResult(testCase)).isFalse();
    }
}
