package com.gmsproduction.onlineshop.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

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
import com.gmsproduction.onlineshop.other.Session;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    ImageView ivItemMainImg;

int selectedItemPostion=0;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Object> itemsList=new ArrayList<Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        init();
        setListener();
        listItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

            Session.getInstance().logoutAndGoToLogin(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void init()
    {
        ivItemMainImg= (ImageView) findViewById(R.id.ivMainImg);
        mRecyclerView= (RecyclerView) findViewById(R.id.rvMain);

    }
    public void setListener()
    {
      /*  mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(MainActivity.this,
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                selectedItemPostion = position;
                MenuItems menuItems = (MenuItems) itemsList.get(position);

                Picasso.with(MainActivity.this).load(menuItems.getItemImg()).into(ivItemMainImg);





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/

        ivItemMainImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MenuItems menuItems = (MenuItems) itemsList.get(selectedItemPostion);


                Intent intent=new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("name",menuItems.getItemName());
                intent.putExtra("img",menuItems.getItemImg());
                intent.putExtra("code",menuItems.getItemCode());
                intent.putExtra("id",menuItems.getItemId());

                startActivity(intent);
            }
        });

    }


    public void listItems()
    {

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

                                MenuItems menuItems=new MenuItems(itemName,itemImg,itemCode,itemId,itemprice,itemQuantity,itemDescription);
                                itemsList.add(menuItems);
                            }



                            if (itemsList.size()>0)
                            {
                                MenuItems menuItems = (MenuItems) itemsList.get(0);

                                Picasso.with(MainActivity.this).load(menuItems.getItemImg()).into(ivItemMainImg);
                            }
                            mLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter = new ItemsRecyAdapter(itemsList,MainActivity.this);
                            mRecyclerView.setAdapter(mAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(SearchActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        // loginUser();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}


/*

class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private MainActivity.ClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {
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
}


*/
