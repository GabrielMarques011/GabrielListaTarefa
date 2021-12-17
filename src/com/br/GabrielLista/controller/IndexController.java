package com.br.GabrielLista.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.br.GabrielLista.io.TarefaIO;
import com.br.GabrielLista.model.StatusTarefa;
import com.br.GabrielLista.model.Tarefa;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class IndexController implements Initializable, ChangeListener<Tarefa> {
	
	
	//ABRINDO PARTE DO MENU
	
	@FXML
	public void btSaidaClick() {
		System.exit(0);
	}
	
	@FXML
	public void btExportClick() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo HTML","html");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File arqSelecionada = chooser.getSelectedFile();
			arqSelecionada = new File(arqSelecionada+".html");
			try {
				TarefaIO.exportHtml(tarefas, arqSelecionada);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Erro ao exportar", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@FXML
	public void btAboutClick() {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/br/GabrielLista/view/Sobre.fxml"));//CHAMANDO SEGUNDA TELA
			Scene scene = new Scene(root, 400, 400);//TAMANHO DA JANELA
			scene.getStylesheets().add(getClass().getResource("/com/br/GabrielLista/view/application.css").toExternalForm());//PARA MEXER NO CSS EXTERNO
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Sobre G||M");
			
			//PARA RETIRA A BORDA
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();//ABRE ESPERAR
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	//FECHANDO MENU
	
    @FXML private DatePicker dpDataLimite;
    
    @FXML private Label lbData;

    @FXML private TextField fxCod;
    
    @FXML private TextField fxTarefa;

    @FXML private TextField fxStatus;

    @FXML private TextArea fxComentario;
    
    @FXML private Tarefa tarefa;

    @FXML private Button btSalvar;

    @FXML private Button btCalend;

    @FXML private Button btLista;

    @FXML private Button btLixo;

    @FXML private Button btBorracha;

    @FXML
    public void btBorrachaClick() {
    	LimparCampos();
    }

    //ADIAR
    @FXML
    public void btCalendClick() throws IOException{
    	if(tarefa != null){
    		int dias = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias você deseja adiar?!", "Informe quantos dias!!", JOptionPane.QUESTION_MESSAGE));
    		
    		//CRIEI UMA NOVA DATA
    		LocalDate novaData = tarefa.getDataLimite().plusDays(dias);
    		tarefa.setDataLimite(novaData);
    		tarefa.setStatus(StatusTarefa.ADIADA);//ALTERANDO O STATUS DA TAREFA, CASO ALTERE A DATA
    		
    		try {
    		TarefaIO.attTarefas(tarefas);
    		
    		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    		JOptionPane.showMessageDialog(null, "Tarefa Adiada com sucesso!!\n"+novaData.format(fmt));
    			
    		carregarTarefas();
    		LimparCampos();
    		
    		} catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar a tarefas", "Erro", JOptionPane.ERROR_MESSAGE);
    			e.printStackTrace();
    		}
    	}
    }      
    
    
    //CONCLUIDO
    @FXML                                                                 
    public void btListaClick() {
    	
    	if(tarefa != null) {
    		
    		tarefa.setStatus(StatusTarefa.CONCLUIDA);
    		tarefa.setDataFinalizada(LocalDate.now());
    		
    		try {
    			TarefaIO.attTarefas(tarefas);
    			
    			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        		JOptionPane.showMessageDialog(null, "Tarefa Conluida com sucesso!!\n"+tarefa.getDataFinalizada().format(fmt));
				
				carregarTarefas();
				LimparCampos();
				
				
			} catch (Exception e) {
				
				JOptionPane.showMessageDialog(null, "ERRO!!", "ERRO TAREFAS", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				
			}
    		
    	}
    	
    }                                                                     
    
    //EXCLUIR
    @FXML
    public void btLixoClick() {
    	
    	if(tarefa != null) {
    		
    		int reposta = JOptionPane.showConfirmDialog(null, "Deseja excluir essa Tarefa "+tarefa.getId()+" !?", "Confirma Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    		
    		//REMOVENDO TAREFA CASO APERTE SIM (0) (NÃO = 1)
    		if(reposta == 0) {
    			tarefas.remove(tarefa);
    			
    			try {
    				
    			TarefaIO.attTarefas(tarefas);
    			carregarTarefas();
				LimparCampos();
    			
    			} catch (IOException e) {
    				
    				JOptionPane.showMessageDialog(null, "ERRO!!", "ERRO TAREFAS", JOptionPane.ERROR_MESSAGE);
    				e.printStackTrace();
    				
    			}
    		}
    	}
    	
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
    	}else {
    		//VERIFICA SE A DATA INFORMADA NÃO É A ANTERIOR Á DATA ATUAL
    		if(dpDataLimite.getValue().isBefore(LocalDate.now())) {//ISBEFORE É UTILIZADO PARA COMPARAR A DATA APLICADA COM A DATA ATUAL
    			JOptionPane.showMessageDialog(null, "Informe uma data compativel", "Informe", JOptionPane.ERROR_MESSAGE);
    			dpDataLimite.requestFocus();
    		}else {
    			if(tarefa == null) {
    				//INSTANCIA A TAREFA
        			tarefa = new Tarefa();
        			//POPULAR A TAREFA
        			tarefa.setDataCriacao(LocalDate.now());// DETERMINANDO A DATA ATUAL
        			tarefa.setStatus(StatusTarefa.ABERTA);//PUXEI O STATUS TAREFA
    			}
    			
    			tarefa.setDataLimite(dpDataLimite.getValue());//DETERMINANDO A DATA LIMITE E PUXANDO A TAREFA
    			tarefa.setDescricao(fxTarefa.getText());//CHAMAEI O DESCRICAO DO TAREFA
    			tarefa.setComentario(fxComentario.getText());//CHAMEIO O COMENTARIO DO TAREFA
    			tarefa.setCod(fxCod.getText());
    			
    			//TODO SALVAR NO BANCO DE DADOS
    			try {
    				if(tarefa.getId() == 0) {
    					TarefaIO.insert(tarefa);
    				}else {
    					TarefaIO.attTarefas(tarefas);
    				}
    				//LIMPAR OS CAMPOS DO FORMULÁRIO
    				
        			LimparCampos();
        			carregarTarefas();
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
    		fxCod.setText(null);
    		
    		btCalend.setDisable(true);
			btLixo.setDisable(true);
			btLista.setDisable(true);
			dpDataLimite.setDisable(false);
			fxStatus.setDisable(false);
			fxCod.setDisable(false);
			
			tvTarefa.getSelectionModel().clearSelection();
    		
			//HABILITEI AS CAIXAS DE TEXTOS
			fxComentario.setEditable(true);
			fxTarefa.setEditable(true);
			
			fxStatus.setDisable(true);
			btCalend.setDisable(false);
			btLista.setDisable(false);
			btLixo.setDisable(false);
			btSalvar.setDisable(false);
			fxCod.setDisable(true);
			
			lbData.setText("Data para realização:");
    	
    }
    

  //CRIANDO O TABLE VIEW
	
    //VARIAVEL PARA GUARDAR AS TAREFAS
  	@FXML
  	private TableView<Tarefa> tvTarefa;
  	
  	//VARIAVEL PARA GUARDAR A LISTA DE TAREFAS
  	private List<Tarefa> tarefas;
  		
  	@FXML
  	private TableColumn<Tarefa, LocalDate> tcData;
  		
	@FXML
	private TableColumn<Tarefa, String> tcTarefa;	

	@FXML
	private TableColumn<Tarefa, StatusTarefa> tcStatus;	
	
  	@Override
  	public void initialize(URL location, ResourceBundle resources) {
  		
  		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));//PRIMEIRA COLUNA IRA APARECER A DATA LIMITE DA TAREFA
  		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricao"));//SEGUNDA COLUNA IRA APARCER A DESCRIÇÃO DA TAREFA
  		tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
  		
  		//CRIEI UM CLASS DENTRO DO METODO EM MODO ANONYMOS, CLASS QUE INCLEMENTA UMA INTERFACE
  		tcData.setCellFactory(call -> {
  			
				//DEVOLVENDO UMA ESTANCIA
				return new TableCell<Tarefa, LocalDate>(){//CRIANDO UMA NOVA CLASS ANONIMA, DENTRO DESSA CHAVES
					
					@Override
					//METODO CHAMADO QUANDO ESTA FAZENDO A INTERFACE
					protected void updateItem(LocalDate item, boolean empty) {
						// TODO Auto-generated method stub
						
						//AQUI VOU CONFIGURAR O "TABLECELL"
						super.updateItem(item, empty);
							DateTimeFormatter ftm = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
						if(!empty) {
							setText(item.format(ftm));
						}else {
							setText("");
						}
						
				}//FECHANDO A 3° INSTRUÇÃO
					
			};//FECHANDO A 2° INSTRUÇÃO
			
		});//FECHANDO A 1° INSTRUÇÃO
  		
  		//PERSONALIZAÇÃO
  		tvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {
  			protected void updateItem(Tarefa item, boolean empty) {
  				super.updateItem(item, empty);
  				if(item == null) {
  					setStyle("");
  				}else if(item.getStatus() == StatusTarefa.CONCLUIDA){
  					setStyle("-fx-background-color: #A2F5AA");
  				}else if(item.getDataLimite().isBefore(LocalDate.now())){
  					setStyle("-fx-background-color: #FF4A3E");
  				}else if(item.getStatus() == StatusTarefa.ADIADA) {
					setStyle("-fx-background-color: #EBE570");
  				}else {
  					setStyle("-fx-background-color: aliceblue");
  				}
  			};
  		});
  		
  		//EVENTO DE SELEÇÃO DE UM ITEM NA TABLE VIEW
  		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);  		
  		
  		
  		carregarTarefas();
  		
  	}
  	
  	
  	
  	private void carregarTarefas() {
  		
  		try {
			
  			//BUSCANDO AS TAREFAS NO BD E GUARDANDO NA VARIAVEL TAREFAS
  			tarefas = TarefaIO.readTarefa();
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));//CONVERTENDO A LISTA PARA UM "OBSERVABLEARRAYLIST" E ASSOCIANDO
  			
			//ATUALIZANDO A TABELA
			tvTarefa.refresh();
			
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar as tarefas", "ERRO!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
  		
  	}
  	
  	//METODO CRIADO DEPOIS DE APLICAR O ChangeListener<Tarefa>
	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		// TODO Auto-generated method stub
		//PASSAR A REFERENCIA DA VARIAVEL LOCAL PARA A TAREFA GLOBAL
		tarefa = newValue;//MINHA VARIAVEL PRIVADA RECEBE UM GLOBAL
		if(tarefa != null) {
			fxTarefa.setText(tarefa.getDescricao());
			fxComentario.setText(tarefa.getComentario());
			fxStatus.setText(tarefa.getStatus().toString());//INVES DE .toString (+"")
			dpDataLimite.setValue(tarefa.getDataLimite());
			
			fxCod.setText(tarefa.getCod());//AQUI
		
			//PARA NÃO CONSEGUIR MODIFICAR, POR ISSO O .setDisable(true)
			dpDataLimite.setDisable(true);
			fxStatus.setDisable(true);
			
			//CRIANDO A PARTE DO COD, DEIXANDO ELA NÃO EDITAVEL
			fxCod.setDisable(true);//AQUI
			
			btCalend.setDisable(false);
			btLixo.setDisable(false);
			btLista.setDisable(false);
			
			fxComentario.setEditable(true);
			fxComentario.setOpacity(1);
			fxStatus.setEditable(true);
			fxTarefa.setEditable(true);
			fxTarefa.setOpacity(1);
			dpDataLimite.setEditable(true);
			dpDataLimite.setOpacity(1);
			fxCod.setText(tarefa.getId() + "");
			
			if(tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
				
				btLista.setDisable(true);
				btCalend.setDisable(true);
				btSalvar.setDisable(true);
				fxComentario.setEditable(false);
				fxTarefa.setEditable(false);
				
				lbData.setText("Data de Conclusão:");
				
			}else {
				
				btLista.setDisable(false);
				btCalend.setDisable(false);
				btSalvar.setDisable(false);
				fxComentario.setEditable(true);
				fxTarefa.setEditable(true);
				
				lbData.setText("Data para realização:");
				
			}
			
			btLixo.setDisable(false);
    		
		}
	}
	
  	//ACABA O TABLE VIEW

}
