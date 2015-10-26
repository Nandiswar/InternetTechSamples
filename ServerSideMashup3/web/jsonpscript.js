/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// The cooljsonp function creates a script tag in the html head section.
// When this script is fetched the servlet runs and
// script tag is removed from the DOM on completion.

function cooljsonp() {
    // calling the servlet hosted in Heroku to count visits
    var url = "http://mysterious-peak-4595.herokuapp.com/JSONServlet";
    //alert("Calling " + url );
    var head = document.head;
    var script = document.createElement("script");
    script.setAttribute("src", url );
    head.appendChild(script);
    head.removeChild(script);
}
