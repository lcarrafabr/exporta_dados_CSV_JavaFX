package br.com.dashboardjtrac.funcoes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.dashboardjtrac.DAO.ChamadosDAO;
import br.com.dashboardjtrac.DAO.MetaTempoResolutionDAO;
import br.com.dashboardjtrac.DAO.TempoResolucaoDAO;
import br.com.dashboardjtrac.model.HistoryModel;
import br.com.dashboardjtrac.model.ListaStatusJtrac;
import br.com.dashboardjtrac.model.MetaTempoResolutionModel;
import br.com.dashboardjtrac.model.TempoResolucaoModel;
import javafx.scene.control.Alert.AlertType;

public class CalculaTempos {
	
	
//	public static Long calculaTempoEmFila(List<HistoryModel>lista) {
//		
//		try {
//			
//			LocalDateTime open = null;
//			LocalDateTime onGoing = null;
//			Integer status = null;
//			
//			//long duracaoHora = 0;
//			long duracaoMinuto = 0;
//			//long duracaoSegundo;
//			long resultTime = 0;
//			
//			int total = lista.size();
//			
//			for (int i = 0; i < total; i++) {
//				
//				if(lista.get(i).getStatus().equals(1)) {
//
//					open = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
//				}
//				
//				if(lista.get(i).getStatus().equals(2)) {
//					
//					onGoing = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
//				}
//				
//				//duracaoHora = ChronoUnit.HOURS.between(dataPrimeiroRegistro, dataUltimoPeriodo);
//				//duracaoMinuto = ChronoUnit.MINUTES.between(dataPrimeiroRegistro, dataUltimoPeriodo);
//				//duracaoSegundo = ChronoUnit.SECONDS.between(dataPrimeiroRegistro, dataUltimoPeriodo);
//				
//				if(open != null && onGoing != null) {
//					
//					duracaoMinuto = ChronoUnit.MINUTES.between(open, onGoing);
//					
//					resultTime = resultTime + duracaoMinuto;
//					
//					open = null;
//					onGoing = null;
//				}
//				//System.out.println("ResultTime: " + resultTime);
//				
//			}
//			//System.out.println("ResultTime: " + resultTime);
//			return resultTime;
//			//System.out.println(resultTime);
//			
//		} catch (Exception e) {
//			System.err.println(e);
//		}
//		return null;
//	}
	
	
	public static String calculaTempoStatus(List<HistoryModel>lista, String categoria, String prioridade) {
		
		ChamadosDAO dao = new ChamadosDAO();
		
		//List<ListaStatusJtrac> listaStatus = dao.findAllStatusJtrac();
		
		LinkedList<ListaStatusJtrac> listaStatus = new LinkedList<ListaStatusJtrac>();
		
		int totalSizeListaHistory = lista.size();	//tamanho da lista History
		
		LocalDateTime ini = null;
		LocalDateTime fim = null;
		
		long duracaoMinuto = 0;
		long duracaoHoras = 0;
		
		String statusInicio = "Ongoing";
		String StatusFim = "Fechado";
		
		LocalDateTime statusIniDate = null;
		LocalDateTime statusFimDate = null;
		
		
		
		for (int i = 0; i < totalSizeListaHistory; i++) {	
			ListaStatusJtrac listaJtrac = new ListaStatusJtrac();

			//System.out.println(statusId);
 			
 			/**Calcula o status fechado, pega o primeiro registro e calcula com o ultimo*/
 			if(i == totalSizeListaHistory -1) {
				//System.out.println("Status: " + lista.get(i).getStatus() + "	Rotulo: " + lista.get(i).getPrefix_code() + "	Indice: " + i);
				
				ini = FuncoesUtis.parseToLocalDateTime(lista.get(0).getTime_stamp());
				fim = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
				duracaoMinuto = ChronoUnit.MINUTES.between(ini, fim);
				duracaoHoras = ChronoUnit.HOURS.between(ini, fim);
				
				
 				listaJtrac.setStatus(lista.get(i).getStatus());
				listaJtrac.setTime(duracaoMinuto);
				listaJtrac.setRotulo(lista.get(i).getPrefix_code());
				
				listaStatus.add(listaJtrac);
				
				//System.out.println("Status: " + lista.get(i).getStatus() + " Tempo: " + duracaoMinuto);
			}
 			
			
			if(i < totalSizeListaHistory -1) {
				
				//System.out.println("Status: " + lista.get(i).getStatus() + "	Rotulo: " + lista.get(i).getPrefix_code() + "	Indice: " + i);
				//totalSizeListStatus = listaStatus.size();	
				
				ini = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
				fim = FuncoesUtis.parseToLocalDateTime(lista.get(i+1).getTime_stamp());
				duracaoMinuto = ChronoUnit.MINUTES.between(ini, fim);
				
				
				
 				listaJtrac.setStatus(lista.get(i).getStatus());
				listaJtrac.setTime(duracaoMinuto);
				listaJtrac.setRotulo(lista.get(i).getPrefix_code());
				
				listaStatus.add(listaJtrac);
				
				//System.out.println(listaJtrac);
				
				//System.out.println("Status: " + lista.get(i).getStatus() + " Tempo: " + duracaoMinuto);
				
			}
			
//			if(lista.get(i).getPrefix_code().contains(statusInicio) && statusIniDate == null) {
//				
//				//System.out.println("status inicio: " + statusInicio + "	= " + lista.get(i).getPrefix_code());
//				statusIniDate = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
//			}
//			
//			if(lista.get(i).getPrefix_code().contains(StatusFim) && statusFimDate == null) {
//				
//				//System.out.println("status fim: " + StatusFim + "	= " + lista.get(i).getPrefix_code());
//				statusFimDate = FuncoesUtis.parseToLocalDateTime(lista.get(i).getTime_stamp());
//			}
			
		
		}
		
/********************************************* S E G U N D A  P A R T E **********************************************************************/		
		try {
			
			FuncoesUtis.ordenaPorStatus(listaStatus);
			
			for (int j = 0; j < listaStatus.size()-2; j++) {
				
				//System.out.println("Range: " + listaStatus.size() + "	index: " + j + "	 -	Status: " + listaStatus.get(j).getStatus());
				int status1 = listaStatus.get(j).getStatus();
				
				for (int j2 = 0; j2 < listaStatus.size(); j2++) {

					int status2 = listaStatus.get(j2).getStatus();

					if (j != j2) {
						if (status1 == status2) {
							listaStatus.get(j).setTime(listaStatus.get(j).getTime() + listaStatus.get(j2).getTime());
							//System.out.println("Range: " + listaStatus.size() + "	index: " + j + "	 -	Status: " + listaStatus.get(j).getStatus());
							listaStatus.remove(j2);
						}
					}	
				}
				//System.out.println("Range: " + listaStatus.size() + "	index: " + j + "	 -	Status: " + listaStatus.get(j).getStatus());
			}
			FuncoesUtis.ordenaPorStatus(listaStatus);
			for (int j = 0; j < listaStatus.size()-1; j++) {
				
				//System.out.println("Range: " + listaStatus.size() + "	index: " + j + "	 -	Status: " + listaStatus.get(j).getStatus());
				int status1 = listaStatus.get(j).getStatus();
				
				for (int j2 = 0; j2 < listaStatus.size(); j2++) {

					int status2 = listaStatus.get(j2).getStatus();

					if (j != j2) {
						if (status1 == status2) {
							listaStatus.get(j).setTime(listaStatus.get(j).getTime() + listaStatus.get(j2).getTime());
							listaStatus.remove(j2);
						}
					}
				}
			}
			
			
			
		} catch (Exception e) {
			FuncoesUtis.messageAlert("Erro no processamento da base Jtrac: " + e, AlertType.ERROR);
			System.err.println("Erro no processamento da base Jtrac: " + e);
			e.printStackTrace();
		}
		
		List<ListaStatusJtrac> indice = new ArrayList<ListaStatusJtrac>();
		
		indice = dao.findAllStatusJtrac();
		
		String result = "";
		String verifica = "";
		int operador = -1;
		int operador2 = -1;
		
		String tempoResolucao = calculaTempoResolucao(listaStatus, categoria, prioridade);
		
		for (int i = 0; i < indice.size(); i++) {
			
			//System.out.println(indice.get(i).getStatus() + "	" + indice.get(i).getRotulo());
			//result = result + ", "  + indice.get(i).getRotulo();
			//System.out.println(indice.get(i).getRotulo() + ", ");
			
			if((!verifica.equalsIgnoreCase(result) && result != "") && operador == 1) {
				
				verifica = result;
				operador = -1;
			}else {
				
				if(result.equalsIgnoreCase("")) {
					result = "";
				}else {
					result = result + ",";
					verifica = result;
				}

				operador = -1;
				operador2 = -1;
			}
			
			/**FOR que irá organizar a ordem dos tempos dos chamados*/
			for (int j = 0; j < listaStatus.size(); j++) {
				
				if(operador2 == 1) {
					result = result + ",";
					operador2 = -1;
				}
				
				
				if((indice.get(i).getStatus() == listaStatus.get(j).getStatus()) && indice.get(i).getRotulo().equalsIgnoreCase(listaStatus.get(j).getRotulo())) {
					//System.out.println("Indice: " + indice.get(i).getStatus() + "	Status: " + listaStatus.get(j).getStatus());
					//System.out.println("Rotulo Indice: " + indice.get(i).getRotulo() + "	Rotulo Status: " + listaStatus.get(j).getRotulo());
					
					//result = result + listaStatus.get(j).getTime() + ", ";
					//result = result + "\"" + listaStatus.get(j).getTime() + "\"," + "\"" + tempoResolucao + "\"";
					
					
					if(j == listaStatus.size()-1) {
						if(!listaStatus.get(j).getRotulo().equalsIgnoreCase("Fechado")) {
//							result = result + ",";
							operador2 = 1;
							//System.out.println("Entrei nesse trecho poha. STATUS = " + listaStatus.get(j).getRotulo());
						}
					}
					
					result = result + "\"" + listaStatus.get(j).getTime() + "\",";
					operador = 1;
				
				}
				
			}
			
		}
		
		result = result + "\"" + tempoResolucao + "\"";
		
		//System.out.println(result);

		
		//System.out.println("**********************************************************************");
		
		
		return result;
	}
	
	
	public static String calculaTempoResolucao(List<ListaStatusJtrac> listaStatusJtrac, String categoria, String prioridade) {
		
		TempoResolucaoDAO dao = new TempoResolucaoDAO();
		
		String tempoResolucao = "";
		
		long tempoMinutos = 0;
		
		List<TempoResolucaoModel> listTempResol = dao.findAll();
		
		for (int i = 0; i < listTempResol.size(); i++) {
			//System.out.println("syso i: " + i);
			
			for (int j = 0; j < listaStatusJtrac.size(); j++) {
				
				String statusAbrv = listaStatusJtrac.get(j).getRotulo().substring(0,4);
				
				
				if(listTempResol.get(i).getStatusInicio().contains(statusAbrv)) {
					
					//System.out.println("Encontrei: " + listTempResol.get(i).getStatusInicio() + "	Time: " + listaStatusJtrac.get(j).getTime());
					
					tempoMinutos += listaStatusJtrac.get(j).getTime();
					
				}
				
			}
			
		}
		//System.out.println("Tempo minutos = " + tempoMinutos);
		//System.out.println("****************************************************************************************************");
		
		//calculaTempoResolutionFinal(categoria, prioridade, tempoMinutos);
		
		//return tempoResolucao;
		
		return calculaTempoResolutionFinal(categoria, prioridade, tempoMinutos);
		
	}
	
	
	private static String calculaTempoResolutionFinal(String categoria, String prioridade, Long tempoEmMinutos) {
		
		MetaTempoResolutionDAO dao = new MetaTempoResolutionDAO();
		List<MetaTempoResolutionModel>list = dao.findMeta(categoria, prioridade);
		
		String retorno = null;
		
		Integer meta = 0;
		
		if ((!categoria.isEmpty() || categoria != null) && (!prioridade.isEmpty() || prioridade != null)) {

			if (!list.isEmpty() && list.get(0).getCategoria().equalsIgnoreCase(categoria) && list.get(0).getPrioridade().equalsIgnoreCase(prioridade)) {

				meta = list.get(0).getMeta();
				
				if(tempoEmMinutos <= meta) {
					retorno = "SUCESSO";
				}else {
					retorno = "FALHA";
				}
				
			}else {
				retorno = "";
			}

		}else {
			retorno = "Sem categoria ou prioridade.";
		}
		
		return retorno;
		
	}
	
	
}
