package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResult {
    private Question question;
    private Integer studentAnswer;
}
