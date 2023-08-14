package com.alik.project1.models;

public class BookPerson {
    private int book_id;
    private int person_id;

    public BookPerson() {
    }

    public BookPerson(int book_id, int person_id) {
        this.book_id = book_id;
        this.person_id = person_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
