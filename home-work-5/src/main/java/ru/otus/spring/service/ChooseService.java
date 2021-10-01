package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Denis Bondar (/bondarbd)
 */

@Service
@AllArgsConstructor
public class ChooseService {
    private final IOService ioService;
    private final NumberReaderService numberReaderService;
}
