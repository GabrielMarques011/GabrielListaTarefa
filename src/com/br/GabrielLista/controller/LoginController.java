package com.br.GabrielLista.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLogin;

    @FXML
    public void btCadastrarClick() {

    		try {
    			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/br/GabrielLista/view/Index.fxml"));
    			Scene scene = new Scene(root,1200,800);
    			scene.getStylesheets().add(getClass().getResource("/com/br/GabrielLista/view/application.css").toExternalForm());
    			Stage stage = new Stage();
    			stage.setScene(scene);
    			stage.setTitle("Login / Cadastrar - G|M");//NOME DA BARRA, NA PARTE DE CIMA
    			stage.setResizable(false);//ELE NÃO REDIMENCIONA A TELA
    			stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/br/GabrielLista/imagens/ToDoList.png")));//ADD IMG EM ICON	
    			stage.show();
    		}catch(Exception e){
    			e.printStackTrace();
    		}	
    	}

    @FXML
    void btLoginClick() {
    	
    	try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/br/GabrielLista/view/Index.fxml"));
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("/com/br/GabrielLista/view/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Login / Cadastrar - G|M");//NOME DA BARRA, NA PARTE DE CIMA
			stage.setResizable(false);//ELE NÃO REDIMENCIONA A TELA
			stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/br/GabrielLista/imagens/ToDoList.png")));//ADD IMG EM ICON	
			stage.show();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
    	
   }
