package com.alik.project1.util;

import com.alik.project1.dao.BookDAO;
import com.alik.project1.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.util.Objects;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (Objects.nonNull(bookDAO.show(book.getName())) &&
                bookDAO.show(book.getBook_id()).getBook_id() != book.getBook_id()) {
            errors.rejectValue("name", "", "Name should be unique");
        }
    }
}
