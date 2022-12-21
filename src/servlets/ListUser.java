package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UtilisateurDao;

/**
 * Servlet implementation class ListUser
 */
@WebServlet({"/list" })
public class ListUser extends HttpServlet
{
	private static final String	VUE_LIST_UTILISATEUR	= "/WEB-INF/listeUtilisateur.jsp";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setAttribute("utilisateurs", UtilisateurDao.lister());
		getServletContext().getRequestDispatcher(VUE_LIST_UTILISATEUR).forward(request, response);
	}

}
