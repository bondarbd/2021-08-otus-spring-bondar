package ru.otus.spring.domain;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @CsvBindByPosition(position = 0, required = true)
    private String question;

    @CsvBindAndSplitByPosition(position = 1, elementType = String.class, splitOn = ";", required = true)
    private List<String> answerList;

    @CsvBindByPosition(position = 2, required = true)
    private Integer rightAnswer;

    public String getFormatterQuestion(){
        StringBuilder formattedAnswer = new StringBuilder();
        for (int i = 0; i < answerList.size(); i++) {
            formattedAnswer.append(i + 1).append(")").append(answerList.get(i)).append("\n");
        }
        return String.format(question + "\n" + formattedAnswer);
    }
}
