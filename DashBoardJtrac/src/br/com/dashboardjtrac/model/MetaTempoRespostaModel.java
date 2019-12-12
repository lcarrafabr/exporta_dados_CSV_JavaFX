package br.com.dashboardjtrac.model;

public class MetaTempoRespostaModel {

	private Integer id;
	private String categoria;
	private String prioridade;
	private Integer meta;

	public MetaTempoRespostaModel() {
	}

	public MetaTempoRespostaModel(Integer id, String categoria, String prioridade, Integer meta) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.prioridade = prioridade;
		this.meta = meta;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

}
