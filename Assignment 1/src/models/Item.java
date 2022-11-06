package models;

import utils.Utilities;
import java.util.Objects;

public class Item {

    private String itemDescription = "No Description";
    private boolean isItemCompleted = false;

    public Item(String itemDescription, boolean isItemCompleted) {
        this.itemDescription = Utilities.truncateString(itemDescription, 50);
        this.isItemCompleted = isItemCompleted;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public boolean isItemCompleted() {
        return isItemCompleted;
    }


    public void setItemDescription(String itemDescription) {
        if (Utilities.validateStringLength(itemDescription, 50)) {
            this.itemDescription = itemDescription;
        }
    }

    public void setItemCompleted(boolean itemCompleted) {
        this.isItemCompleted = itemCompleted;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemDescription='" + itemDescription + '\'' +
                ", isItemCompleted=" + isItemCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return isItemCompleted() == item.isItemCompleted() && Objects.equals(getItemDescription(), item.getItemDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemDescription(), isItemCompleted());
    }
}





