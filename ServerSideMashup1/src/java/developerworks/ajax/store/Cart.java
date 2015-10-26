package developerworks.ajax.store;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * A very simple shopping Cart
 *
 * @author nandi_000
 */
public class Cart {

    private HashMap<String, Item> contents;

    /**
     * Creates a new Cart instance
     */
    public Cart() {
        contents = new HashMap<String, Item>();
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
            // Add the item code and item to the contents map
            if (!contents.containsKey(itemCode)) {
                contents.put(itemCode, item);
            }
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
            // Check if item is already in the cart
            if (contents.containsKey(itemCode)) {
                // remove the subscribed feed
                contents.remove(itemCode);
            }
        }

    }

    /**
     * fetch the feed from the RSS Url
     */
    public String fetchFeed() {
        StringBuffer feed = new StringBuffer();
        for (Iterator<Item> I = contents.values().iterator(); I.hasNext();) {
            Item item = I.next();
            String itemUrl = item.getUrl();
            try {
                URL feedUrl = new URL(itemUrl);
                Charset encoding = Charset.defaultCharset();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(feedUrl.openStream(), encoding));
                //String inputLine;
                int feedChar;
                /*while ((inputLine = bufferedReader.readLine()) != null) {
                 feed.append(inputLine);
                 }*/
                // read individual characters
                while ((feedChar = bufferedReader.read()) != -1) {
                    char value = (char) feedChar;
                    if (value == '"') {
                        feed.append("\\\"");
                    } else if (value == '\r' || value == '\n') {
                        feed.append("\\n");
                    } else {
                        feed.append(value);
                    }
                    //feed.append(value);
                }
                bufferedReader.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return feedJson(feed.toString());
    }
    /*
    *@param itemcode
    *@returns feed url associated with itemcode
    */
    public String fetchURL(String itemCode) {
         Catalog catalog = new Catalog();
        // Check for item in catalog based on item code
        if (catalog.containsItem(itemCode)) {
            // Check if item is already in the cart
            if (contents.containsKey(itemCode)) {
                // remove the subscribed feed
                Item item = contents.get(itemCode);
                return item.getUrl();
            }
        }
        return null;
    }

    public String feedJson(String feedData) {
        StringBuffer json = new StringBuffer();
        // Append the current system time to check for latest async calls
        json.append("{\"cart generated\":" + System.currentTimeMillis() + ",");
        json.append("\"newsfeed\":\"").append(feedData).append("\"}");
        return json.toString();
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
        for (Iterator<Item> I = contents.values().iterator(); I.hasNext();) {
            Item item = I.next();
            //int itemQuantity = contents.get(item).intValue();

            xml.append("<item code=\"" + item.getCode() + "\">\n");
            xml.append("<name>");
            xml.append(item.getName());
            xml.append("</name>\n");
            //xml.append("<quantity>");
            //xml.append(itemQuantity);
            //xml.append("</quantity>\n");
            xml.append("</item>\n");
        }
        //xml.append("<feed>").append(cartXml).append("</feed>\n");
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
        for (Iterator<Item> I = contents.values().iterator(); I.hasNext();) {
            Item item = I.next();
            //int itemQuantity = contents.get(item).intValue();
            json.append("{");
            json.append("\"item code\":\"" + item.getCode() + "\",");
            json.append("\"name\":\"");
            json.append(item.getName()).append("\"},");
            //json.append("\"quantity\":");
            //json.append(itemQuantity).append("},");
        }
        // Remove , if it exists at the end of json for correcting the syntax
        if (json.toString().endsWith(",")) {
            json.setLength(json.length() - 1);
        }
        json.append("]}");
        return json.toString();
    }

    // Get the count of subscribed feeds
    private String getCartTotal() {
        // Return the total number of subscribed feeds
        return "" + contents.size();
    }
}
