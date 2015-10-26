package developerworks.ajax.servlet;

import developerworks.ajax.store.Cart;
import developerworks.ajax.store.Catalog;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.http.*;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

/**
 *
 * @author nandi_000
 */
public class CartServlet extends HttpServlet {

    /**
     * Updates Cart, and outputs XML representation of contents
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Read headers
//        Enumeration headers = req.getHeaderNames();
//        while (headers.hasMoreElements()) {
//            String header = (String) headers.nextElement();
//            System.out.println(header + ": " + req.getHeader(header));
//        }
//        // Get the cart for this browser session
//        Cart cart = getCartFromSession(req);
//        // Read the action to be performed
//        String action = req.getParameter("action");
//        // Read the item on which action has to be taken
//        String item = req.getParameter("item");
//        // Add or remove items from the cart
//        String cartJson = "";
//        if ((action != null) && (item != null)) {
//            // Add item to the cart
//            if ("add".equals(action)) {
//                cart.addItem(item);
//                cartJson = cart.toJson();
//            } // Remove item from the cart
//            else if ("remove".equals(action)) {
//                cart.removeItems(item);
//                cartJson = cart.toJson();
//            }// fetch rss feed
//            else if ("fetchfeed".equals(action)) {
//                cartJson = cart.fetchFeed();
//            }
//        }
//        // Serialize the Cart's state to XML
//        //String cartXmlP = cart.toXml(cartXml);
//        // Write XML to response
//        //res.setContentType("text/xml");
//        //res.getWriter().write(cartXml);
//
//        // Serialize the Cart's state to JSON
//        //String cartJson = cart.toJson();
//        // Write JSON to response
//        res.setContentType("application/json");
//        res.getWriter().write(cartJson);
    }

    // synchronized ajax calls are made
    public synchronized void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Bounce to post, for debugging use
        // Hit this servlet directly from the browser to see XML
        String action = req.getParameter("action");

        Catalog catalog = new Catalog();
        String feeditemCode = req.getParameter("item");
        //System.out.println("itemcode " + feeditemCode);
        // dereference item url using its itemcode
        String feedUrl = catalog.fetchURL(feeditemCode);
        //System.out.println("itemurl" + feedUrl);
        //res.setContentType("text/html");
        //System.out.println("inn");
        PrintWriter out = res.getWriter();
        try {
            // get the xsl stored in this project 
            ServletContext context = getServletContext();
            InputStream xsl = (InputStream) (context.getResourceAsStream("/XSLTransformerCode.xsl"));

            // We need two source objects and one result
            // get an external xml document using a url in a 
            // string format
            Source xmlDoc = new StreamSource(feedUrl);
            Source xslDoc = new StreamSource(xsl);
            Result result = new StreamResult(out);

            // Prepare to transform 
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer trans = factory.newTransformer(xslDoc);
            trans.transform(xmlDoc, result);
        } catch (TransformerException e) {
            System.out.println("Transformer Probem" + e);
        }

    }

//    private Cart getCartFromSession(HttpServletRequest req) {
//        //create a session if it doesn't exist
//        HttpSession session = req.getSession(true);
//        Cart cart = (Cart) session.getAttribute("cart");
//        // Check for shopping cart in active session
//        if (cart == null) {
//            // Create a shopping cart for a new session
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//        // Return the shopping cart
//        return cart;
//    }
}
