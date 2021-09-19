package ru.otus.spring.repository;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ru.otus.spring.configuration.ApplicationConfiguration;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exeption.QuestionReadingException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Component
@Slf4j
public class QuestionRepositoryImpl implements QuestionRepository {

    private List<Question> questionList = new ArrayList<>();
    private final ApplicationConfiguration applicationConfiguration;
    private final ResourceLoader resourceLoader;

    @Autowired
    public QuestionRepositoryImpl(ApplicationConfiguration applicationConfiguration, ResourceLoader resourceLoader) {
        this.applicationConfiguration = applicationConfiguration;
        this.resourceLoader = resourceLoader;
    }


    @Override
    public List<Question> getQuestionList() throws QuestionReadingException {
        try {
            Resource csvFile = resourceLoader
                    .getResource("classpath:" + applicationConfiguration.getTestFileName());
            CsvToBean<Question> questionCsvToBean
                    = new CsvToBeanBuilder<Question>(
                    new CSVReader(
                            new InputStreamReader(
                                    csvFile.getInputStream(),
                                    StandardCharsets.UTF_8)))
                    .withSeparator(',')
                    .withType(Question.class)
                    .build();
            List<Question> questionList = questionCsvToBean.parse();
            log.info("Question list was successfully parsed");
            return questionList;
        } catch (Exception exception){
            throw new QuestionReadingException("Unable to read question file cause: " + exception.getMessage());
        }
    }


}
