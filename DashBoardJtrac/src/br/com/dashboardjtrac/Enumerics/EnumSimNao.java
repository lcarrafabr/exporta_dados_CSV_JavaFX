package br.com.dashboardjtrac.Enumerics;

public enum EnumSimNao {

	SIM("SIM"), 
	NAO("NÃO");

	String opcao;

	private EnumSimNao(String opcao) {
		this.opcao = opcao;
	}

	public String getOpcao() {
		return opcao;
	}

}
