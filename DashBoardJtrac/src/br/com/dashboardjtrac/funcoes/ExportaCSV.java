package br.com.dashboardjtrac.funcoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.DAO.ChamadosDAO;
import br.com.dashboardjtrac.model.ChamadosModel;
import br.com.dashboardjtrac.model.ListaStatusJtrac;
import javafx.scene.control.Alert.AlertType;

public class ExportaCSV {
	
	public static void exportaCSV(List<ChamadosModel> listChamados, String path) throws FileNotFoundException {
		
		String chamados = "";
		String sistema = "";
		String status = "";
		String dataAbertura = "";
		String dataFechamento = "";
		String categoria = "";
		String prioridade = "";
		String effortTime = "";
		String summary = "";
		String suporteN2 = "";
		
		String textoFormatado = null;
		
		String nomeArquivo = "Base_de_chamados_" + FuncoesUtis.getDataToNameExport(LocalDateTime.now()) + ".csv";
		
		//System.out.println(nomeArquivo);
		
		//PrintWriter pw = new PrintWriter(new File(path+"\\export_.csv"));
		PrintWriter pw = new PrintWriter(new File(path + "\\" + nomeArquivo));
		
		StringBuilder sb = new StringBuilder();
		
		
		/**Inserir Header*/
		
		String header = null;
		
		ChamadosDAO dao = new ChamadosDAO();
		List<ListaStatusJtrac> indice = new ArrayList<ListaStatusJtrac>();
		
		indice = dao.findAllStatusJtrac();
		
		header = "\"" + "Chamados"  + "\"" + ","
		+"\"" + "Sistema"  + "\"" + ","
		+"\"" + "Status"  + "\"" + ","
		+"\"" + "Data abertura"  + "\"" + ","
		+"\"" + "Data Fechamento"  + "\"" + ","
		+"\"" + "Categoria"  + "\"" + ","
		+"\"" + "Prioridade"  + "\"" + ","
		+"\"" + "Summary"  + "\"" + ","
		+"\"" + "SuporteN2"  + "\"" + ",";
		
		for (int i = 0; i < indice.size(); i++) {
			
			header = header + "\"" + indice.get(i).getRotulo() + "\"" + ",";
		}
		
		header += "\"" + "Resolução" + "\"";
		
		sb.append(header);
		sb.append("\n");
		
		
		for (int i = 0; i < listChamados.size(); i++) {
			
			
			chamados = listChamados.get(i).getSequence_num().toString();
			sistema = listChamados.get(i).getPrefix_code();
			status = listChamados.get(i).getStatus();
			dataAbertura = listChamados.get(i).getDataAbertura();
			dataFechamento = listChamados.get(i).getDataFechamento();
			categoria = listChamados.get(i).getCategoria();
			prioridade = listChamados.get(i).getPrioridade();
			effortTime = listChamados.get(i).getEffortTime();
			summary = listChamados.get(i).getSummary();
			suporteN2 = listChamados.get(i).getSupoprteN2();
			
			
			textoFormatado = "\"" + chamados + "\"" + ","
			+"\"" + sistema + "\"" + ","
			+"\"" + status + "\"" + ","
			+"\"" + dataAbertura + "\"" + ","
			+"\"" + dataFechamento + "\"" + ","
			+"\"" + categoria + "\"" + ","
			+"\"" + prioridade + "\"" + ","
			+"\"" + summary + "\"" + ","
			+"\"" + suporteN2 + "\"" + ","
			+effortTime;	


				sb.append(textoFormatado);
				sb.append("\n");
	

		}//Fim do FOR
		
		
		pw.write(sb.toString());
		pw.close();
		FuncoesUtis.messageAlert("Exportado com sucesso", AlertType.INFORMATION);
		
	}
	
	
	public static void exportaResultadosCSV(List<String> listResultados, String path) {
		
		PrintWriter pw = null;
		
		try {
			
			String nomeArquivo = "resultados_" + FuncoesUtis.getDataToNameExport(LocalDateTime.now()) + ".csv";
			
			//System.out.println(nomeArquivo);
			
			//PrintWriter pw = new PrintWriter(new File(path+"\\export_.csv"));
			pw = new PrintWriter(new File(path + "\\" + nomeArquivo));
			
			StringBuilder sb = new StringBuilder();
			
			
			/**Inserir Header*/
			
			String header = null;
			
			header = "\"" + "Sistema"  + "\"" + ","
			+"\"" + "Prioridades"  + "\"" + ","
			+"\"" + "Categoria"  + "\"" + ","
			+"\"" + "Response OK"  + "\"" + ","
			+"\"" + "Response fail"  + "\"" + ","
			+"\"" + "% response"  + "\"" + ","
			+"\"" + "Resolution Ok"  + "\"" + ","
			+"\"" + "Resolution Fail"  + "\"" + ","
			+"\"" + "% Resolution"  + "\"" + ",";
			
			sb.append(header);
			sb.append("\n");
			
			
			for (int i = 0; i < listResultados.size(); i++) {


					sb.append(listResultados.get(i));
					sb.append("\n");
		

			}//Fim do FOR
			
			
			pw.write(sb.toString());
			pw.close();
			FuncoesUtis.messageAlert("Exportado com sucesso", AlertType.INFORMATION);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro no exportaResultadosCSV: " + e);
		}finally {
			pw.close();
		}
		
	}

}
