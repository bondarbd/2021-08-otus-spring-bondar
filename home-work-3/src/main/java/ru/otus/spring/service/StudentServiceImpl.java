package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

/**
 * @author Denis Bondar (/bondarbd)
 */

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UIService uiService;
    private final MessageSourceService messageSource;

    @Override
    public Student getStudent() {
        var firstName = uiService.read(messageSource.getMessage("message.student.enter-first-name"));
        var lastName = uiService.read(messageSource.getMessage("message.student.enter-last-name"));
        return new Student(firstName, lastName);
    }
}
