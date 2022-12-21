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

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/update")
public class UpdateUser extends HttpServlet {

	private static final String VUE_UPDATE_UTILISATEUR = "/WEB-INF/modifierUtilisateur.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");

		if (id != null && id.matches("[0-9]+")) {
			Utilisateur utilisateur = UtilisateurDao.get(Integer.parseInt(id));

			if (utilisateur != null) {
				request.setAttribute("utilisateur", utilisateur);
				getServletContext().getRequestDispatcher(VUE_UPDATE_UTILISATEUR).forward(request, response);
				return;
			}
		}

		response.sendRedirect(request.getContextPath());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		UserForm form = new UserForm(request);
		
		if(!form.modifier()) {
			request.setAttribute("utilisateur", form.getUtilisateur());
			request.setAttribute("status", form.isStatus());
			request.setAttribute("statusMessage", form.getStatusMessage());
			request.setAttribute("erreurs", form.getErreurs());
			getServletContext().getRequestDispatcher(VUE_UPDATE_UTILISATEUR).forward(request, response);
		}
		else {
			response.sendRedirect(request.getContextPath());
		}
	}

}
