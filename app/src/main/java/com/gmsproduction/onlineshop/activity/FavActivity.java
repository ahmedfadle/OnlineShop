package com.gmsproduction.onlineshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.adapter.ItemsRecyAdapter;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.MenuItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Object> itemsListRefresh=new ArrayList<Object>();

    LinearLayout ll_no_item;

    SharedPreferences sharedPreferences;



    int listSize=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);


        getSupportActionBar().setTitle("Favourite");

        init();
        setListener();
        listItems();


    }






    public void init()
    {
        ll_no_item= (LinearLayout) findViewById(R.id.ll_no_item);

        //ivItemMainImg= (ImageView) findViewById(R.id.ivMainImg);
        mRecyclerView= (RecyclerView) findViewById(R.id.rvMain);

    }
    public void setListener()
    {
   /*     mRecyclerView.addOnItemTouchListener(new RecyclerTouchListenerFav(FavActivity.this,
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

 *//*               MenuItems menuItems = (MenuItems) itemsList.get(position);

                Intent intent=new Intent(FavActivity.this,DetailsActivity.class);
                intent.putExtra("name",menuItems.getItemName());
                intent.putExtra("img",menuItems.getItemImg());
                intent.putExtra("code",menuItems.getItemCode());
                intent.putExtra("id",menuItems.getItemId());
                intent.putExtra("price",menuItems.getItemPrice());
                intent.putExtra("quantity",menuItems.getItemQuantity());
                intent.putExtra("description",menuItems.getItemDescription());

                startActivity(intent);*//*
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/



    }


    public void listItems()
    {

        final ArrayList<Object> itemsList=new ArrayList<Object>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ITEMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("gallary",response);

                        try {
                            JSONArray read=new JSONArray(response);
                            for (int i=0;i<read.length();i++)
                            {
                                //Log.d("gallary",response);
                                JSONObject tracks=read.getJSONObject(i);






                                String itemId = tracks.getString("id");

                                String itemName = tracks.getString("name");
                                String itemImg = tracks.getString("img");
                                String itemCode = tracks.getString("code");
                                String itemprice = tracks.getString("price");

                                String itemQuantity = tracks.getString("quantity");
                                String itemDescription = tracks.getString("description");


                                sharedPreferences=getSharedPreferences("likes", Context.MODE_PRIVATE);
                                String favId=sharedPreferences.getString("id"+itemId,"1");



                                if (favId.equals("liked")) {


                                    ll_no_item.setVisibility(View.GONE);

                                    MenuItems menuItems = new MenuItems(itemName, itemImg, itemCode, itemId, itemprice, itemQuantity, itemDescription);
                                    itemsList.add(menuItems);
                                    //  Toast.makeText(HomeActivity.this, ""+itemName, Toast.LENGTH_SHORT).show();

                                }

                            }




                            mLayoutManager = new GridLayoutManager(FavActivity.this,2);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter = new ItemsRecyAdapter(itemsList,FavActivity.this);
                            mRecyclerView.setAdapter(mAdapter);

                            mRecyclerView.hasFixedSize();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    @Override
    protected void onResume() {
        super.onResume();
        listItems();
    }




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


}

/*

class RecyclerTouchListenerFav implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private FavActivity.ClickListener clickListener;

    public RecyclerTouchListenerFav(Context context, final RecyclerView recyclerView, final FavActivity.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}*/
