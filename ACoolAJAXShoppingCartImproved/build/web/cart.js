// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function addToCart(itemCode) {
    // Obtain an XMLHttpRequest instance
    var req = newXMLHttpRequest();
    // Set the handler function to receive callback notifications
    // from the request object
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    // Open an HTTP POST connection to the shopping cart servlet.
    // Third parameter specifies request is asynchronous.
    req.open("POST", "cart.do", true);
    // Specify that the body of the request contains form data
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // Send form encoded data stating that I want to add the 
    // specified item to the cart.
    req.send("action=add&item=" + itemCode);
}

/*
 * Removes the specified item from the shopping cart, via Ajax call
 * itemCode - product code of the item to be removed
 */
function removeFromCart(itemCode) {
    // Obtain an XMLHttpRequest instance
    var req = newXMLHttpRequest();
    // Set the handler function to receive callback notifications
    // from the request object
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    // Open an HTTP POST connection to the shopping cart servlet.
    // Third parameter specifies request is asynchronous.
    req.open("POST", "cart.do", true);
    // Specify that the body of the request contains form data
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // Send form encoded data stating that I want to add the 
    // specified item to the cart.
    req.send("action=remove&item=" + itemCode);
}


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in XML document.
 */
function updateCart(cartXML) {
    // Get the root "cart" element from the document
    var cart = cartXML.getElementsByTagName("cart")[0];
    // Check that a more recent cart document hasn't been processed
    // already
    var generated = cart.getAttribute("generated");
    if (generated > lastCartUpdate) {
        lastCartUpdate = generated;
        
        // Clear the HTML list used to display the cart contents
        var contents = document.getElementById("contents");
        contents.innerHTML = "";

        // Loop over the items in the cart
        var items = cart.getElementsByTagName("item");
        for (var I = 0; I < items.length; I++) {

            var item = items[I];
            // Extract the text nodes from the name and quantity elements
            var name = item.getElementsByTagName("name")[0].firstChild.nodeValue;
            var quantity = item.getElementsByTagName("quantity")[0].firstChild.nodeValue;

            // Create and add a list item HTML element for this cart item
            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(name + " x " + quantity));
            contents.appendChild(listItem);
        }

    }
    // Update the cart's total using the value from the cart document
    document.getElementById("total").innerHTML = cart.getAttribute("total");
}
