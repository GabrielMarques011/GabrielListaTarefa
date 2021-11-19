package com.br.GabrielLista.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {
	private long id;
	private LocalDate dataCriacao;
	private LocalDate dataLimite;
	private LocalDate dataFinalizada;
	private String descricao;
	private String comentario;
	private StatusTarefa status;//CHAMANDO O STATUS DA TAREFA
	private ChoiceClass Estado;//CHAMANDO O CHOICE DO ESTADO
	
	
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
	
	public ChoiceClass getEstado() {
		
		return Estado;
	}

	public void setEstado(ChoiceClass estado) {
		
		Estado = estado;
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
		//builder.append(this.getEstado().ordinal()+";");
		builder.append(this.getComentario()+";");
		builder.append(this.getStatus().ordinal()+"\n");//\N PARA QUEBRAR A PROXIMA TAREFA PARA BAIXO
		return builder.toString();
	}

	
	
	
}