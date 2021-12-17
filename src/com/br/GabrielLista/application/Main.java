package com.br.GabrielLista.application;
	
import com.br.GabrielLista.io.TarefaIO;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			TarefaIO.criarFiles();//IRA CRIAR UMA PASTA
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/br/GabrielLista/view/Login.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("/com/br/GabrielLista/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login / Cadastrar - G|M");//NOME DA BARRA, NA PARTE DE CIMA
			primaryStage.setResizable(false);//ELE NÃO REDIMENCIONA A TELA
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/com/br/GabrielLista/imagens/ToDoList.png")));//ADD IMG EM ICON	
			primaryStage.show();
			TarefaIO.readTarefa();
		
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}