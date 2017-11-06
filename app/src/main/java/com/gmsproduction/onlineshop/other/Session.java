package com.gmsproduction.onlineshop.other;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import com.gmsproduction.onlineshop.activity.LoginActivity;
import com.gmsproduction.onlineshop.adapter.ColorsArray;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by hendiware on 2016/12 .
 */

public class Session {
    // define single instance
    private static Session instance;
    // define realm
    private Realm realm;

    // Session constructor
    private Session() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfig);
    }

    // get singletone from session
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // get new Instance (new Object) from this class
    public static Session newInstance() {
        return new Session();
    }

    // login user take user and add it to realm
    public void loginUser(final User user) {

        if (realm.where(User.class).findFirst() == null) {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);
                }
            });

        } else {
            logout();
            loginUser(user);
        }


    }

    // logout
    public void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }

    public boolean isUserLoggedIn() {
        return realm.where(User.class).findFirst() != null;
    }

    public User getUser() {
        return realm.where(User.class).findFirst();
    }

    public void logoutAndGoToLogin(Activity activity) {
        logout();
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }


    public void insertCartOrder(final CartOrder order) {



            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(order);
                }
            });

    }

    public void insertCartOrderColor(final OrderColor color) {



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(color);
            }
        });




    }


    //find all objects in the Book.class
    public RealmResults<OrderColor> getOrederColors(String item_id) {

        return realm.where(OrderColor.class).equalTo("item_id",item_id).findAll();
    }





/*
    public void updateOrderCartColors(final RealmList<ColorsArray color, final String items_id)
    {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                OrderColor order = realm.where(OrderColor.class).equalTo("item_id", items_id).findFirst();
                if(order == null) {
                    order = realm.createObject(OrderColor.class, items_id);
                }
                order.setColor_code(color);


            }
        });
        Log.e("TAG","DOWNLOAD UPDATE COMPLETED");
    }
*/


    public void deleteOrederColors(final String color_code,final String item_id)
    {

        final ArrayList<CartOrder> list = new ArrayList(getOreders());


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<OrderColor> rows = realm.where(OrderColor.class).equalTo("color_code",color_code)
                        .equalTo("item_id",item_id).findAll();
                rows.deleteLastFromRealm();
            }
        });
    }


    public void deleteOrederItemsColors(final String item_id)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                RealmResults<OrderColor> rows = realm.where(OrderColor.class).equalTo("item_id",item_id).findAll();
                rows.deleteLastFromRealm();
            }
        });
    }







    //find all objects in the Book.class
    public RealmResults<CartOrder> getOreders() {

        return realm.where(CartOrder.class).findAll();
    }


    //find all objects in the Book.class
    public CartOrder getOreder(String item_id) {

        return realm.where(CartOrder.class).equalTo("items_id",item_id).findFirst();
    }


    public void deleteOreder(final String items_id)
    {

        final ArrayList<CartOrder> list = new ArrayList(getOreders());


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CartOrder> rows = realm.where(CartOrder.class).equalTo("items_id",items_id).findAll();
                rows.deleteAllFromRealm();



                RealmResults<OrderColor> rowsColor = realm.where(OrderColor.class).equalTo("item_id",items_id).findAll();
                rowsColor.deleteAllFromRealm();
            }
        });
    }



    public boolean isItemExist(final String item_id)
    {
        return realm.where(CartOrder.class).equalTo("items_id",item_id).findFirst() != null;

    }


    public void updateOrderCart(final String quantity,final String desc, final String items_id)
    {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CartOrder order = realm.where(CartOrder.class).equalTo("items_id", items_id).findFirst();
                if(order == null) {
                    order = realm.createObject(CartOrder.class, items_id);
                }
                order.setQuantity(String.valueOf(quantity));
                order.setDescription(desc);


            }
        });
        Log.e("TAG","DOWNLOAD UPDATE COMPLETED");
    }


}
