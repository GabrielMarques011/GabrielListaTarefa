package com.br.GabrielLista.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.br.GabrielLista.io.TarefaIO;
import com.br.GabrielLista.model.ChoiceClass;
import com.br.GabrielLista.model.StatusTarefa;
import com.br.GabrielLista.model.Tarefa;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class IndexController implements Initializable {

	
	//APLIQUEI ISSO AQUI PARA APARECER OPÇÕES DE NOMES, ESTADOS...
	@FXML
	private ChoiceBox<ChoiceClass> Estado;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Estado.setItems(FXCollections.observableArrayList(ChoiceClass.values()));
	}
	//FINALIZANDO O APARECER AS OPÇÕES
	

    @FXML
    private DatePicker dpDataLimite;

    @FXML
    private TextField fxTarefa;

    @FXML
    private TextField fxStatus;

    @FXML
    private TextArea fxComentario;
    
    //VARIAVEL PARA GUARDAR A TAREFA //COMO SE FOSSE UMA BANCO DE DADOS
    @FXML
    private Tarefa tarefa;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btCalend;

    @FXML
    private Button btLista;

    @FXML
    private Button btLixo;

    @FXML
    private Button btBorracha;

    @FXML
    public void btBorrachaClick() {
    	LimparCampos();
    }

    @FXML
    public void btCalendClick() {
                                                                          
    }                                                                     
                                                                          
    @FXML                                                                 
    public void btListaClick() {
                                                                          
    }                                                                     
                                                                          
    @FXML
    public void btLixoClick() {

    }

    @FXML
    public void btSalvaClick() {
    	
    	//VALIDAÇÃO DE CAMPOS
    	if(dpDataLimite.getValue()==null) {//IRA APARECER ESSE ERRO CASO NÃO TENHA PREENCHIDO O CAMPO DATA-LIMITE
    		JOptionPane.showMessageDialog(null, "Informe a data limite", "Informe", JOptionPane.ERROR_MESSAGE);
    		dpDataLimite.requestFocus();
    	}else if(fxTarefa.getText().isEmpty()) {//IRA APARECER ESSE ERRO CASO NÃO TENHA PREENCHIDO O CAMPO TAREFA
    		JOptionPane.showMessageDialog(null, "Informe a descrição da Tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		fxTarefa.requestFocus();
    	}else if(fxComentario.getText().isEmpty()) {//iRA APARECER ESSE ERRO CASO NÃO TENHA PREENCHIDO O CAMPO DE COMENTARIO
    		JOptionPane.showMessageDialog(null, "Informe os comentários da Tarefa, por favor!!", "Informe", JOptionPane.ERROR_MESSAGE);
    		fxComentario.requestFocus();
    	}else if(fxTarefa.getLength() <=5) {
    		JOptionPane.showMessageDialog(null, "Tarefa está muito curta", "Informe", JOptionPane.ERROR_MESSAGE);
    		fxTarefa.requestFocus();
    	}else if(Estado.getValue()==null) {
    		JOptionPane.showMessageDialog(null, "Você não preencheu o campo do Nivel de Importancia", "Informe", JOptionPane.ERROR_MESSAGE);
    		Estado.requestFocus();
    	}else {
    		//VERIFICA SE A DATA INFORMADA NÃO É A ANTERIOR Á DATA ATUAL
    		if(dpDataLimite.getValue().isBefore(LocalDate.now())) {//ISBEFORE É UTILIZADO PARA COMPARAR A DATA APLICADA COM A DATA ATUAL
    			JOptionPane.showMessageDialog(null, "Informe uma data compativel", "Informe", JOptionPane.ERROR_MESSAGE);
    			dpDataLimite.requestFocus();
    		}else {
    			//INSTANCIA A TAREFA
    			tarefa = new Tarefa();
    			//POPULAR A TAREFA
    			tarefa.setDataCriacao(LocalDate.now());// DETERMINANDO A DATA ATUAL
    			tarefa.setStatus(StatusTarefa.ABERTA);//PUXEI O STATUS TAREFA
    			tarefa.setDataLimite(dpDataLimite.getValue());//DETERMINANDO A DATA LIMITE E PUXANDO A TAREFA
    			tarefa.setDescricao(fxComentario.getText());//CHAMAEI O DESCRICAO DO TAREFA
    			tarefa.setComentario(fxComentario.getText());//CHAMEIO O COMENTARIO DO TAREFA
    			
    			//TODO SALVAR NO BANCO DE DADOS
    			try {
    				TarefaIO.insert(tarefa);
    				//LIMPAR OS CAMPOS DO FORMULÁRIO    			
        			LimparCampos();
    			}catch(FileNotFoundException e) {
    				JOptionPane.showMessageDialog(null, "Arquivo não encontrado:"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    				e.printStackTrace();
    			}catch(IOException e) {
    				JOptionPane.showMessageDialog(null, "Arquivo de I/O:"+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    				e.printStackTrace();
    			}
    			
    		}
    	}
    }               

    
    @FXML
    private void LimparCampos() {
    	
    		fxComentario.setText(null);//DEIXANDO CADA CAIXA VAZIA, APENAS COM O CLICK NO BOTÃO NOVAMENTE
    		fxTarefa.setText(null);
    		fxStatus.setText(null);
    		dpDataLimite.setValue(null);
    		Estado.setValue(null);
    		
    		//POSSIBILIDADES DE LIMPAR O OBJETO
    		// = null;
    		//.clear();
    		//.setText("");
    		//setValue(null);
    		//setText(null);
    		//requestFocus();//SERVE PARA DAR O FOCO COMO SE FOSSE O PRIMEIRO OBJETO
    	
    }
    


}
