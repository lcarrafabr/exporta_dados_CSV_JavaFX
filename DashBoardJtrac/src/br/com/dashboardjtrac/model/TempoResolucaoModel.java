package br.com.dashboardjtrac.model;

public class TempoResolucaoModel {

	private String statusInicio;
	private String statusFim;

	public TempoResolucaoModel(String statusInicio, String statusFim) {
		this.statusInicio = statusInicio;
		this.statusFim = statusFim;
	}

	public TempoResolucaoModel() {
	}

	public String getStatusInicio() {
		return statusInicio;
	}

	public void setStatusInicio(String statusInicio) {
		this.statusInicio = statusInicio;
	}

	public String getStatusFim() {
		return statusFim;
	}

	public void setStatusFim(String statusFim) {
		this.statusFim = statusFim;
	}

}
