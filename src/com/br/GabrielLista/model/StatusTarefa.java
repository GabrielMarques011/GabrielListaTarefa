package com.br.GabrielLista.model;

public enum StatusTarefa {
	
	/*CRIANDO UMA ENUMERA��O*/
	ABERTA("Aberta"), 
	CONCLUIDA("Concluida"), 
	ADIADA("Adiada");
	
	String descricao;
	
	private StatusTarefa(String desc) {
		this.descricao = desc;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
}