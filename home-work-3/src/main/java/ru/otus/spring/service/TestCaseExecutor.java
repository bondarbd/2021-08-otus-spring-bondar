package ru.otus.spring.service;

import ru.otus.spring.domain.TestCase;
import ru.otus.spring.exeption.QuestionReadingException;

public interface TestCaseExecutor {
    TestCase executeTest() throws QuestionReadingException;
}
