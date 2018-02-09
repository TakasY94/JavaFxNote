package com.takasy.javafxtests;

import com.mysql.cj.api.mysqla.result.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.concurrent.Callable;

public class DBNotebook implements Callable<ObservableList> {

    private static final String URL = "jdbc:mysql://localhost:3306/notes?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public ObservableList<Note> getNoteData() {
        return noteData;
    }

    private ObservableList<Note> noteData = FXCollections.observableArrayList();


    @Override
    public ObservableList call() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e) {
            System.out.println("Driver not registered");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            System.out.println("Connection with db success");

            ResultSet resultset = statement.executeQuery("SELECT title, body, date FROM notedb");
            while (resultset.next()) {
                Note note = new Note(resultset.getString("title"), resultset.getString("body"), resultset.getDate("date"));
                noteData.add(note);
                System.out.println(note.getTitle() + note.getBody() + note.getDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noteData;
    }
}
