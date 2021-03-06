// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;
var selectedFeeds;
var OverallFeed;
/*
 * Adds the specified item to the shopping cart
 */
function addToCart(itemCode, itemName, feedUrl) {
    // check for an itemcode and add it to local storage
    if (itemCode !== null && itemCode !== undefined) {
        localStorage.setItem(itemCode, JSON.stringify({itemcode: itemCode, itemname: itemName, feedUrl: feedUrl}));
    }
    // update the cart section shown to user
    updateCartDisplay();
}

/*
 * Removes the specified item from the shopping cart
 */
function removeFromCart(itemCode) {
    // remove the item from the cart
    localStorage.removeItem(itemCode);
    // update the cart section shown to user
    updateCartDisplay();
}

/*
 * 
 * update the cart shown to user
 */
function updateCartDisplay() {
    // Clear the HTML list used to display the cart contents
    var contents = document.getElementById("contents");
    contents.innerHTML = "";
    selectedFeeds = new Array();

    var keys = Object.keys(localStorage), i = keys.length;

    while (i--) {
        // retrieve an object saved as a string
        var selectedCodesString = localStorage.getItem(keys[i]);
        selectedCodes = JSON.parse(selectedCodesString);
        // Extract the values of name
        var name = selectedCodes.itemname;
        // keeping track of user preferred news items
        selectedFeeds.push(selectedCodes.itemcode);
        // Create and add a list item HTML element for this cart item
        var listItem = document.createElement("li");
        listItem.appendChild(document.createTextNode(name));
        contents.appendChild(listItem);
    }
    // Update the cart's total based on total feeds selected
    document.getElementById("total").innerHTML = keys.length;
}

/*
 * 
 * fetch feed of the subscribed channels
 */
function getDocsFromCart() {
    var arrayLength = selectedFeeds.length;
    OverallFeed = "";
    if (arrayLength == 0) {
        var req = newXMLHttpRequest();
        // Set the handler function to receive callback notifications
        // from the request object
        req.onreadystatechange = getReadyStateHandler(req, updateCartFeed);
        // Open an HTTP GET connection to the shopping cart servlet.
        // Third parameter specifies request is asynchronous.
        //append the parameters to be passed at end of url
        req.open("GET", "cart.do" + "?" + "action=fetchfeed&item=999", true);
        // Specify that the body of the request contains form data
        req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        // Send form encoded data stating that I want to 
        // fetch the subscribed feeds

        //alert("action=fetchfeeds&item="+itemCode);
        req.send();
    } else {
        // Make a ajax request to fetch feed for each item selected 
        for (var i = 0; i < arrayLength; i++) {
            // Obtain an XMLHttpRequest instance
            var req = newXMLHttpRequest();
            // Set the handler function to receive callback notifications
            // from the request object
            req.onreadystatechange = getReadyStateHandler(req, updateCartFeed);
            // Open an HTTP GET connection to the shopping cart servlet.
            // Third parameter specifies request is asynchronous.
            var itemCode = selectedFeeds[i];
            //append the parameters to be passed at end of url
            req.open("GET", "cart.do" + "?" + "action=fetchfeed&item=" + itemCode, true);
            // Specify that the body of the request contains form data
            req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            // Send form encoded data stating that I want to 
            // fetch the subscribed feeds

            //alert("action=fetchfeeds&item="+itemCode);
            req.send();
        }
    }

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
        selectedFeeds = new Array();
        // Loop over the items fetched
        for (var I = 0; I < items.length; I++) {

            var item = items[I];
            // Extract the values of name and quantity from item json object
            var name = item.name;

            selectedFeeds.push(item["item code"]);
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
    //var cartJsonObj = jsonParse(feedJSON);
    //alert(cartJsonObj);
    //var feed = cartJsonObj['newsfeed'];
    // concatenate the individual feeds fetched
    OverallFeed += feedJSON;
    // Update the div element with the fetched feed
    document.getElementById("feedText").innerHTML = OverallFeed;
}
