package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.dashboardjtrac.persistence.AbstractAccessDAO;

public class ChamadosAccessDAO extends AbstractAccessDAO{
	
	public String findStatus(String prefix_code, int statusId) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String status = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "SELECT * "
					+ "FROM TB_PARAM_STATUS "
					+ "WHERE PREFIX_CODE = '" + prefix_code + "' "
							+ "and status = '" + statusId + "' ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				status = rs.getString("rotulo");
				
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		return status;
	}
	
	public String findCategoria(String prefix_code, int cus_int_02) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String categoria = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "SELECT * "
					+ "FROM TB_PARAM_CATEGORIA "
					+ "WHERE PREFIX_CODE = '" + prefix_code + "' "
							+ "and cus_int_02 = '" + cus_int_02 + "' ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				categoria = rs.getString("rotulo");
				
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		return categoria;
	}
	
	public String findPrioridade(int cus_int_04) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String prioridade = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "SELECT * "
					+ "FROM TB_PARAM_PRIORIDADES "
					+ "WHERE cus_int_04 = '" + cus_int_04 + "' ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				prioridade = rs.getString("rotulo");
				
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		return prioridade;
	}

}
