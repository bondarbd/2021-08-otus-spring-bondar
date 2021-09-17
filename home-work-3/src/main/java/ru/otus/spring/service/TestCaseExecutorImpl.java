package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionResult;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestCase;
import ru.otus.spring.exeption.QuestionReadingException;
import ru.otus.spring.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Service
@AllArgsConstructor
public class TestCaseExecutorImpl implements TestCaseExecutor {

    private final QuestionResultService questionResultService;
    private final QuestionRepository questionRepository;
    private final UIService uiService;
    private final MessageSourceService messageSourceService;
    private final StudentService studentService;

    @Override
    public TestCase executeTest() throws QuestionReadingException {
        Student student = studentService.getStudent();
        uiService.write(messageSourceService.getMessage("message.test.start.description"));
        List<QuestionResult> questionResultList = new ArrayList<>();
        List<Question> questionList = questionRepository.getQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            questionResultList.add(questionResultService.answerQuestion(questionList.get(i), i + 1));
        }
        return TestCase.builder()
                .questionResultList(questionResultList)
                .student(student)
                .build();
    }
}
