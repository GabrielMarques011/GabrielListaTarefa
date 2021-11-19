package com.br.GabrielLista.application;
	
import com.br.GabrielLista.io.TarefaIO;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			TarefaIO.criarFiles();//IRA CRIAR UMA PASTA
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../view/Index.fxml"));
			Scene scene = new Scene(root,814,588);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}