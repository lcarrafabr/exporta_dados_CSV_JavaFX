package br.com.dashboardjtrac.Enumerics;

public enum EnumPeriodosDoDia {
	
	MANHA("MANHÃ"),
	TARDE("TARDE"),
	NOITE("NOITE"),
	MADRUGADA("MADRUGADA"),
	OUTROS_HORARIOS("OUTROS HORÁRIOS"),
	SEM_COBERTURA("SEM COBERTURA");
	
	String periodo;

	private EnumPeriodosDoDia(String periodo) {
		this.periodo = periodo;
	}

	public String getPeriodo() {
		return periodo;
	}

}
