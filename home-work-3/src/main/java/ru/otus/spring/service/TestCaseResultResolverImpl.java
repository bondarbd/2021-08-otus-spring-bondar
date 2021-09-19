package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.TestCase;

import java.util.Objects;

/**
 * @author Denis Bondar (/bondarbd)
 */

@Component
@AllArgsConstructor
public class TestCaseResultResolverImpl implements TestCaseResultResolver {

    private final UIService uiService;
    private final MessageSourceService messageSourceService;

    @Override
    public Boolean getTestCaseResult(TestCase testCase) {
        long rightAnswerCount = testCase.getQuestionResultList().stream()
                .filter(questionResult
                        -> Objects.equals(
                                questionResult.getStudentAnswer(),
                                questionResult.getQuestion().getRightAnswer()))
                .count();
        uiService.write(messageSourceService.getMessage("message.test.end")+ rightAnswerCount);
        uiService.write(testCase.getStudent().getFirstName() + " " + testCase.getStudent().getLastName());
        if(rightAnswerCount >= testCase.getQuestionResultList().size() * 0.75){
            uiService.write(messageSourceService.getMessage("message.test.passed"));
            uiService.write(messageSourceService.getMessage("message.test.thanks"));
            return true;
        }else {
            uiService.write(messageSourceService.getMessage("message.test.no-passed"));
            uiService.write(messageSourceService.getMessage("message.test.thanks"));
            return false;
        }
    }
}
