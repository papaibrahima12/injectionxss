package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import beans.Utilisateur;

public class UtilisateurDao {
	
	private static ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();
	
	static {
		Connection connexion = openConnexion();
		if (connexion != null) {
			try {
				Statement stmt = connexion.createStatement();
				String requete = "SELECT * FROM utilisateurs";
				ResultSet rs = stmt.executeQuery(requete);
				while (rs.next()) {
					Utilisateur user = new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("login"), rs.getString("password"));
					users.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static Connection openConnexion() {
		Connection connexion = null;
		try {
            Class.forName("com.mysql.jdbc.Driver");
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeeDB", "root", "kuni");
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		return connexion;
	}
	
	public static void closeConnexion(Connection connexion) {
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static boolean ajouter(Utilisateur user)  {
		Connection connexion = openConnexion();
		if (connexion != null) {
			String requete = "INSERT INTO utilisateurs (nom, prenom, login, password) VALUES (?, ?, ?,?)";
			try {
				PreparedStatement ps = connexion.prepareStatement(requete);
				StringBuilder s = new StringBuilder();
				try {
					MessageDigest msg = MessageDigest.getInstance("SHA-256");
					byte[] hash = msg.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
					for (byte b : hash) {
						s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ps.setString(1, user.getNom());
				ps.setString(2, user.getPrenom());
				ps.setString(3, user.getLogin());
				ps.setString(4, s.toString());
				if (ps.executeUpdate() != 0) {
					user.setId(getLastId());
					users.add(user);
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnexion(connexion);
			}
		}
		return false;
	}
	
	public static ArrayList<Utilisateur> lister() {
		
		return users;
	}
	
	public static boolean modifier(Utilisateur userToUpdate) {
		
		Connection connexion = openConnexion();
		if (connexion != null) {
			String requete = "UPDATE utilisateurs SET nom = ?, prenom = ?, login = ?, password = ?  WHERE (id = ?)";
			try {
				PreparedStatement ps = connexion.prepareStatement(requete);
				ps.setString(1, userToUpdate.getNom());
				ps.setString(2, userToUpdate.getPrenom());
				ps.setString(3, userToUpdate.getLogin());
				ps.setString(4, userToUpdate.getPassword());
				ps.setInt(5, userToUpdate.getId());
				if (ps.executeUpdate() != 0) {
					for (Utilisateur user : users) {
						if (user.getId() == userToUpdate.getId()) {
							user.setLogin(userToUpdate.getLogin());
							user.setNom(userToUpdate.getNom());
							user.setPassword(userToUpdate.getPassword());
							user.setPrenom(userToUpdate.getPrenom());
							ps.close();
							return true;
						}
					}
				}
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnexion(connexion);
			}
		}
		return false;
	}
	
	public static boolean supprimer(int id) {
		
		Connection connexion = openConnexion();
		if (connexion != null) {
			String requete = "DELETE FROM utilisateurs WHERE (id = ?);";
			try {
				PreparedStatement ps = connexion.prepareStatement(requete);
				ps.setInt(1, id);
				if (ps.executeUpdate() != 0) {
					for (Utilisateur user : users) {
						if(user.getId() == id) {
							users.remove(user);
							ps.close();
							return true;
						}
					}
				}
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnexion(connexion);
			}
		}
		return false;
	}
	
	public static int getLastId() {

		Connection connexion = openConnexion();
		if (connexion != null) {
			try {
				Statement stmt = connexion.createStatement();
				String requete = "SELECT * FROM utilisateurs ORDER BY id DESC";
				ResultSet rs = stmt.executeQuery(requete);
				if (rs.next()) {
					return rs.getInt("id");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnexion(connexion);
			}
		}
		return 0;
	}
	
	public static Utilisateur get(int id) {
		for (Utilisateur user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public static Utilisateur get(String login) {
		for (Utilisateur user : users) {
			if(user.getLogin().equals(login)) {
				return user;
			}
		}
		return null;
	}
}
