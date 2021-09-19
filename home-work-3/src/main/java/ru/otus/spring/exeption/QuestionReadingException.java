package ru.otus.spring.exeption;

/**
 * @author Denis Bondar (/bondarbd)
 */
public class QuestionReadingException extends Exception {
    public QuestionReadingException(String message) {
        super(message);
    }
}
