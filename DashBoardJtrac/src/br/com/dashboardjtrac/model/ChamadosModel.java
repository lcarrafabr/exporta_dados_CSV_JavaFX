package br.com.dashboardjtrac.model;

public class ChamadosModel {

	private Long id;
	// Chamado
	private Long sequence_num;
	// Sistema
	private String prefix_code;
	
	private String dataAbertura;
	
	private int statusId;
	private String status;
	
	private String dataFechamento;
	
	private String categoria;

	private String prioridade;
	
	private String effortTime;
	
	private String summary;
	
	private String supoprteN2;


	

	public ChamadosModel(Long id, Long sequence_num, String prefix_code, String dataAbertura, int statusId,
			String status, String dataFechamento, String categoria, String prioridade, String effortTime,
			String summary, String supoprteN2) {
		this.id = id;
		this.sequence_num = sequence_num;
		this.prefix_code = prefix_code;
		this.dataAbertura = dataAbertura;
		this.statusId = statusId;
		this.status = status;
		this.dataFechamento = dataFechamento;
		this.categoria = categoria;
		this.prioridade = prioridade;
		this.effortTime = effortTime;
		this.summary = summary;
		this.supoprteN2 = supoprteN2;
	}

	public String getPrefix_code() {
		return prefix_code;
	}
	
	public void setPrefix_code(String prefix_code) {
		this.prefix_code = prefix_code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSequence_num() {
		return sequence_num;
	}

	public void setSequence_num(Long sequence_num) {
		this.sequence_num = sequence_num;
	}
	
	public String getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	public String getDataFechamento() {
		return dataFechamento;
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
	
	public String getEffortTime() {
		return effortTime;
	}
	
	public void setEffortTime(String effortTime) {
		this.effortTime = effortTime;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getSupoprteN2() {
		return supoprteN2;
	}
	
	public void setSupoprteN2(String supoprteN2) {
		this.supoprteN2 = supoprteN2;
	}
}
