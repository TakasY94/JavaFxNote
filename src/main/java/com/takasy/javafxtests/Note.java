package com.takasy.javafxtests;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Note {
    private SimpleStringProperty title = new SimpleStringProperty("");
    private SimpleStringProperty body = new SimpleStringProperty("");
    private Date date;

    public Note() {
    }

    public Note(String title, String body, Date date) {
        this.title = new SimpleStringProperty(title);
        this.body = new SimpleStringProperty(body);
        this.date = date;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getBody() {
        return body.get();
    }

    public void setBody(String body) {
        this.body.set(body);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
// Для того чтобы таблица обновлялась автоматически при добавлении/изменении записей
    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty bodyProperty() {
        return body;
    }
}
