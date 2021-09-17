package ru.otus.spring.domain;

import lombok.Value;

/**
 * @author Denis Bondar (/bondarbd)
 */

@Value
public class Student {
    String firstName;
    String lastName;

    @Override
    public String toString(){
      return firstName + " " + lastName;
    }
}
