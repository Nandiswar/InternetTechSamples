package developerworks.ajax.servlet;

import developerworks.ajax.store.Cart;
import javax.servlet.http.*;

import java.util.Enumeration;
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
        Enumeration headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            System.out.println(header + ": " + req.getHeader(header));
        }
        // Get the cart for this browser session
        Cart cart = getCartFromSession(req);
        // Read the action to be performed
        String action = req.getParameter("action");
        // Read the item on which action has to be taken
        String item = req.getParameter("item");
        // Add or remove items from the cart
        String cartJson = "";
        if ((action != null) && (item != null)) {
            // Add item to the cart
            if ("add".equals(action)) {
                cart.addItem(item);
                cartJson = cart.toJson();
            } // Remove item from the cart
            else if ("remove".equals(action)) {
                cart.removeItems(item);
                cartJson = cart.toJson();
            }// fetch rss feed
            else if ("fetchfeed".equals(action)) {
                cartJson = cart.fetchFeed();
            }
        }
        // Serialize the Cart's state to XML
        //String cartXmlP = cart.toXml(cartXml);
        // Write XML to response
        //res.setContentType("text/xml");
        //res.getWriter().write(cartXml);

        // Serialize the Cart's state to JSON
        //String cartJson = cart.toJson();
        // Write JSON to response
        res.setContentType("application/json");
        res.getWriter().write(cartJson);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Bounce to post, for debugging use
        // Hit this servlet directly from the browser to see XML
        doPost(req, res);
    }

    private Cart getCartFromSession(HttpServletRequest req) {
        //create a session if it doesn't exist
        HttpSession session = req.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        // Check for shopping cart in active session
        if (cart == null) {
            // Create a shopping cart for a new session
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        // Return the shopping cart
        return cart;
    }
}
