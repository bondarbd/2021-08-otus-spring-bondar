package ru.otus.spring.service;

/**
 * @author Denis Bondar (/bondarbd)
 */
public interface IOService {
    String read(String question);
    void write(String message);
}
