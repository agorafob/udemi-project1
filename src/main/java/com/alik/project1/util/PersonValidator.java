package com.alik.project1.util;

import com.alik.project1.dao.PersonDAO;
import com.alik.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (Objects.nonNull(personDAO.show(person.getName())) &&
                personDAO.show(person.getPerson_id()).getPerson_id() != person.getPerson_id()) {
            errors.rejectValue("name", "", "Name should be unique");
        }
    }
}
