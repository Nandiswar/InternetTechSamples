package developerworks.ajax.store;

import java.math.BigDecimal;
import java.util.*;

/**
 * A very simple shopping Cart
 */
public class Cart {

    private HashMap<Item, Integer> contents;

    /**
     * Creates a new Cart instance
     */
    public Cart() {
        contents = new HashMap<Item, Integer>();
    }

    /**
     * Adds a named item to the cart
     *
     * @param itemName The name of the item to add to the cart
     */
    public void addItem(String itemCode) {

        Catalog catalog = new Catalog();
        // Check for item in catalog based on item code
        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int newQuantity = 1;
            // Check if item is already in the cart
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                // Increment the quantity by 1
                newQuantity += currentQuantity.intValue();
            }
            // Add the item and new quantity to the contents map
            contents.put(item, new Integer(newQuantity));
        }
    }

    /**
     * Removes the named item from the cart
     *
     * @param itemName Name of item to remove
     */
    public void removeItems(String itemCode) {

        contents.remove(new Catalog().getItem(itemCode));
    }

    /**
     * @return XML representation of cart contents
     */
    public String toXml() {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\"?>\n");
        // Append the current system time to check for latest async calls
        xml.append("<cart generated=\"" + System.currentTimeMillis() + "\" total=\"" + getCartTotal() + "\">\n");
        // Generate xml tags and values by iterating through the contents
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();

            xml.append("<item code=\"" + item.getCode() + "\">\n");
            xml.append("<name>");
            xml.append(item.getName());
            xml.append("</name>\n");
            xml.append("<quantity>");
            xml.append(itemQuantity);
            xml.append("</quantity>\n");
            xml.append("</item>\n");
        }

        xml.append("</cart>\n");
        return xml.toString();
    }

    // Get the total price of cart items
    private String getCartTotal() {
        int total = 0;
        // Loop through the content items
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();
            // increment the total price based on item price and its quantity
            total += (item.getPrice() * itemQuantity);
        }
        // Return the total price in $M.XX format
        return "$" + new BigDecimal(total).movePointLeft(2);
    }
}
