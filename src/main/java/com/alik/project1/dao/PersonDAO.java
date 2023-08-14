package com.alik.project1.dao;

import com.alik.project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int person_id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM person WHERE person_id=?",
                    new BeanPropertyRowMapper<>(Person.class), person_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Person show(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM person WHERE name=?",
                    new BeanPropertyRowMapper<>(Person.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,year) VALUES (?,?)",
                person.getName(), person.getYear());
    }

    public void update(int person_id, Person updatePerson) {
        jdbcTemplate.update("UPDATE person SET name = ?, year = ? WHERE person_id = ?",
                updatePerson.getName(), updatePerson.getYear(), person_id);
    }

    public void delete(int person_id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id=?", person_id);
    }
}
