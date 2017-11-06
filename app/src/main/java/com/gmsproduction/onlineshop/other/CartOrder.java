package com.gmsproduction.onlineshop.other;

import io.realm.RealmObject;

/**
 * Created by ahmed on 8/29/2017.
 */

public class CartOrder extends RealmObject {

    private String user_id;
    private String items_id;
    private String quantity;
    private String img;
    private String price;
    private String name;
    private String description;
    private String descriptionDb;
    private String code;







    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItems_id() {
        return items_id;
    }

    public void setItems_id(String items_id) {
        this.items_id = items_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionDb() {
        return descriptionDb;
    }

    public void setDescriptionDb(String descriptionDb) {
        this.descriptionDb = descriptionDb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
