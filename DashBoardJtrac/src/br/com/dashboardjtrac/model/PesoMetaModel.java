package br.com.dashboardjtrac.model;

public class PesoMetaModel {

	private String prioridade;
	private String categoria;
	private Integer peso;

	public PesoMetaModel() {
	}

	public PesoMetaModel(String prioridade, String categoria, Integer peso) {
		this.prioridade = prioridade;
		this.categoria = categoria;
		this.peso = peso;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

}
