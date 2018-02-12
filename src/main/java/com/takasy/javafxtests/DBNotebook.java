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

    private ObservableList<Note> noteData = FXCollections.observableArrayList();

    public ObservableList<Note> getNoteData() {
        return noteData;
    }

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

    public void saveNote(Note note) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); Statement statement = connection.createStatement()) {
            System.out.println("Connection with db success (save)");

            String title = note.getTitle();
            String body = note.getBody();
            //Date date = note.getDate();
            String query = "INSERT INTO notedb (title, body, date) VALUES (?, ?, NOW())";
            PreparedStatement prep = connection.prepareStatement(query);
            prep.setString(1, title);
            prep.setString(2, body);
            prep.executeUpdate();
            //prep.setDate(3, date);//TODO получать из util.Date sql.Date и вставлять (не критично так как время создания заметки и сохранения практически одинаковое)

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
