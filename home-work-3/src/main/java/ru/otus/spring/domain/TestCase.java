package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestCase {
    private Student student;
    private List<QuestionResult> questionResultList;
}
