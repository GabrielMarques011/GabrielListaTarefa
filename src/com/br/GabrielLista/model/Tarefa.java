package com.br.GabrielLista.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa implements Comparable<Tarefa> {
	
	private long id;
	
	private LocalDate dataCriacao, dataLimite, dataFinalizada;
	
	private String descricao, comentario, cod;//CRIEI O COD AQUI NA TAREFA
	
	private StatusTarefa status;//CHAMANDO O STATUS DA TAREFA
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	public LocalDate getDataFinalizada() {
		return dataFinalizada;
	}
	
	public void setDataFinalizada(LocalDate dataFinalizada) {
		this.dataFinalizada = dataFinalizada;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	public StatusTarefa getStatus() {
		return status;
	}
	
	public void setStatus(StatusTarefa status) {
		
		this.status = status;
	}
	
	public String formatToSave() {
		StringBuilder builder = new StringBuilder();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");//FORMATADOR DE DATA
		builder.append(this.getId()+";");
		builder.append(this.getDataCriacao().format(fmt)+";");//DETERMINANDO O PADRAO DE DATA
		builder.append(this.getDataLimite().format(fmt)+";");
		
		if(this.getDataFinalizada() != null) {
			
			builder.append(this.getDataFinalizada().format(fmt));
		}
		
		//APLICANDO PARA SALVAR
		builder.append(";");
		builder.append(this.getDescricao()+";");
		builder.append(this.getComentario()+";");
		builder.append(this.getStatus().ordinal()+"\n");//\N PARA QUEBRAR A PROXIMA TAREFA PARA BAIXO
		return builder.toString();
	}

	
	//DETERMINANDO OS GET E SET DO COD (FXCOD)
	//AQUI
	
	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	@Override
	public int compareTo(Tarefa o) {
		if(this.getDataLimite().isBefore(o.getDataLimite())) {
			return -1;
		}else if(this.getDataLimite().isAfter(o.getDataLimite())){
			return 1;
		}else {
			return this.getDescricao().compareTo(o.getDescricao());	
		}
	}

	
	
	
}