package com.br.GabrielLista.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.br.GabrielLista.model.Tarefa;
import com.sun.xml.internal.stream.util.BufferAllocator;

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
	
	public static List<Tarefa> readTarefa() throws FileNotFoundException {
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		FileReader reader = new FileReader(arqTarefas);//ARQUIVO QUE SERVE PARA LER
		BufferedReader buff = new BufferedReader(reader);//ELE GUARDA EM QUANTO VAI LENDO
		return null;
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

	
}
