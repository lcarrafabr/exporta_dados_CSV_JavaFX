package br.com.dashboardjtrac.Enumerics;

public enum EnumPeriodosDoDia {
	
	MANHA("MANH�"),
	TARDE("TARDE"),
	NOITE("NOITE"),
	MADRUGADA("MADRUGADA"),
	OUTROS_HORARIOS("OUTROS HOR�RIOS"),
	SEM_COBERTURA("SEM COBERTURA");
	
	String periodo;

	private EnumPeriodosDoDia(String periodo) {
		this.periodo = periodo;
	}

	public String getPeriodo() {
		return periodo;
	}

}
