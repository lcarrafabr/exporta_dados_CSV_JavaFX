package br.com.dashboardjtrac.model;

public class ListaStatusJtrac {

	private Integer status;
	private String rotulo;
	private Long time;

	public ListaStatusJtrac() {
	}

	public ListaStatusJtrac(Integer status, String rotulo) {
		this.status = status;
		this.rotulo = rotulo;
	}
	
	

	@Override
	public String toString() {
		return "ListaStatusJtrac [status=" + status + ", rotulo=" + rotulo + ", time=" + time + "]";
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}

}
