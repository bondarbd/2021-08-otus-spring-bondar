package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionResult;

/**
 * @author Denis Bondar (/bondarbd)
 */

@Service
@AllArgsConstructor
public class QuestionResultServiceImpl implements QuestionResultService {

    private final UIService uiService;
    private final MessageSourceService messageSourceService;

    @Override
    public QuestionResult answerQuestion(Question question, Integer questionNumber) {
        uiService.write(messageSourceService.getMessage("message.test.question") + " " + questionNumber);
        uiService.write(question.getFormatterQuestion());
        Integer studentAnswer = readStudentAnswer(question.getAnswerList().size());
        return QuestionResult.builder().question(question).studentAnswer(studentAnswer).build();
    }

    private Integer readStudentAnswer(Integer answerListSize){
        var answer = uiService.read(messageSourceService.getMessage("message.test.choose"));
        try{
            int answerInd = Integer.parseInt(answer);
            if(answerInd < 0 || answerInd > answerListSize){
                uiService.write(messageSourceService.getMessage("message.test.incorrect-choose"));
                return  readStudentAnswer(answerListSize);
            }
            else return answerInd;
        }catch (Exception exception){
            uiService.write(messageSourceService.getMessage("message.test.incorrect-choose"));
            return  readStudentAnswer(answerListSize);
        }
    }
}
