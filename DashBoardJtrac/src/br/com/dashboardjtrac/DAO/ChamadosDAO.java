package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.funcoes.CalculaTempos;
import br.com.dashboardjtrac.funcoes.FuncoesUtis;
import br.com.dashboardjtrac.funcoes.VerificaPeriodo;
import br.com.dashboardjtrac.model.ChamadosModel;
import br.com.dashboardjtrac.model.HistoryModel;
import br.com.dashboardjtrac.model.ListaStatusJtrac;
import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class ChamadosDAO extends AbstractPostgresDAO{
	
	public List<ChamadosModel> findAllChamados(LocalDate dataInicio, LocalDate dataFim, String prefix_code, Integer sequence_num){
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		VerificaChamadoN2DAO n2DAO = new VerificaChamadoN2DAO();
		
		try {
			
			System.out.println("Aguarde. Processando dados...");
			
			cn = getConnection();
			
			String comando = "select i.id, i.sequence_num, s.prefix_code, i.time_stamp, i.status, s.id as \"space_id\","
					+ "i.cus_int_02, i.cus_int_04, c.rotulo as \"categoria\", st.rotulo as \"status_texto\", p.rotulo as \"prioridade\", i.summary "
					+ "from items i "
					+ "inner join spaces s on s.id = i.space_id "
					+ "inner join tb_param_categoria c on c.cus_int_02 = i.cus_int_02 and c.prefix_code = s.prefix_code "
					+ "inner join tb_param_status st on st.status = i.status and st.prefix_code = s.prefix_code "
					+ "inner join tb_param_prioridades p on p.cus_int_04 = i.cus_int_04 and s.prefix_code = p.prefix_code "
					//+ "left join history h on i.id = h.item_id and h.status = 99 "
					+ "where s.prefix_code in ('SURSIS', 'WMS','TSS','TSSCOL','TOTVS') "
					+ "and i.status = 99 ";
					//+ "order by i.id desc "
					//+ "limit 50 ";
			
			if(dataInicio != null && dataFim != null) {
				comando += "AND i.time_stamp between '" + dataInicio + " 00:00:00' and '" + dataFim + " 23:59:59' ";
			}
			
			if(prefix_code != null) {
				comando = comando + "and s.prefix_code = '" + prefix_code + "' ";
			}
			
			if(sequence_num > 0) {
				comando = comando + "and i.sequence_num = '" + sequence_num + "' ";
			}

			comando = comando + "order by i.id desc ";
			
			if(dataInicio == null || dataFim == null) {
				comando = comando + "limit 30 ";
			}
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<ChamadosModel> list = new ArrayList<ChamadosModel>();
			
			//ChamadosAccessDAO accessDAO = new ChamadosAccessDAO();
			
			while (rs.next()) {
				
				Long id = rs.getLong("id");
				Long spaceId = rs.getLong("space_id");
				Long chamado = rs.getLong("sequence_num"); 
				String sistema = rs.getString("prefix_code");
				String sistemaVerificaSupN2 = sistema;
				String dataAbertura = FuncoesUtis.converteTimeStamp(rs.getTimestamp("time_stamp"));
				int statusID = rs.getInt("status");
				String status = rs.getString("status_texto"); //accessDAO.findStatus(sistema, statusID);
				//String status = StatusParam.findStatusParam(sistema, statusID); // Desativado(esse codigo usaria uma classe que seria mais rapido)
				//System.out.println("Sistema: " + sistema + "	-	Num chamado: " + chamado);//*******************************************************************
				//int cus_int_02 = rs.getInt("cus_int_02");
				
				String categoria = rs.getString("categoria");
				//if(cus_int_02 != 0) {
					//categoria = accessDAO.findCategoria(sistema, cus_int_02);
				//}
				
				if(sistema.equalsIgnoreCase("WMS")) {
					sistema = VerificaPeriodo.verificaPeriodo(sistema, dataAbertura);
				}
				
				
				String dataFechamneto = null;
				
				if(statusID == 99) {
					dataFechamneto = getDataFechamento(chamado, spaceId, statusID);
				}
				
				//int cus_int_04 = rs.getInt("cus_int_04");
				String prioridade = rs.getString("prioridade");
				
				//if(cus_int_04 != 0) {
					//prioridade = accessDAO.findPrioridade(cus_int_04);
				//}
				
				String summary = rs.getString("summary").replaceAll(";", "");
				
				String suporteN2 = null;
				if(sistemaVerificaSupN2.equalsIgnoreCase("WMS")) {
					suporteN2 = n2DAO.findN2Suporte(chamado, spaceId);
				}else {
					suporteN2 = "";
				}
				
				String efforTime = tempoNaFila(chamado, spaceId, categoria, prioridade);
				
				//System.out.println(efforTime);
				
				//System.out.println(prioridade);
				
				ChamadosModel chamados = new ChamadosModel(id, 
						chamado, 
						sistema,
						dataAbertura,
						statusID,
						status,
						dataFechamneto,
						categoria,
						prioridade,
						efforTime,
						summary,
						suporteN2
						);
				
				list.add(chamados);
			}
			System.out.println("Processamento Concluido.");
			
			return list;
			
		} catch (Exception e) {
			System.out.println("Erro: "+e);
		}finally {
			closeResources(cn, ps, rs);
		}
		
		return null;
	}
	
	private String getDataFechamento(Long sequence_num, Long space_id, int status_id) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String dataFechamento = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select h.status, h.time_stamp "
					+ "from items i "
					+ "inner join history h on i.id = h.item_id "
					+ "inner join spaces s on s.id = i.space_id "
					+ "where 1=1 "
					+ "and i.sequence_num = " + sequence_num + " "
					+ "and space_id = " + space_id + " "
					+ "and h.status = 99"
					+ "order by h.time_stamp ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				if(rs.getInt("status") == 99) {
					dataFechamento = FuncoesUtis.converteTimeStamp(rs.getTimestamp("time_stamp"));
				}
			}
			
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		
		return dataFechamento;
	}
	
	private String tempoNaFila(Long sequence_num, Long space_id, String categoria, String prioridade) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String tempoNaFila = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select h.*, i.sequence_num, s.prefix_code, st.rotulo, i.space_id "
					+ "from items i "
					+ "inner join history h on i.id = h.item_id "
					+ "inner join spaces s on s.id = i.space_id "
					+ "inner join tb_param_status st on st.status = h.status and st.prefix_code = s.prefix_code "
					+ "where i.sequence_num = '" + sequence_num + "' "
					//+ "where i.sequence_num = 29849 "
					+ "and space_id = '" + space_id + "' "
					//+ "and i.space_id = 6 "
					+ "order by h.time_stamp ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<HistoryModel> listaGeral = new ArrayList<>();
			
			while (rs.next()) {
				
				Integer statusId = rs.getInt("status");
				String time_stamp = FuncoesUtis.converteTimeStamp(rs.getTimestamp("time_stamp"));
				String rotulo = rs.getString("rotulo");
				
				//System.out.println("sequence_num : " + sequence_num2 + " " + "Status: " + statusId + " Timestamp: " + time_stamp);
				
				HistoryModel historyModel = new HistoryModel(statusId, time_stamp, rotulo);
				
				listaGeral.add(historyModel);
				
			}
			
			//tempoNaFila = CalculaTempos.calculaTempoEmFila(listaGeral);
			
			tempoNaFila = CalculaTempos.calculaTempoStatus(listaGeral, categoria, prioridade);
			
			//System.out.println(tempoNaFila);
			
			return tempoNaFila;
			
			
			
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		return tempoNaFila;
		
	}
	
	
	public List<ListaStatusJtrac> findAllStatusJtrac(){
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select status, rotulo "
					+ "from tb_param_status "
					+ "group by status, rotulo "
					+ "order by status ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<ListaStatusJtrac> list = new ArrayList<ListaStatusJtrac>();
			
			while (rs.next()) {
				
				Integer status = rs.getInt("status");
				String rotulo = rs.getString("rotulo");
				
				ListaStatusJtrac listaStatusJtrac = new ListaStatusJtrac(status, rotulo);
				
				list.add(listaStatusJtrac);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		return null;
	}
	


}
