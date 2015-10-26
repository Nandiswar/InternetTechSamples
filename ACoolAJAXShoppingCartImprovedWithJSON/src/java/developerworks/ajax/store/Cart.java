package developerworks.ajax.store;

import java.math.BigDecimal;
import java.util.*;

/**
 * A very simple shopping Cart
 * @author nandi_000
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
        Catalog catalog = new Catalog();
        // Check for item in catalog based on item code
        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int decreaseQuantity = 1;
            // Check if item is already in the cart
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                // Decrease the quantity by 1
                currentQuantity -= decreaseQuantity;
                // Check if item quantity is 0
                if (currentQuantity == 0) {
                    // Remove the item as quantity is 0
                    contents.remove(new Catalog().getItem(itemCode));
                } else {
                    // Add the item and new quantity to the contents map
                    contents.put(item, currentQuantity);
                }
            }
        }

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

    /**
     *
     * @return JSON representation of cart contents
     */
    public String toJson() {
        StringBuffer json = new StringBuffer();
        // Append the current system time to check for latest async calls
        json.append("{\"cart generated\":" + System.currentTimeMillis() + ",");
        json.append("\"total\":\"" + getCartTotal() + "\",");
        // Generate json array of json objects by iterating through the contents
        json.append("\"items\":[");
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();
            json.append("{");
            json.append("\"item code\":\"" + item.getCode() + "\",");
            json.append("\"name\":\"");
            json.append(item.getName()).append("\",");
            json.append("\"quantity\":");
            json.append(itemQuantity).append("},");
        }
        // Remove , if it exists at the end of json for correcting the syntax
        if(json.toString().endsWith(",")){
            json.setLength(json.length()-1);
        }
        json.append("]}");
        return json.toString();
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
