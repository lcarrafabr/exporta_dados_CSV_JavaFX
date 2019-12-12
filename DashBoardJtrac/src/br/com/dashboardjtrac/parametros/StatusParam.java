package br.com.dashboardjtrac.parametros;

public class StatusParam {
	
	public static String findStatusParam(String prefix_code, int statusId) {
		
		String retorno = null;
		
		switch (prefix_code) {
		case "SURSIS":
			
			if(statusId == 1) {
				retorno = "Open";
			}
			if(statusId == 2) {
				retorno = "Ongoing";
			}
			if(statusId == 6) {
				retorno = "Pausado";
			}
			if(statusId == 7) {
				retorno = "Cancelado";
			}
			if(statusId == 8) {
				retorno = "Resolvido";
			}
			if(statusId == 9) {
				retorno = "Resolvido";
			}
			if(statusId == 99) {
				retorno = "Fechado";
			}
			
			break;

		default:
			retorno = null;
			break;
		}
		
		return retorno;
	}

}
