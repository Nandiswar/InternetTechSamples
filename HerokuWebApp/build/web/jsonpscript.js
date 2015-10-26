/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// File jsonpscript.js
// The cooljsonp function takes a URL and a callback function name.
// It creates a script tag in the html head section.
// When this script is fetched the servlet runs and returns json 
// padded with the name of the function. That is, the server returns
// myCallback({some JSON object})
// The client will call myCallback({some JSON object})
// The script tag is removed from the DOM on completion.

function cooljsonp(url, myCallback) {
    alert("Calling " + url + " with " + myCallback + " for callback");
    var head = document.head;
    var script = document.createElement("script");

    script.setAttribute("src", url + '?' + 'method=' + myCallback);
    head.appendChild(script);
    head.removeChild(script);
}

function callBackHandler(jsonObject) {
    alert("callBack called");
    // display the id property value
    // treating it as a JSON object
    alert(jsonObject.id);
    // covert the jsonObject to a String
    var response = JSON.stringify(jsonObject);
    alert(response);
}

