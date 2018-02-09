/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.takasy.javafxtests;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<Future<ObservableList>> list = new ArrayList<Future<ObservableList>>();
        Callable<ObservableList> callable = new DBNotebook();
        Future<ObservableList> future = executor.submit(callable);
        list.add(future);
        list.get(0);//TODO Полученный ObserveList нужно передать в MainController для отрисовки полей таблицы из БД
        System.out.println(list.get(0).get());
        executor.shutdown();



        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(stage);

        stage.setTitle("Записная книжка");
        Scene scene = new Scene(fxmlMain);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
