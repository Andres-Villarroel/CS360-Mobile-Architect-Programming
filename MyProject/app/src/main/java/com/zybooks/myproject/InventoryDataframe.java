package com.zybooks.myproject;

public class InventoryDataframe {

    private String itemName;
    private String itemQuantity;
    //itemId is no longer needed as it would be the responsibility
    // of the database handler to keep track of _id column
    private String itemId;

    public InventoryDataframe(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        //this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String newId){
        this.itemId = newId;
    }

}
