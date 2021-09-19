package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.configuration.ApplicationConfiguration;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Component
@AllArgsConstructor
public class MessageSourceServiceImpl implements MessageSourceService {

    private final ApplicationConfiguration configuration;
    private final MessageSource messageSource;

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, configuration.getLocale());
    }
}
