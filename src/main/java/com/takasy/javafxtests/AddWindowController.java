package com.takasy.javafxtests;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;


public class AddWindowController {

    @FXML
    TextField txtTitle;

    @FXML
    TextArea txtBody;

    @FXML
    Button btnSave;

    private Note note;

    public void setNote(Note note) {
        if (note == null){return;}
        this.note = note;
        txtTitle.setText(note.getTitle());
        txtBody.setText(note.getBody());
    }

    public Note getNote() {
        return note;
    }

    public void actionClose(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        note.setTitle(txtTitle.getText());
        note.setBody(txtBody.getText());
        note.setDate(new Date());
        actionClose(actionEvent);
    }
}
