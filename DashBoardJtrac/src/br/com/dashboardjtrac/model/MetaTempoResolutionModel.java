package br.com.dashboardjtrac.model;

public class MetaTempoResolutionModel {

	private Long id;
	private String categoria;
	private String prioridade;
	private Integer meta;

	public MetaTempoResolutionModel(Long id, String categoria, String prioridade, Integer meta) {
		this.id = id;
		this.categoria = categoria;
		this.prioridade = prioridade;
		this.meta = meta;
	}

	public MetaTempoResolutionModel() {
	}
	
	

	@Override
	public String toString() {
		return "MetaTempoResolutionModel [meta=" + meta + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
