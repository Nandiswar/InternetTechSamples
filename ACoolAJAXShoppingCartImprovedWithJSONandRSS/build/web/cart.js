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
    // Send form encoded data stating that I want to remove the 
    // specified item to the cart.
    req.send("action=remove&item=" + itemCode);
}

/*
 * 
 * fetch feed of the subscribed channels
 */
function fetchFeed() {
    // Obtain an XMLHttpRequest instance
    var req = newXMLHttpRequest();
    // Set the handler function to receive callback notifications
    // from the request object
    req.onreadystatechange = getReadyStateHandler(req, updateCartFeed);
    // Open an HTTP POST connection to the shopping cart servlet.
    // Third parameter specifies request is asynchronous.
    req.open("POST", "cart.do", true);
    // Specify that the body of the request contains form data
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    // Send form encoded data stating that I want to 
    // fetch the subscribed feeds
    req.send("action=fetchfeed&item=999");
}


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in JSON object
 */
function updateCart(cartJSON) {
    // Get the entire json object from the document
    var cartJsonObj = jsonParse(cartJSON);
    // Read cart generated timestamp value from cartJsonObj
    var generated = cartJsonObj["cart generated"];
    // Check that a more recent cart document hasn't been processed
    // already
    if (generated > lastCartUpdate) {
        lastCartUpdate = generated;
        
        // Clear the HTML list used to display the cart contents
        var contents = document.getElementById("contents");
        contents.innerHTML = "";

        // Fetch items array from cartJsonObj
        var items = cartJsonObj.items;
        // Loop over the items fetched
        for (var I = 0; I < items.length; I++) {

            var item = items[I];
            // Extract the values of name and quantity from item json object
            var name = item.name;
            //var quantity = item.quantity;

            // Create and add a list item HTML element for this cart item
            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(name));
            contents.appendChild(listItem);
        }

    }
    // Update the cart's total using the value from the cartJsonObj
    document.getElementById("total").innerHTML = cartJsonObj.total;
}

function updateCartFeed(feedJSON) {
    var cartJsonObj = jsonParse(feedJSON);
    //alert(cartJsonObj);
    var feed = cartJsonObj['newsfeed'];
    // Update the text area with the fetched feed
    document.getElementById("feedText").innerHTML = cartJsonObj.newsfeed;;
}
