package com.alik.project1.controllers;

import com.alik.project1.dao.BookDAO;
import com.alik.project1.dao.BookPersonDAO;
import com.alik.project1.dao.PersonDAO;
import com.alik.project1.models.Book;
import com.alik.project1.models.BookPerson;
import com.alik.project1.models.Person;
import com.alik.project1.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    PersonDAO personDAO;
    PersonValidator personValidator;
    BookDAO bookDAO;
    BookPersonDAO bookPersonDAO;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO, BookPersonDAO bookPersonDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookPersonDAO = bookPersonDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "/people/index";
    }

    @GetMapping("/{person_id}")
    public String show(@PathVariable("person_id") int person_id, Model model,
                       @ModelAttribute("book") Book book,
                       @ModelAttribute("bookPerson") BookPerson bookPerson) {
        model.addAttribute("person", personDAO.show(person_id));
        model.addAttribute("hasBooks", bookPersonDAO.hasBook(person_id));
        model.addAttribute("books", bookDAO.index());
        model.addAttribute("booksWithOwners", bookPersonDAO.index());
        return "/people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int person_id) {
        model.addAttribute("person", personDAO.show(person_id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("person_id") int person_id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personDAO.update(person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int person_id) {
        personDAO.delete(person_id);
        return "redirect:/people";
    }
}
