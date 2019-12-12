package br.com.dashboardjtrac.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.dashboardjtrac.Enumerics.EnumSimNao;
import br.com.dashboardjtrac.persistence.AbstractPostgresDAO;

public class VerificaChamadoN2DAO extends AbstractPostgresDAO{
	
	public String findN2Suporte(Long chamado, Long space_id) {
		
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Long> list = new ArrayList<Long>();
		
		try {
			cn = getConnection();
			
			String comando = "select h.cus_int_05 " //Cada sistema tem uma coluna especifica, verificar isso se for incluir mais sistemas
					+ "from items i "
					+ "inner join history h on i.id = h.item_id "
					+ "inner join spaces s on s.id = i.space_id "
					//+ "where i.sequence_num = 3 "
					//+ "and space_id = 732733 ";
					+ "where i.sequence_num = ? "
					+ "and space_id = ? ";
			
			ps = cn.prepareStatement(comando);
			
			ps.setLong(1, chamado);
			ps.setLong(2, space_id);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				
				Long idSuporteN2 = rs.getLong("cus_int_05");
				
				list.add(idSuporteN2);
				
			}
			
			String verificaSuporteN2 = verificaExisteN2(list);
			
			return verificaSuporteN2;
			
		} catch (Exception e) {
			System.out.println("Erro no findN2Suporte. \n" + e);
		}finally {
			closeResources(cn, ps, rs);
		}
		
		return null;
	}
	
	private static String verificaExisteN2(List<Long> listN2) {
		
		String possuiChamadoN2 = "";
		
		for (int i = 0; i < listN2.size(); i++) {
			
			if(listN2.get(i).equals(2L)) {
				
				possuiChamadoN2 = EnumSimNao.SIM.getOpcao();
				break;
			}else if(listN2.get(i).equals(1L)) {
				
				possuiChamadoN2 = EnumSimNao.NAO.getOpcao();
			}
			
		}
		//System.out.println(possuiChamadoN2);
		return possuiChamadoN2;
	}

}
