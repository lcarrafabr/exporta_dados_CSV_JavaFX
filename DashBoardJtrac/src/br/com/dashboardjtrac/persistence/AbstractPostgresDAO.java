package br.com.dashboardjtrac.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractPostgresDAO {
	
	public static Connection cn = null;

	public static java.sql.Connection getConnection() throws SQLException {
		String driver = "org.postgresql.Driver";
		
		String url = "jdbc:postgresql://105.103.138.101:5432/xxxxx";

		String username = "xxxxxx"; // leave blank if none
		String password = "xxxxxx"; // leave blank if none

		try {
			Class.forName(driver);
			cn = DriverManager.getConnection(url, username, password);
			cn.setAutoCommit(false);
			//System.out.println("Conectado! Postgres vivo");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println(e);
		}
		return cn;
	}
	
	public void closeResources(Connection cn, Statement ps, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
        if (cn != null) {
            try {
                cn.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
    }

}
