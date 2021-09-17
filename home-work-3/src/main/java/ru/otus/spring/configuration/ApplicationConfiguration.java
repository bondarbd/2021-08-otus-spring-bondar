package ru.otus.spring.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Denis Bondar (/bondarbd)
 */
@Component
@ConfigurationProperties("configuration")
public class ApplicationConfiguration {

    private String language = "en";

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Locale getLocale(){
        return Locale.forLanguageTag(language);
    }

    public String getTestFileName(){
        String baseFileType = ".csv";
        String baseFileName = "questions";
        return baseFileName + "_" + language + baseFileType;
    }
}
