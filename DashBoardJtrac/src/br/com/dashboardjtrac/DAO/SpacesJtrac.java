package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class SpacesJtrac extends AbstractPostgresDAO{
	
	
	public List<String> findPrefixCodeSistema(){
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			cn = getConnection();
			
			String comando = "select s.* "
					+ "from spaces s "
					+ "where s.prefix_code in ('SURSIS', 'WMS','TSS','TSSCOL','TOTVS')  ";
			
			ps = cn.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			List<String>list = new ArrayList<String>();
			
			while (rs.next()) {
				
				list.add(rs.getString("prefix_code"));
			}
			
			return list;
			
		} catch (Exception e) {
			System.err.println("Erro na DAO SPACES: " + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		
		return null;
	}
	
}
