package br.com.dashboardjtrac.Enumerics;

public enum EnumSimNao {

	SIM("SIM"), 
	NAO("N�O");

	String opcao;

	private EnumSimNao(String opcao) {
		this.opcao = opcao;
	}

	public String getOpcao() {
		return opcao;
	}

}
