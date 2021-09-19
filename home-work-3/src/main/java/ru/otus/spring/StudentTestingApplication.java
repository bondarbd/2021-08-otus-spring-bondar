package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.service.TestService;

/**
 * @author Denis Bondar (/bondarbd)
 */

@SpringBootApplication
public class StudentTestingApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext applicationContext = SpringApplication.run(StudentTestingApplication.class);
        TestService bean = applicationContext.getBean(TestService.class);
        bean.runTest();
        applicationContext.close();
    }

}
