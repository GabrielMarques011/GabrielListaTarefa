package com.br.GabrielLista.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private Button btOk;

    @FXML
    public void btOkClick() {
    	Stage stage = (Stage) btOk.getScene().getWindow();
    	stage.close();
    }

}