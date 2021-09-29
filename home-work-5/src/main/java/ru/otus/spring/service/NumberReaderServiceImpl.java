package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Component
@AllArgsConstructor
public class NumberReaderServiceImpl implements NumberReaderService {

    private static final String  INPUT_MESSAGE = "Для выбора введите значение: ";
    private static final String INCORRECT_INPUT_MESSAGE = "Некорректное значение, повторите ввод: ";
    private final IOService ioService;

    @Override
    public long readNumber(long limitNumber) {
        try{
            String currentNum = ioService.read(INPUT_MESSAGE);
            long answerInd = Long.parseLong(currentNum);
            if(answerInd < 0 || answerInd > limitNumber){
                ioService.write(INCORRECT_INPUT_MESSAGE);
                return readNumber(limitNumber);
            }
            else return answerInd;
        }catch (Exception exception){
            ioService.write(INCORRECT_INPUT_MESSAGE);
            return readNumber(limitNumber);
        }
    }

    @Override
    public long readNumber(Long... availableNumber) {
        try{
            List<Long> availableNumberList = Arrays.asList(availableNumber);
            String currentNum = ioService.read(INPUT_MESSAGE);
            Long answerInd = Long.parseLong(currentNum);
            if(!availableNumberList.contains(answerInd)){
                ioService.write(INCORRECT_INPUT_MESSAGE);
                return readNumber(availableNumber);
            }
            else return answerInd;
        }catch (Exception exception){
            ioService.write(INCORRECT_INPUT_MESSAGE);
            return readNumber(availableNumber);
        }
    }

}
