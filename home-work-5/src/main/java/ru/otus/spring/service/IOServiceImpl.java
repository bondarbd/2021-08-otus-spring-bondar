package ru.otus.spring.service;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Component
public class IOServiceImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream printStream;

    public IOServiceImpl() {
        this.scanner = new Scanner(System.in);
        this.printStream = System.out;
    }

    @Override
    public String read(String question) {
        printStream.println(question);
        return scanner.next();
    }

    @Override
    public void write(String message) {
        printStream.println(message);
    }
}
