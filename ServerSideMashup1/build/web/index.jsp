<%-- 
    Document   : index.jsp
    Created on : 10 Sep, 2015, 2:28:44 PM
    Author     : nandi_000
--%>
<%@ page import="java.util.*" %>
<%@ page import="developerworks.ajax.store.*" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" language="javascript" src="ajax1.js"></script>
        <script type="text/javascript" language="javascript" src="cart.js"></script>
        <!-- Importing JSON library java script -->
        <script type="text/javascript" language="javascript" src="json-minified.js"></script>
        <link rel="stylesheet" type="text/css" href="newscss.css"/>
    </head>
    <!-- Make a request to server to get the cart details upon page reload -->
    <body onload="addToCart(null)">
        <div style="float: left; width: 500px">
            <h2>Catalog</h2>
            <table border="1">
                <!-- Table of products from store's catalog, one row per item -->
                <thead><th>Name</th><th>Source</th><th>Add</th><th>Delete</th></thead>
                <tbody>
                    <!-- Looping through catalog item elements -->
                    <%
                        for (Iterator<Item> I = new Catalog().getAllItems().iterator(); I.hasNext();) {
                            Item item = I.next();
                    %>
                    <!-- Populating item details-->
                    <tr><td><%= item.getName()%></td><td><%= item.getDescription()%></td><td>
                            <!-- Click button to add item to cart via Ajax request-->
                            <button onclick="addToCart('<%= item.getCode()%>')">Add</button></td><td>
                            <!-- Click button to remove item from cart via Ajax request -->
                            <button onclick="removeFromCart('<%= item.getCode()%>')">Remove</button></td></tr>
                            <% }%>
                </tbody>
            </table>
            <br></br>
            <!-- Click button to fetch the feed via Ajax request -->
            <button onclick="getDocsFromCart()">Generate newspaper</button>
            <!-- The news feed will be added in this div -->
            <div id="feedText"></div>


            <!-- Positioning shopping cart to right side of the browser -->    
            <div style="position: absolute; top: 0px; right: 0px; width: 250px">
                <!-- Representation of shopping cart, updated asynchronously -->
                <h2>Cart Contents</h2>
                <ul id="contents">
                    <!-- List-items will be added here for each item in the cart -->
                </ul>
                <!-- Total number of feeds selected -->
                Number of feeds selected: <span id="total">0</span>
            </div>
    </body>
</html>
