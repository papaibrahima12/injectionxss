package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utilisateur;
import dao.UtilisateurDao;
import form.UserForm;

@WebServlet("/ajouter")
public class AddUser extends HttpServlet {
	
	private static final String VUE_AJOUT_UTILISATEUR = "/WEB-INF/ajoutUtilisateur.jsp";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getServletContext().getRequestDispatcher(VUE_AJOUT_UTILISATEUR).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserForm form = new UserForm(request);
		if(!form.ajouter()) {
			request.setAttribute("utilisateur", form.getUtilisateur());
			request.setAttribute("status", form.isStatus());
			request.setAttribute("statusMessage", form.getStatusMessage());
			request.setAttribute("erreurs", form.getErreurs());
			getServletContext().getRequestDispatcher(VUE_AJOUT_UTILISATEUR).forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath());
		}
		
	}
}
