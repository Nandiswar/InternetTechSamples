package developerworks.ajax.store;

import java.util.*;

/**
 * Catalog elements
 *
 * @author nandi_000
 */
public class Catalog {

    private static Map<String, Item> items;

    // Static block executed to describe the cart items
    static {
        items = new HashMap<String, Item>();
        items.put("nytamerica001", new Item("nytamerica001", "America News", "NYT America News", "http://rss.nytimes.com/services/xml/rss/nyt/Americas.xml"));
        items.put("nytusa001", new Item("nytusa001", "USA News", "NYT USA news", "http://rss.nytimes.com/services/xml/rss/nyt/US.xml"));
        items.put("newswn001", new Item("newswn001", "New South Wales News", "Sydney Morning Herald New South Wales News", "http://www.smh.com.au/rssheadlines/nsw/article/rss.xml"));
        items.put("itpro001", new Item("itpro001", "IT Pro News", "Sydney Morning Herald IT Pro News", "http://feeds.smh.com.au/rssheadlines/itpro.xml"));
        items.put("latinamerica001", new Item("latinamerica001", "Latin America", "BBC Latin American News", "http://feeds.bbci.co.uk/news/world/latin_america/rss.xml"));
        items.put("adelaide001", new Item("adelaide001", "Adelaide News", "Adelaide News from Adelaide Now", "http://feeds.news.com.au/public/rss/2.0/anow_state_191.xml"));
        items.put("england001", new Item("england001", "England News", "England BBC News", "http://feeds.bbci.co.uk/news/england/rss.xml"));
        items.put("africa001", new Item("africa001", "Africa News", "Africa BBC News", "http://feeds.bbci.co.uk/news/world/africa/rss.xml"));
        items.put("northireland001", new Item("northireland001", "North Ireland News", "North Ireland BBC News", "http://feeds.bbci.co.uk/news/northern_ireland/rss.xml"));
        items.put("scotland001", new Item("scotland001", "Scotland News", "Scotland BBC News", "http://feeds.bbci.co.uk/news/scotland/rss.xml"));
    }

    // Return all the items in Catalog
    public Collection<Item> getAllItems() {
        return items.values();
    }

    /**
     * @param itemCode Code of the item to be checked
     * @return boolean value based on the item existence check
     */
    public boolean containsItem(String itemCode) {
        return items.containsKey(itemCode);
    }

    /**
     * @param itemCode Code of the item to be checked
     * @return the item based on @param
     */
    public Item getItem(String itemCode) {
        return items.get(itemCode);
    }
    
    /*
    *@param itemcode
    *@returns feed url associated with itemcode
    */
    public String fetchURL(String itemCode) {
        // Check for item based on item code
        if (containsItem(itemCode)) {
            Item item = getItem(itemCode);
            return item.getUrl();
        }
        return null;
    }
}
