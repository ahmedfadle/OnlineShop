package com.gmsproduction.onlineshop.adapter;

import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by ahmed on 8/30/2017.
 */

public class ColorsArray extends RealmObject {

    private String code;

    public ColorsArray()
    {

    }


    public ColorsArray(String code)
    {
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
