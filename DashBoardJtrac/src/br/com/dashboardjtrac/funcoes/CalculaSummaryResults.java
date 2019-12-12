package br.com.dashboardjtrac.funcoes;

import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.DAO.MetaTempoResolutionDAO;
import br.com.dashboardjtrac.DAO.MetaTempoRespostaDAO;
import br.com.dashboardjtrac.DAO.PrioridadesJtracParamDAO;
import br.com.dashboardjtrac.DAO.SpacesJtrac;
import br.com.dashboardjtrac.Enumerics.EnumPeriodosDoDia;
import br.com.dashboardjtrac.model.ChamadosModel;
import br.com.dashboardjtrac.model.MetaTempoResolutionModel;
import br.com.dashboardjtrac.model.MetaTempoRespostaModel;

public class CalculaSummaryResults {
	
	
	public static List<String> calculaSummary(List<ChamadosModel> listChamados) {
		
		int qtdSucessoIncidente = 0;
		int qtdFalhaIncidente = 0;
		String verificaSucessoIncidente = null;
		String verificaSucessoSR = null;
		String prioridade = null;
		String sistema = null;
		String openIncidente[] = null;
		String openSR[] = null;
/************************************/
		int qtdSucessoRS = 0;
		int qtdFalhaRS = 0;
		
		List<String> listCSV = new ArrayList<String>();
		
		SpacesJtrac daoSpace = new SpacesJtrac();
		PrioridadesJtracParamDAO daoPrioridade = new PrioridadesJtracParamDAO();
		
		List<String> prefix_code = daoSpace.findPrefixCodeSistema();
		List<String> prioridadesList = daoPrioridade.findAll();
		List<String> categoriaList = new ArrayList<String>();
		String categoria = null;
		
		prefix_code.add("WMS - " + EnumPeriodosDoDia.MANHA.getPeriodo());
		prefix_code.add("WMS - " + EnumPeriodosDoDia.TARDE.getPeriodo());
		prefix_code.add("WMS - " + EnumPeriodosDoDia.NOITE.getPeriodo());
		prefix_code.add("WMS - " + EnumPeriodosDoDia.SEM_COBERTURA.getPeriodo());
		
		categoriaList.add("Incidente");
		categoriaList.add("Requisição de Serviço (SR)");
		
		//System.out.println("Sistema" + "	Prioridade"  + "	QTD SUCESSO" + "	QTD Falha");
		
		
		for (int h = 0; h < categoriaList.size(); h++) {
			
			categoria = categoriaList.get(h);
		
		for (int i = 0; i < prefix_code.size(); i++) {
			
			if(prefix_code.get(i).equalsIgnoreCase("WMS")) {/**Para de verificar o WMS*/
				continue;
			}
			
			sistema = prefix_code.get(i);
			
			for (int j = 0; j < prioridadesList.size(); j++) {
				
				//System.out.println(sistema + "	" + prioridadesList.get(j));
				
				prioridade = prioridadesList.get(j);
				
				for (int k = 0; k < listChamados.size(); k++) {
					
					//System.out.println(listChamados.get(k).getSummary());
					
					if(listChamados.get(k).getPrioridade().equalsIgnoreCase(prioridade) && listChamados.get(k).getPrefix_code().equalsIgnoreCase(sistema) && 
							//listChamados.get(k).getCategoria().equalsIgnoreCase("Incidente")) {
							listChamados.get(k).getCategoria().equalsIgnoreCase(categoria)) {

						
						verificaSucessoIncidente = listChamados.get(k).getEffortTime();
						
						//System.out.println(verificaSucesso);
						
						openIncidente = verificaSucessoIncidente.split(",");
						
						//System.out.println(open[0].replaceAll("\"", ""));
						
						int openIntIncidente = Integer.parseInt(openIncidente[0].replaceAll("\"", ""));
						
						
						if(verificaIRIncidente(categoria, prioridade, openIntIncidente, "resposta")) {		
							qtdSucessoIncidente ++;
						}else {
							qtdFalhaIncidente ++;
						}
						
					}
					
					
					
					if(listChamados.get(k).getPrioridade().equalsIgnoreCase(prioridade) && listChamados.get(k).getPrefix_code().equalsIgnoreCase(sistema) && 
							//listChamados.get(k).getCategoria().equalsIgnoreCase("Requisição de Serviço (SR)")) {
							listChamados.get(k).getCategoria().equalsIgnoreCase(categoria)) {
						
						verificaSucessoSR = listChamados.get(k).getEffortTime();
						
						openSR = verificaSucessoSR.split(",");
						
						int openIntSR = Integer.parseInt(openSR[1].replaceAll("\"", ""));
						
						if(verificaIRIncidente(categoria, prioridade, openIntSR, "resolucao")) {
							
							qtdSucessoRS ++;
						}else {
							qtdFalhaRS ++;
						}
						
						//System.out.println(verificaSucesso);
						
//						if(verificaSucessoSR.contains("SUCESSO")) {
//							qtdSucessoRS ++;
//						}
//						if(verificaSucessoSR.contains("FALHA")) {
//							qtdFalhaRS ++;
//						}
					}
	
				}
				
				String percentIncident = calculaResponse(qtdSucessoIncidente, qtdFalhaIncidente);
				String percentRS = calculaResponse(qtdSucessoRS, qtdFalhaRS);
				
				
				String formatar = formataPadraoCSV(sistema, prioridade, categoria, qtdSucessoIncidente, qtdFalhaIncidente, percentIncident, qtdSucessoRS, qtdFalhaRS, percentRS);
				
				listCSV.add(formatar);
//				
//				System.out.println(sistema + "	" + prioridadesList.get(j) + "	" + qtdSucesso + "		" + qtdFalha + "	" + percentIncident
//						+ "	" + qtdSucessoRS + "		" + qtdFalhaRS + "	" + percentRS);
				
				qtdSucessoIncidente = 0;
				qtdFalhaIncidente = 0;
				qtdSucessoRS = 0;
				qtdFalhaRS = 0;
			}
			
		}
		}

		
		//return listCSV;
		return listCSV;
		
//		for (int i = 0; i < listCSV.size(); i++) {
//			
//			System.out.println(listCSV.get(i));
//		}

}
	
	private static String calculaResponse(Integer qtdSucesso, Integer qtdFalha) {
		
		//System.out.println("Verifica variaveis: qtdSucc: " + qtdSucesso + "	qtdFail: " + qtdFalha);
		
		Double result = (double) (qtdSucesso + qtdFalha);
		Double percentOk = 0.0;
		Double percentFalha = 0.0;
		Double resultFinal = 100.0;
		String resultFormatado = "100.0";
		
//		if(qtdFalha > 0) {
//			
//			percentFalha = (double) ((qtdFalha / result) * 100);
//			//System.out.println((double) ((qtdFalha / result) * 100) + "	Falha");
//			resultFinal = percentFalha;
//		}
		if(qtdSucesso > 0) {
			percentOk = (double) ((qtdSucesso / result) * 100);
			//System.out.println((double) ((qtdSucesso / result) * 100) + "	Ok");
			resultFinal = percentOk;
		}else if (qtdSucesso == 0 && qtdFalha > 0) {
			resultFinal = 0.0;
		}
		
		
		resultFormatado = FuncoesUtis.formataDouble2Casas(resultFinal);
		
		//System.out.println("Result: " + result + "	OK: " + percentOk + "	Fail: " + percentFalha + "	|	QtdSuccesso: " + qtdSucesso + "	QTDFalha: " + qtdFalha);
		return resultFormatado;
	}
	
	
	private static String formataPadraoCSV(String sistema, String prioridade, String categoria, int qtdSucesso, int qtdFalha, String percentIncident, int qtdSucessoRS, int qtdFalhaRS, String percentRS) {
		
		String aspas = "\"";
		String comma = ",";
		String formato = aspas + sistema + aspas + comma + 
						aspas + prioridade + aspas + comma + 
						aspas + categoria + aspas + comma + 
						aspas + Integer.toString(qtdSucesso) + aspas + comma + 
						aspas + Integer.toString(qtdFalha) + aspas + comma + 
						aspas + percentIncident+ aspas + comma + 
						aspas + Integer.toString(qtdSucessoRS) + aspas + comma + 
						aspas + Integer.toString(qtdFalhaRS) + aspas + comma + 
						aspas + percentRS + aspas + comma;
		
		return formato;
		
	}
	
	
	/**Usando para verificar e comparar a meta de Open do Incidente de resposta e SR de resposta*/
	static Integer meta;
	
	private static boolean verificaIRIncidente(String categoria, String prioridade, int openInt, String tipo) {
		
		boolean retorno = false;
		
		meta = 0;
		
		//if("Incidente".equalsIgnoreCase(categoria)) {
		if("resposta".equalsIgnoreCase(tipo)) {
			
			MetaTempoRespostaDAO dao = new MetaTempoRespostaDAO();
			
			List<MetaTempoRespostaModel> metaList = new ArrayList<MetaTempoRespostaModel>();
			metaList = dao.findMeta(categoria, prioridade);

			metaList.stream().forEach((list) ->{
				meta = list.getMeta();
				//System.out.println(list.getMeta());
			});
			
			if(openInt <= meta) {
				retorno = true;
			}
			
			//System.out.println("Prior: " + prioridade + "	Categ: " + categoria + "	OpenInt: " + openInt + "	Meta: " + meta);
		
		
		//}else if ("Requisição de Serviço (SR)".equalsIgnoreCase(categoria)) {
		}else if ("resolucao".equalsIgnoreCase(tipo)) {
			
			MetaTempoResolutionDAO dao = new MetaTempoResolutionDAO();
			
			List<MetaTempoResolutionModel> metaList = new ArrayList<MetaTempoResolutionModel>();
			metaList = dao.findMeta(categoria, prioridade);

			metaList.stream().forEach((list) ->{
				meta = list.getMeta();
				//System.out.println(list.getMeta());
			});
			
			if(openInt <= meta) {
				retorno = true;
			}
			
			//System.out.println("Prior: " + prioridade + "	Categ: " + categoria + "	OpenInt: " + openInt + "	Meta: " + meta);
			
		} else {
			retorno = false;
		}
		
		return retorno;
	}
	

}
