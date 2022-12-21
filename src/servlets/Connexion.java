package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Utilisateur;
import dao.UtilisateurDao;
import form.UserForm;

@WebServlet({"", "/connexion"})
public class Connexion extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		UserForm form = new UserForm(req);
		
		if (form.connexion()) {
			HttpSession session = req.getSession();
			session.setAttribute("isConnected", true);
			resp.sendRedirect("list");
		}
		else {
			req.setAttribute("login", req.getAttribute("login"));
			req.setAttribute("status", form.isStatus());
			req.setAttribute("statusMessage", form.getStatusMessage());
			req.setAttribute("erreurs", form.getErreurs());
			getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
		}
		
	}

}
