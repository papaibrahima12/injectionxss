package form;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.UtilisateurDao;

public class UserForm {
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_LOGIN  = "login";
	private static final String CHAMP_PASSWORD = "password";
	private static final String CHAMP_PASSWORD_BIS = "passwordBis";
	
	private static final String EMPTY_FIELD_ERROR_MESSAGE = "Vous devez renseigner le champs";
	private static final String WRONG_PASSWORD_MESSAGE = "Les mots de passe ne sont pas conformes";
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> erreurs;
	private boolean status;
	private String statusMessage;
	private Utilisateur utilisateur;
	
	public UserForm(HttpServletRequest request) {
		this.request = request;
		this.erreurs = new HashMap<String, String>();
	}
	
	public boolean ajouter() {
		String nom = getPrarameter(CHAMP_NOM);
		String prenom = getPrarameter(CHAMP_PRENOM);
		String login = getPrarameter(CHAMP_LOGIN);
		String password = getPrarameter(CHAMP_PASSWORD);
	
		this.utilisateur = new Utilisateur(nom, prenom, login, password);
		
		validerLogin(login);
		validerChamps(CHAMP_NOM, CHAMP_PRENOM, CHAMP_LOGIN, CHAMP_PASSWORD, CHAMP_PASSWORD_BIS);
		validerPasswords();
		
		if (erreurs.isEmpty()) {
			status = true;
			statusMessage = "Ajout effectué avec succés";
			UtilisateurDao.ajouter(utilisateur);
		}
		else {
			status = false;
			statusMessage = "Echec de l'ajout";
		}
		
		return status;
	}
	
	public boolean connexion() {
		String login = getPrarameter(CHAMP_LOGIN);
		String password = getPrarameter(CHAMP_PASSWORD);
		Utilisateur user = UtilisateurDao.get(login);
		statusMessage = "login ou mot de passe incorrect";
		
		validerChamps(CHAMP_LOGIN, CHAMP_PASSWORD);
		
		if (user != null && user.getPassword().equals(password)) {
			if (erreurs.isEmpty()) {
				status = true;
			}
			else {
				status = false;
			}
		}
		else {
			status = false;
		}
		return status;
	}
	
	public boolean modifier() {
		String id = request.getParameter("id");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (id != null && id.matches("[0-9]+") && UtilisateurDao.get(Integer.parseInt(id)) != null)
		{
			utilisateur = new Utilisateur(Integer.parseInt(id), nom, prenom, login, password);
			
			validerChamps(CHAMP_NOM, CHAMP_PRENOM, CHAMP_LOGIN, CHAMP_PASSWORD, CHAMP_PASSWORD_BIS);
			validerPasswords();
			
			if (erreurs.isEmpty()) {
				status = true;
				statusMessage = "Modification effectuée avec succés";
				UtilisateurDao.modifier(utilisateur);
			}
			else {
				status = false;
				statusMessage = "Echec de la modification";
			}
		}
		else {
			status = false;
			statusMessage = "Cet id n'existe pas.";
		}
		return status;
	}
	
	private String getPrarameter(String parametre) {
		String valeur = this.request.getParameter(parametre);
		return ((valeur == null || valeur.trim().isEmpty() ) ? null : valeur.trim());
	}
	
	private void validerChamps(String... parameters) {
		for(String parametre:parameters) {
			if (this.getPrarameter(parametre) == null) {
				erreurs.put(parametre, EMPTY_FIELD_ERROR_MESSAGE);
			}
		}
	}
	
	private void validerPasswords() {
		String password = this.getPrarameter(CHAMP_PASSWORD);
		String passwordBis = this.getPrarameter(CHAMP_PASSWORD_BIS);
		
		if (password != null && !password.equals(passwordBis)) {
			erreurs.put(CHAMP_PASSWORD, WRONG_PASSWORD_MESSAGE);
			erreurs.put(CHAMP_PASSWORD_BIS, WRONG_PASSWORD_MESSAGE);
		}
	}
	
	private void validerLogin(String login) {
		if (UtilisateurDao.get(login) != null) {
			erreurs.put(CHAMP_LOGIN, "Ce login existe deja");
		}
	}
	
	public Map<String, String> getErreurs() {
		return erreurs;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
