<%-- 
    Document   : updateCloudError
    Created on : Dec 13, 2012, 12:05:00 PM
    Author     : siliu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>update cloud error</title>
    </head>
    <body>
        <h2>Failed to update cloud!</h2>
        <br>
        <s:url action="queryCloudList" var="aURL" />
        <s:a href="%{aURL}">return to the list of clouds</s:a>
    </body>
</html>
