<!-- Autor Flo, Lorenz, Michael -->

<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	errorPage="errorpage.jsp"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rezeptbuch - ${rezept.name}</title>

<link
	rel="stylesheet"
	href="${pageContext.request.contextPath}/main.css">
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<!-- textarea.js sollte nach dem stylesheet kommen, Da es optische Dinge tut. -->
<script src="${pageContext.request.contextPath}/js/textarea.js"></script>

<!-- viewRecipe.js & editRecipe.js sollte nach textarea.js kommen, da sie die Funktionen aufrufen, die sonst nicht bekannt sind.-->
<c:if test="${not empty user}">
	<script src="${pageContext.request.contextPath}/js/viewRecipe.js"></script>
	<script src="${pageContext.request.contextPath}/js/editRecipe.js"></script>
	<script src="${pageContext.request.contextPath}/js/rating.js"></script>
</c:if>

<base href="${pageContext.request.requestURI}">
</head>

<body>
	<%@ include file="fragments/nav.jspf"%>

	<c:choose>
		<c:when test="${not empty rezept}">
			<main> <%-- Name des Rezeptes --%>
			<div id="Einstellungen">
				<h1>
					<textarea
						form="editForm"
						name="recipeName"
						id="recipeName"
						disabled
						placeholder="Rezepttitel">${rezept.name}</textarea>
				</h1>
				
				<h2 class="authorname">${rezept.creator.firstName} ${rezept.creator.lastName}</h2>
				
				<%-- Je nach Abonnement-Status kann das Rezept gemerkt werden resp. entmerkt werden --%>
				<c:if test="${not empty user}">

					<div class="buttonRow">
						<c:choose>
							<c:when test="${aboStatus eq false }">
								<input
									type="hidden"
									id="aboAction"
									name="aboAction"
									value="addAbo">
								<button
									id="aboButton"
									name="aboButton"
									class="button-secondary"
									type="button">Merken</button>
							</c:when>
							<c:otherwise>
								<input
									type="hidden"
									id="aboAction"
									name="aboAction"
									value="deleteAbo">
								<button
									id="aboButton"
									name="aboButton"
									class="button-secondary"
									type="button">Nicht länger merken</button>
							</c:otherwise>
						</c:choose>

						<c:if test="${not empty user and user.id eq rezept.creator.id}">
							<button
								id="editRecipeButton"
								name="editRecipeButton"
								class="button"
								type="button">Rezept bearbeiten</button>
						</c:if>
					</div>
				</c:if>
				<%-- Falls der Aufrufer der eingeloggte Ersteller des Rezeptes ist, kann er dieses bearbeiten--%>
				<c:if test="${not empty user and user.id eq rezept.creator.id}">
					<!-- 					<button -->
					<!-- 						id="editRecipeButton" -->
					<!-- 						name="editRecipeButton" -->
					<!-- 						class="button" -->
					<!-- 						type="button">Rezept bearbeiten</button> -->

					<div
						id="editButtons"
						class="hidden-block buttonRow">
						<button
							form="editForm"
							name="save"
							class="button"
							type="submit">Speichern</button>
						<button
							form="editForm"
							type="button"
							class="button"
							id="refreshButton"
							name="refresh"
							value="Verwerfen">Verwerfen</button>
						<button
							form="editForm"
							type="button"
							class="button-secondary"
							id="deleteButton"
							name="deleteButton">Rezept löschen</button>
					</div>
				</c:if>


				<%-- Falls eine Message da ist, wird diese angezeigt (Noch nicht implementiert --%>
				<c:if test="${not empty message}">
					<p>${message}</p>

				</c:if>
			</div>

			<div id="Details">

				<%-- Bild vom Rezept --%>
				<c:if test="${not empty rezept.filename}">
					<img
						src="../LoadImage?id=${rezept.id}&table=recipes"
						width="250"
						height="200"
						class="rec-img"
						alt="Ein Foto vom Rezept ${rezept.name}">
				</c:if>

				<!-- Form für das Aendern des Rezeptes -->
				<form
					id="editForm"
					action="/rezeptbuch/EditRecipeServlet"
					method="post">

					<input
						type="hidden"
						name="id"
						id="id"
						value="${rezept.id}">
					<input
						type="hidden"
						name="creatorID"
						id="creatorID"
						value="${rezept.creator.id}">
					<label class="labelfortextarea">Zutaten</label>
					<table id="recipeIngredients">
						<tr>
							<th>Menge
							<th>Einheit
							<th>Zutat
						</tr>
						<c:forEach
							var="ingredient"
							items="${rezept.ingredients}">
							<tr>
								<td>${ingredient.quantity}
								<td>${ingredient.unit}
								<td>${ingredient.ingredient}
							</tr>
						</c:forEach>
					</table>


					<div
						id="ingredientContainer"
						class="hidden-block">
						<c:forEach
							var="ingredient"
							items="${rezept.ingredients}">
							<div class="newLine clearfix">
								<input
									type="number"
									name="zutatenMenge"
									class="zutatenMenge"
									placeholder="Menge"
									required
									value="${ingredient.quantity}">
								<select
									name="zutatenEinheit"
									class="zutatenEinheit"></select>
								<input
									type="text"
									name="zutatenZutat"
									class="zutatenZutat"
									placeholder="Zutat"
									required
									value="${ingredient.ingredient}">
								<!--  ein versteckter Input, um in Javascript die ausgewählte Einheit zu setzen -->
								<input
									type="hidden"
									name="selectedValue"
									class="selectedValue"
									value="${ingredient.unit}">
								<button
									type="button"
									class="button-del deleteIng"
									name="deleteIng"
									value="Zutat entfernen"></button>
							</div>
						</c:forEach>
					</div>

					<button
						type="button"
						class="button hidden-block"
						id="addIngredientButton"
						name="addrow"
						value="Zutat hinzufügen">Zutat hinzufügen</button>


					<!-- Felder des Rezeptes, die später geändert werden sollen -->
					<div id="editRecipe">

						<label for="description">Beschreibung</label>
						<textarea
							disabled
							name="description"
							id="description"
							class="textarea-transitional"
							required
							maxlength="2500">${rezept.description}
					</textarea>
						<label for="durationPreparation">Vorbereitungszeit</label>
						<input
							disabled
							type="number"
							name="durationPreparation"
							id="durationPreparation"
							min="0"
							max="500"
							value="${rezept.durationPreparation}">
						<label for="durationCooking">Kochzeit</label>
						<input
							disabled
							type="number"
							name="durationCooking"
							id="durationCooking"
							min="0"
							max="500"
							value="${rezept.durationCooking}">
						<label for="difficulty">Schwierigkeitsgrad</label>
						<input
							disabled
							type="number"
							name="difficulty"
							id="difficulty"
							max="5"
							min="0"
							value="${rezept.difficulty}">
						<label for="servings">Portionen</label>
						<input
							disabled
							type="number"
							name="servings"
							id="servings"
							max="99"
							min="0"
							value="${rezept.servings}">
					</div>
				</form>
			</div>

			<div id="Bewertungen">
				<h2>Bewertung</h2>
				<div id="current-rating"></div>
				<c:choose>
					<c:when test="${not empty user }">
						<form
							id="rating-form"
							name="rating-form"
							action="/rezeptbuch/ratingservlet"
							method="post"
							autocomplete="off">
							<input
								type="hidden"
								name="recipe"
								value="${rezept.id}">
							<fieldset
								class="rating"
								form="rating-form">
								<input
									class="star-radio"
									type="radio"
									id="star5"
									name="rating"
									value="5">
								<label
									class="star-label"
									for="star5"
									title="rocks!">5 stars</label>
								<input
									class="star-radio"
									type="radio"
									id="star4"
									name="rating"
									value="4">
								<label
									class="star-label"
									for="star4"
									title="pretty good">4 stars</label>
								<input
									class="star-radio"
									type="radio"
									id="star3"
									name="rating"
									value="3">
								<label
									class="star-label"
									for="star3"
									title="meh">3 stars</label>
								<input
									class="star-radio"
									type="radio"
									id="star2"
									name="rating"
									value="2">
								<label
									class="star-label"
									for="star2"
									title="kinda bad">2 stars</label>
								<input
									class="star-radio"
									type="radio"
									id="star1"
									name="rating"
									value="1">
								<label
									class="star-label"
									for="star1"
									title="sucks big time">1 star</label>
							</fieldset>
							<label
								id="rating-label">${rezept.calculateRatingFloat()}</label>
						</form>


						
					</c:when>
					<c:otherwise>
						<p>Melden Sie sich an, um zu bewerten!
					</c:otherwise>
				</c:choose>

			</div>

			<div id="Kommentare">
				<h2>Kommentare</h2>
				<div id="comments">
					<c:forEach
						var="comment"
						items="${rezept.comments}">
						<div class="commentBox">
							<h4>${comment.author.firstName} ${comment.author.lastName}</h4>
							<div class="commentContent">${comment.comment}</div>
							<c:if test="${not empty user}">
								<c:if test="${user.id == comment.author.id}">
									<a
										class="button-del"
										href="/rezeptbuch/DeleteCommentServlet?id=${comment.id}&recipe=${rezept.id}"></a>

								</c:if>
							</c:if>
						</div>
					</c:forEach>
				</div>
				<c:if test="${not empty user}">
					<form id="commentForm">
						<p>
							<label
								class="labelfortextarea"
								for="newComment">Verfassen sie einen neuen Kommentar </label>
							<textarea
								name="newComment"
								id="newComment"
								placeholder="Kommentar verfassen..."
								maxlength="2500"
								required></textarea>
						</p>
						<button
							id="commentButton"
							class="button"
							type="button">Kommentieren</button>
					</form>
				</c:if>
			</div>

			</main>
		</c:when>
		<c:otherwise>
			<main> <c:if test="${not empty message}">
				<h2>${message}</h2>

			</c:if></main>
		</c:otherwise>
	</c:choose>

</body>
</html>