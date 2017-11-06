package com.gmsproduction.onlineshop.other;

import com.gmsproduction.onlineshop.adapter.ColorsArray;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by ahmed on 8/30/2017.
 */

public class OrderColor extends RealmObject{



    private String item_id;
    private String color_code;
    //private RealmList<ColorsArray> color_code; // legal

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

 /*   public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }*/

/*    public void setColor_code(RealmList<ColorsArray> color_code) {
        this.color_code = color_code;
    }

    public RealmList<ColorsArray> getColor_code() {
        return color_code;
    }*/
}
