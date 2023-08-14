package com.alik.project1.dao;

import com.alik.project1.models.BookPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class BookPersonDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookPersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookPerson> index() {
        return jdbcTemplate.query("select b.book_id, p.person_id from book b join person p on b.person_id = p.person_id",
                new BeanPropertyRowMapper<>(BookPerson.class));
    }

    public Integer getOwnerId(int book_id) {
        Integer ownerId = null;
        if (index().isEmpty()) {
            return null;
        } else {
            BookPerson bookPerson = index().stream().filter(i -> i.getBook_id() == book_id).findAny().orElse(null);
            if (Objects.nonNull(bookPerson)) {
                ownerId = bookPerson.getPerson_id();
            }
        }
        return ownerId;
    }

    public boolean hasBook(int person_id) {
        return index().stream().anyMatch(i -> i.getPerson_id() == person_id);
    }
}
