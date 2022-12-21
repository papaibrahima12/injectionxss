<%@ page contentType="text/html; charset=ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Gestion des utilisateurs</title>
	<link rel="stylesheet" href="/firstappcrud/css/design.css">
</head>
<body>
	<div id="entete">Gestion des utilisateurs</div>
	<div id="menu">
		<ul>
			<li><a href="/firstappcrud">Accueil</a></li>
			<li><a href="/firstappcrud/list">Lister</a></li>
			<li><a href="/firstappcrud/ajouter">Ajouter</a></li>
		</ul>
	</div>
	<div id="corps">
		<h1 id="titre-principal">Ajout d'un utilisateur</h1>
		<h2 class="messageBox ${status ? 'succes' : 'erreur' }">${statusMessage }</h2>
		<form method="post">
			<div class="formItem">
				<label>Nom</label>
				<input type="text" name="nom" value="${utilisateur.nom }">
				<span class="erreur">${erreurs.nom}</span>
			</div>
			<div class="formItem">
				<label>Prenom</label>
				<input type="text" name="prenom" value="${utilisateur.prenom }">
				<span class="erreur">${erreurs.prenom}</span>
			</div>
			<div class="formItem">
				<label>Login</label>
				<input type="text" name="login" value="${utilisateur.login }">
				<span class="erreur">${erreurs.login}</span>
			</div>
			<div class="formItem">
				<label>Password</label>
				<input type="password" name="password">
				<span class="erreur">${erreurs.password}</span>
			</div>
			<div class="formItem">
				<label>Confirmation Password</label>
				<input type="password" name="passwordBis">
				<span class="erreur">${erreurs.passwordBis}</span>
			</div>
			
			<div class="formItem">
				<label></label>
				<input type="submit" value="Ajouter">
			</div>
		</form>
	</div>
	<div id="pied">Kuni77 &copy; Novembre 2022</div>
</body>
</html>