
package developerworks.ajax.store;

import java.math.BigDecimal;
/**
 * Shopping cart item class with its details
 * @author nandi_000
 */
public class Item {
  private String code;
  private String name;
  private String description;
  private String url;

  // Constructor to initialize the item objects
  public Item(String code,String name,String description,String url) {
    this.code=code;
    this.name=name;
    this.description=description;
    this.url=url;
  }

  // Getter method to return item code
  public String getCode() {
    return code;
  }

  // Getter method to return item name
  public String getName() {
    return name;
  }

  // Getter method to return item description
  public String getDescription() {
    return description;
  }

  // Getter method to return item price
  public String getUrl() {
    return url;
  }

  // Getter method to return item price in $P.XX format
  /*public String getFormattedPrice() {
    return "$"+new BigDecimal(price).movePointLeft(2);
  }*/

  /**
   * 
   * @param o
   * @return boolean value comparing item object with @param
   */
  public boolean equals(Object o) {
    if (this == o) return true;
    if (this == null) return false;
    if (!(o instanceof Item)) return false;
    return ((Item)o).getCode().equals(this.code);
  }
}

