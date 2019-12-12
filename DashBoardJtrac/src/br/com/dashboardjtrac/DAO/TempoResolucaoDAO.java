package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.model.TempoResolucaoModel;
import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class TempoResolucaoDAO extends AbstractPostgresDAO{
	
	public List<TempoResolucaoModel> findAll(){
		
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select * "
					+ "from tb_param_tempo_resolucao ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<TempoResolucaoModel> list = new ArrayList<TempoResolucaoModel>();
			
			while (rs.next()) {
				
				String statusIni = rs.getString("status_inicio");
				
				TempoResolucaoModel oTempResol = new TempoResolucaoModel(statusIni, null);
				
				list.add(oTempResol);
			}
			
			return list;
		} catch (Exception e) {
			System.err.println("Erro no TempoResolucaoDAO: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		return null;
	}
	
}
