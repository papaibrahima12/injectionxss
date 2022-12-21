package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilisateurDaoDb {
	
	private Connection connexion = null;
	
	

	public UtilisateurDaoDb() {
	}
	
	//set classpath=C:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-j-8.0.31.jar;.;
	public void onConnexion() {
		try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeeDB", "root", "kuni");
            if (connexion != null)
            	System.out.print("okayyy");
            else
            	System.out.print("not okayyy");
		}
		catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void closeConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnexion() {
		return connexion;
	}

	public void setConnexion(Connection connexion) {
		this.connexion = connexion;
	}
	
	
}
