package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class PrioridadesJtracParamDAO extends AbstractPostgresDAO{
	
	public List<String> findAll(){
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select distinct rotulo "
					+ "from tb_param_prioridades ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<String> list = new ArrayList<String>();
			
			while (rs.next()) {
				
				list.add(rs.getString("rotulo"));
			}
			
			return list;
		} catch (Exception e) {
			System.err.println("Erro no PrioriradesJtracparamDAO: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		
		return null;
	}

}
