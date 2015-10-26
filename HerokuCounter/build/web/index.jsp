<%-- 
    Document   : index
    Created on : 6 Oct, 2015, 5:14:59 PM
    Author     : nandi_000
--%>

<%@page import="com.visit.bean.VisitorCount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% VisitorCount visitObj = new VisitorCount(); %>
        <!-- fetching the visitor count and displaying at front-end -->
        Total visitors: <%= visitObj.getVisitors()%>                
    </body>
</html>
