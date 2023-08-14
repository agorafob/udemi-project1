package com.alik.project1.dao;

import com.alik.project1.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int book_id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM book WHERE book_id=?",
                    new BeanPropertyRowMapper<>(Book.class), book_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Book show(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM book WHERE name=?",
                    new BeanPropertyRowMapper<>(Book.class), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name,author,year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void addPerson(int book_id, int person_id) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?",
                person_id, book_id);
    }

    public void releaseBook(int book_id) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?",
                null, book_id);
    }

    public void update(int book_id, Book updateBook) {
        jdbcTemplate.update("UPDATE book SET name = ?, author=?, year = ? WHERE book_id = ?",
                updateBook.getName(), updateBook.getAuthor(), updateBook.getYear(), book_id);
    }

    public void delete(int book_id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", book_id);
    }


}
