package ru.otus.spring.service;

/**
 * @author Denis Bondar (/bondarbd)
 */
public interface NumberReaderService {
    long readNumber(long limitNumber);
    long readNumber(Long... availableNumbers);
}
