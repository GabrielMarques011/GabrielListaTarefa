package com.br.GabrielLista.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.br.GabrielLista.model.StatusTarefa;
import com.br.GabrielLista.model.Tarefa;

public class TarefaIO {

	//CRIANDO 3 CONSTANTES
	private static final String FOLDER = System.getProperty("user.home")+"/GabrielListaTarefa";//AQUI ESTA ME GUIANDO PARA O USER DO PC E CRIANDO UMA PASTA LÁ CHAMADO "/GabrielListaTarefa"

	private static final String FILE_IDS = FOLDER + "/id.csv";
	
	private static final String FILE_TAREFA = FOLDER + "/tarefas.csv";
	
	
	public static void criarFiles() {
		try {
			
			//CRIANDO FILES PARA MANIPULAR FILES
			File pasta = new File(FOLDER);
			File arqIds = new File(FILE_IDS);
			File arqTarefa = new File(FILE_TAREFA);
			
			if(!pasta.exists()) {//VERIFICANDO SE A PASTA EXISTE
				pasta.mkdir();//CRIANDO A PASTA
				arqIds.createNewFile();//CRIANDO O ARQUVO
				arqTarefa.createNewFile();//CRIANDO O ARQUVO
				FileWriter writer = new FileWriter(arqIds);//CRIEI UM ESCRITOR DE ARQUIVOS
				writer.write("1");//ID DA PRIMEIRA TAREFA
				writer.close();//FECHANDO O ARQUIVO DEPOIS DE USAR
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();//SERVE PARA DAR UM AVISO NO CONSOLE
			
		}
	}
	
	public static void insert(Tarefa tarefa) throws FileNotFoundException, IOException{
		
		File arqTarefas = new File(FILE_TAREFA);
		File arqIds = new File(FILE_IDS);
		
		//LER O ULTIMO ID NO FILE_IDS
		Scanner leitor = new Scanner(arqIds);
		tarefa.setId(leitor.nextLong());
		leitor.close();
		
		//GRAVA A TAREFA NO ARQUIVO
		FileWriter writer = new FileWriter(arqTarefas, true);
		writer.append(tarefa.formatToSave());
		writer.close();
		
		//GRAVAR O NOVO "PROXIMO ID" NO ARQUIVO DE IDS
		long proxID = tarefa.getId() + 1;
		writer = new FileWriter(arqIds);
		writer.write(proxID+"");
		writer.close();
		
	}
	
	public static List<Tarefa> readTarefa() throws IOException {
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		FileReader reader = new FileReader(arqTarefas);//ARQUIVO QUE SERVE PARA LER
		BufferedReader buff = new BufferedReader(reader);//ELE GUARDA EM QUANTO VAI LENDO
		
		String linha;//ESTE METODO IRA LER CADA METODO DE UMA LINHA;
		
		while((linha = buff.readLine()) != null) {//FAZENDO ELE VER SE TEM LINHAS, CASO TENHA ELE IRA LER ATE A ULTIMA
			
			String[] vetor = linha.split(";");
			
			System.out.println(linha);
			
			//CRIANDO UMA TAREFA
			Tarefa t = new Tarefa();
			t.setId(Long.parseLong(vetor[0]));//DETERMINANDO O PRIMEIRO VETOR (ID)
			
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			//DETERMINANDO O ID NO COD
			t.setCod(vetor[0]);//AQUI
			
			t.setDataCriacao(LocalDate.parse(vetor[1], fmt));//DETERMINANDO O SEGUNDO VETOR (DATA DE CRIAÇÃO)
			
			t.setDataLimite(LocalDate.parse(vetor[2], fmt));//DETERMINANDO O TERCEIRO VETOR (DATA LIMITE)
			
			if(!vetor[3].isEmpty()) {//DETERMINANDO O QUARTO VETOR (DATA FINALIZADA)
				t.setDataFinalizada(LocalDate.parse(vetor[3], fmt));
			}
			
			t.setDescricao(vetor[4]);//DETERMINANDO O QUINTO VETOR (DESCRIÇÃO)
			
			t.setComentario(vetor[5]);//DETERMINANDO O SEXTO VETOR (COMENTARIO)
			
			//DETERMINANDO O SETIMO VETOR (STATUS)
			int indStatus = Integer.parseInt(vetor[6]);//CONVERTENDO STRING PARA NUM INTEIRO
			t.setStatus(StatusTarefa.values()[indStatus]);
			
			tarefas.add(t);//ADICIONANDO OS ARQUIVOS NA LISTA DE TAREFAS
		}
		
		buff.close();
		reader.close();
		Collections.sort(tarefas);
		
		System.out.println(tarefas.size());
		
		return tarefas;
	}
	
	public static String getFolder() {
		return FOLDER;
	}

	public static String getFileIds() {
		return FILE_IDS;
	}

	public static String getFileTarefa() {
		return FILE_TAREFA;
	}	
	
	
	public static void attTarefas (List<Tarefa> tarefas) throws IOException {
		File arqTarefas = new File(FILE_TAREFA);
		FileWriter writer = new FileWriter(arqTarefas);
		
		for(Tarefa t: tarefas) {
			writer.append(t.formatToSave());
		}
		writer.close();
	}
	
	
	public static void exportHtml(List<Tarefa> lista, File arquivos) throws IOException {
		FileWriter writer = new FileWriter(arquivos);
		writer.append("<!DOCTYPE html>\n");
		writer.append("<html>\n");
		writer.append("<body>\n");
		writer.append("<h1>Lista de Tarefas</h1>\n");
		writer.append("<ul>\n");
		
		for (Tarefa tarefa : lista) {
			writer.append("<li>\n");
			writer.append(tarefa.getDescricao() + " - " +tarefa.getDataLimite() + " - " +tarefa.getStatus());
			writer.append("</li>\n");
		}
		
		writer.append("</ul>\n");
		writer.append("</body>\n");
		writer.append("</html>\n");
		writer.close();
	}
	
	
}
