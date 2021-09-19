package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionResult;

/**
 * @author Denis Bondar (/bondarbd)
 */
public interface QuestionResultService {
    QuestionResult answerQuestion(Question question, Integer questionNumber);
}
