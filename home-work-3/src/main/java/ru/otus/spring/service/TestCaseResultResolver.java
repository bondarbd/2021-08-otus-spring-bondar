package ru.otus.spring.service;

import ru.otus.spring.domain.TestCase;

public interface TestCaseResultResolver {
    Boolean getTestCaseResult(TestCase testCase);
}
