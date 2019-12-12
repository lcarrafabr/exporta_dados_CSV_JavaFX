package br.com.dashboardjtrac.model;

public class HistoryModel {

	private Integer status;
	private String time_stamp;
	private String prefix_code;

	

	public HistoryModel(Integer status, String time_stamp, String prefix_code) {
		this.status = status;
		this.time_stamp = time_stamp;
		this.prefix_code = prefix_code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTime_stamp() {
		return time_stamp;
	}

	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	public void setPrefix_code(String prefix_code) {
		this.prefix_code = prefix_code;
	}
	
	public String getPrefix_code() {
		return prefix_code;
	}

}
