package com.gmsproduction.onlineshop.other;

/**
 * Created by ahmed on 8/22/2017.
 */

public class MenuItems {
    private final String itemId;

    private final String itemImg;
    private final String itemName;
    private final String itemCode;
    private final String itemPrice;
    private final String itemQuantity;
    private final String itemDescription;


    public MenuItems(String itemName,String itemImg,String itemCode,String itemId,String itemPrice,String itemQuantity,String itemDescription)
    {
        this.itemCode=itemCode;
        this.itemImg=itemImg;
        this.itemName=itemName;
        this.itemId=itemId;

        this.itemDescription=itemDescription;
        this.itemPrice=itemPrice;
        this.itemQuantity=itemQuantity;
    }

    public String getItemImg() {
        return itemImg;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemDescription() {
        return itemDescription;
    }
}
