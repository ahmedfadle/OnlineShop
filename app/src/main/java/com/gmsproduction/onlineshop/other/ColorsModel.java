package com.gmsproduction.onlineshop.other;

/**
 * Created by ahmed on 8/29/2017.
 */

public class ColorsModel {


    private String text;
    private boolean isSelected = false;

    public ColorsModel() {

    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setText(String text) {
        this.text = text;
    }
}
