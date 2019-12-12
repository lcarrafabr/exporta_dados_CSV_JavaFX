package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.model.MetaTempoResolutionModel;
import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class MetaTempoResolutionDAO extends AbstractPostgresDAO{
	
	public List<MetaTempoResolutionModel>findMeta(String categoria, String prioridade){
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select * "
					+ "from tb_param_meta_temp_resolution "
					+ "where categoria = ? "
					+ "and prioridade = ? ";
			
			ps = cn.prepareStatement(comando);
			
			ps.setString(1, categoria);
			ps.setString(2, prioridade);
			
			rs = ps.executeQuery();
			
			List<MetaTempoResolutionModel> list = new ArrayList<MetaTempoResolutionModel>();
			
			while (rs.next()) {
				
				Long id = rs.getLong("id");
				String categoria2 = rs.getString("categoria");
				String prioridade2 = rs.getString("prioridade");
				Integer meta = rs.getInt("meta");
				
				MetaTempoResolutionModel oMeta = new MetaTempoResolutionModel(id, categoria2, prioridade2, meta);
				
				list.add(oMeta);
			}
			
			return list;
			
		} catch (Exception e) {
			System.err.println("Erro no MetaTempoResolutionDAO: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		return null;
	}
	
	

}
