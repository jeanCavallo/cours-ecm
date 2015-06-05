<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fragments" tagdir="/WEB-INF/tags/fragments" %>

<!doctype html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <title>Cooking miam miam : le site de cuisine à manger</title>

    <link rel="stylesheet" href="/lib/bootstrap/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="/css/style.css" />
</head>
<body>
    <%-- BUG-3 : factorize through tag files --%>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">Cooking Miam Miam</a>
            </div>

            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="/recettes">Toutes les recettes</a></li>
                    <li><a href="/recettes-du-moment">Recette du moment</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-sm-4">
                <div class="thumbnail">
                    <img src="/image/${receipe.imageId}" alt="${fn:escapeXml(receipe.title)}">
                </div>
            </div>
            <div class="col-xs-12 col-sm-8">
                <%-- BUG : edit button when admin --%>
                <h1>${fn:escapeXml(receipe.title)}</h1>
                <p>${fn:escapeXml(receipe.intro)}</p>
                    <%-- BUG : format date --%>
                <p>${receipe.date}</p>
                <c:if test="${not empty receipe.ingredients}">
                    <ul>
                        <c:forEach var="ingredient" items="${receipe.ingredients}">
                            <li>${fn:escapeXml(ingredient.name)} : ${fn:escapeXml(ingredient.quantity)} ${fn:escapeXml(ingredient.unit)}</li>
                        </c:forEach>
                    </ul>
                </c:if>
                <%-- BUG : format text with function --%>
                <p>${fn:escapeXml(receipe.text)}</p>
            </div>
        </div>
    </div>

    <fragments:footer />

    <script src="/lib/jquery/dist/jquery.min.js"></script>
    <script src="/lib/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>