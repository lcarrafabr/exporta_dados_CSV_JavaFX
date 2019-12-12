package br.com.dashboardjtrac.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractAccessDAO {

	public static Connection con = null;

	public static java.sql.Connection getConnection() throws SQLException {
		String driver = "net.ucanaccess.jdbc.UcanaccessDriver";

		String url = "jdbc:ucanaccess://C:\\Base_conhecimento\\base_parametros_java.accdb";
		//String url = "jdbc:ucanaccess://\\\\105.112.11.49\\logistics\\SL_INNOVATION\\40-Support\\40 - Systems\\SURSIS\\Programa\\Relatorios\\BASE_JAVA\\base_parametros_java.accdb";
		
		//String url = "jdbc:ucanaccess://.\\..\\DB\\base_conhecimento_db.accdb";

		String username = ""; // leave blank if none
		String password = ""; // leave blank if none

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.setAutoCommit(false);
			//System.out.println("Conectado no Access!");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		return con;
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
