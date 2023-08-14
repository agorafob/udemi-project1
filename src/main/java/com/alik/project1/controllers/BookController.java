package com.alik.project1.controllers;

import com.alik.project1.dao.BookDAO;
import com.alik.project1.dao.BookPersonDAO;
import com.alik.project1.dao.PersonDAO;
import com.alik.project1.models.Book;
import com.alik.project1.models.Person;
import com.alik.project1.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    BookDAO bookDAO;
    PersonDAO personDAO;
    BookValidator bookValidator;
    BookPersonDAO bookPersonDAO;


    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator, BookPersonDAO bookPersonDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
        this.bookPersonDAO = bookPersonDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "/books/index";
    }

    @GetMapping("/{book_id}")
    public String show(@PathVariable("book_id") int book_id,
                       Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(book_id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("bookOwnerId", bookPersonDAO.getOwnerId(book_id));
        return "/books/show";
    }

    @PatchMapping("/{book_id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("book_id") int book_id,
                         @ModelAttribute("person") Person person) {
        if (person.getPerson_id() > 0) {
            bookDAO.addPerson(book_id, person.getPerson_id());
        } else if (person.getName().equals("Delete Delete")) {
            bookDAO.releaseBook(book_id);
        } else {
            bookValidator.validate(book, bindingResult);
            if (bindingResult.hasErrors()) {
                return "/books/edit";
            }
            System.out.println("update");
            bookDAO.update(book_id, book);
        }
        return "redirect:/books/{book_id}";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}/edit")
    public String edit(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookDAO.show(book_id));
        return "books/edit";
    }


    @DeleteMapping("/{book_id}")
    public String delete(@PathVariable("book_id") int book_id) {
        bookDAO.delete(book_id);
        return "redirect:/books";
    }


}
