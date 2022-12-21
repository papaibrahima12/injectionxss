<%@page import="beans.Utilisateur, java.util.ArrayList"%>
<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%
	final String APP_ROOT = request.getContextPath();
	ArrayList<Utilisateur> utilisateurs = (ArrayList<Utilisateur>) request.getAttribute("utilisateurs"); 
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Gestion des utilisateurs</title>
	<link rel="stylesheet" href="<%= APP_ROOT %>/css/design.css">
	<script src="<%= APP_ROOT %>/js/script.js"></script>
</head>
<body>
	<div id="entete">Gestion des utilisateurs</div>
	<div id="menu">
		<ul>
			<li><a href="<%= APP_ROOT %>">Accueil</a></li>
			<li><a href="<%= APP_ROOT %>/list">Lister</a></li>
			<li><a href="<%= APP_ROOT %>/ajouter">Ajouter</a></li>
		</ul>
	</div>
	<div id="corps">
		<h1 id="titre-principal">Liste des utilisateurs</h1>
		<%
			if(utilisateurs.isEmpty())
			{%>
				<p>La liste des utilisateurs est pour le moment vide !</p><%
			}
			else
			{%>
				<table>
					<tr>
						<th>ID</th>
						<th>Prenom</th>
						<th>Nom</th>
						<th>Login</th>
						<th>Password</th>
						<th colspan="2">Actions</th>
					</tr><%
					for (Utilisateur utilisateur : utilisateurs)
					{
						request.setAttribute("utilisateur", utilisateur);%>
						<tr>
							<td>${utilisateur.id }</td>
							<td>${utilisateur.prenom }</td>
							<td>${utilisateur.nom }</td>
							<td>${utilisateur.login }</td>
							<td>${utilisateur.password }</td>
							<td><a href="<%= APP_ROOT %>/update?id=${utilisateur.id }">Modifier</a></td>
							<td><a href="<%= APP_ROOT %>/delete?id=${utilisateur.id }" onclick="return confirmSuppression()">Supprimer</a></td>
						</tr><%
					}%>
				</table><%
			}
		%>
	</div>
	<div id="pied">Kuni77 &copy; Novembre 2022</div>
</body>
</html>