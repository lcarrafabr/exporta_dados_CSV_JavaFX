package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.model.MetaTempoRespostaModel;
import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class MetaTempoRespostaDAO extends AbstractPostgresDAO{
	
	public List<MetaTempoRespostaModel> findMeta(String categoria, String prioridade){
		
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select * "
					+ "from tb_param_meta_tempo_resposta "
					+ "where categoria = ? "
					+ "and prioridade = ? ";
			
			ps = cn.prepareStatement(comando);
			
			ps.setString(1, categoria);
			ps.setString(2, prioridade);
			
			rs = ps.executeQuery();
			
			List<MetaTempoRespostaModel> list = new ArrayList<MetaTempoRespostaModel>();
			
			while (rs.next()) {
				
				Integer id = rs.getInt("id");
				String categoria2 = rs.getString("categoria");
				String prioridade2 = rs.getString("prioridade");
				Integer meta = rs.getInt("meta");
				
				MetaTempoRespostaModel oMeta = new MetaTempoRespostaModel(id, categoria2, prioridade2, meta);
				
				list.add(oMeta);
				
			}
			
			return list;
			
		} catch (Exception e) {
			System.err.println("Erro no DAO MetaTempoResposta; " + e);
			e.printStackTrace();
		}finally {
			closeResources(cn, ps, rs);
		}
		return null;
	}

}
