<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/style-geral.css">
    <link rel="stylesheet" href="../../css/style-estab.css">
    <link rel="stylesheet" href="../../css/tags.css">
    <title>Tags</title>
</head>
<body>
    <h2>Gerenciamento de Tags</h2>
   <form action="EditarTagsController" method="post" id="tagForm">
        <ul class="tag-list">
            <c:forEach var="tag" items="${tags}">
                <li>
                    <label>
                        <input type="checkbox" name="tagIds" value="${tag.id}">
                        <span class="tag">${tag.nome}</span>
                    </label>
                </li>
            </c:forEach>
        </ul>
        <button type="submit">Salvar</button>
    </form>

</body>
</html>