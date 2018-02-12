/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takasy.javafxtests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainController  {

    public ObservableList<Note> noteData = FXCollections.observableArrayList();
    private Stage mainStage;
    private DBNotebook dbNotebook;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Note> noteTable;

    @FXML
    private TableColumn<Note, String> titleColumn;

    @FXML
    private TableColumn<Note, String> bodyColumn;

    @FXML
    private TableColumn<Note, Date> dateColumn;

    private Parent fxmlAdd;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private AddWindowController addWindowController;
    private Stage addDialogStage;



    public MainController() {}

    @FXML
    private void initialize() throws Exception {

        //Привязываем к колонкам определённые поля POJO Note
        titleColumn.setCellValueFactory(new PropertyValueFactory<Note, String>("title"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<Note, String>("body"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Note, Date>("date"));

        //Заполняем таблицу данными из БД
        noteTable.setItems(initDB());
        initLoader();
    }

    public void setMainStage (Stage mainStage) {
        this.mainStage = mainStage;
    }

    //Тестовый метод
    private void initData() {
        noteData.add(new Note("Alex1", "qwerty", new Date()));
        noteData.add(new Note("Alex2", "qwerty", new Date()));
        noteData.add(new Note("Alex3", "qwerty", new Date()));
        noteData.add(new Note("Alex4", "qwerty", new Date()));
    }


    @FXML
    private void showDialog(ActionEvent event) {
        if (addDialogStage == null){
            addDialogStage = new Stage();
            addDialogStage.setTitle("Редактирование заметки");
            addDialogStage.setScene(new Scene(fxmlAdd));
            addDialogStage.initModality(Modality.WINDOW_MODAL);
            addDialogStage.initOwner(mainStage);
        }
        addDialogStage.showAndWait();
        noteData.add(addWindowController.getNote());

    }
    private void initLoader() {

        try {
            fxmlLoader.setLocation(getClass().getResource("new_note.fxml"));
            fxmlAdd = fxmlLoader.load();
            addWindowController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList initDB() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Future<ObservableList>> list = new ArrayList<Future<ObservableList>>();
        Callable<ObservableList> callable = new DBNotebook();
        Future<ObservableList> future = executor.submit(callable);
        list.add(future);
        executor.shutdown();
        return list.get(0).get();
    }

}
