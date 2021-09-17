package ru.otus.spring.repository;

import ru.otus.spring.domain.Question;
import ru.otus.spring.exeption.QuestionReadingException;

import java.util.List;

public interface QuestionRepository {
    List<Question> getQuestionList() throws QuestionReadingException;
}
