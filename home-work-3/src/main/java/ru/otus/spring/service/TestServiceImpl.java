package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.TestCase;
import ru.otus.spring.exeption.QuestionReadingException;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Service
@AllArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {

    private final TestCaseExecutor caseExecutor;
    private final TestCaseResultResolver resultResolver;

    public void runTest() {
        try {
            TestCase testCase = caseExecutor.executeTest();
            resultResolver.getTestCaseResult(testCase);
        } catch (QuestionReadingException e) {
            log.error(e.getMessage());
        }

    }
}
