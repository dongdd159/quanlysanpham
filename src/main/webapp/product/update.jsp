<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 6/8/2021
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <table border="1" cellpadding="5">
        <caption>
            <h1>
                Edit Product
            </h1>
        </caption>
        <tr>
            <th>Name:</th>
            <td>
                <input type="text" name="name" size="45" value="<c:out value='${product.name}' />"/>
            </td>
        </tr>
        <tr>
            <th>Quantity:</th>
            <td>
                <input type="text" name="quantity" size="45" value="<c:out value='${product.quantity}' />"/>
            </td>
        </tr>
        <tr>
            <th>Color:</th>
            <td>
                <input type="text" name="color" size="15" value="<c:out value='${product.color}' />"/>
            </td>
        </tr>
        <tr>
            <th>Description:</th>
            <td>
                <input type="text" name="description" size="45" value="<c:out value='${product.description}' />"/>
            </td>
        </tr>
        <tr>
            <th><label for="category">category:</label></th>
            <td>
                <select name="category" id="category">
                    <option value="${product.category.id}">${product.category.name}</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
