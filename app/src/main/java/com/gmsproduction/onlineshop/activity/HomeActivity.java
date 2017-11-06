package com.gmsproduction.onlineshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.adapter.ItemsRecyAdapter;
import com.gmsproduction.onlineshop.other.CartOrder;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.MenuItems;
import com.gmsproduction.onlineshop.other.Session;
import com.gmsproduction.onlineshop.other.User;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {


    SwipeRefreshLayout srl_main;
    private static final String TAG = "ReactiveNetwork";
    private Disposable networkDisposable;
    private Disposable internetDisposable;

    private TextView tvConnectivityStatus;
    private TextView tvInternetStatus;

    ProgressBar pb_home;


    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;


    public static int badgeCount =0;




    ImageView ivItemMainImg;

    int selectedItemPostion=0;

    int refreshChecker=0;



    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


       // getSupportActionBar().setTitle("Cinderlla");


        init();
        setListener();
        listItems();


/*
        tvConnectivityStatus = (TextView) findViewById(R.id.connectivity_status);
        tvInternetStatus = (TextView) findViewById(R.id.internet_status);*/






    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
*/

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        ArrayList<CartOrder> list = new ArrayList(Session.getInstance().getOreders());
        HomeActivity.badgeCount =list.size();
        //you can add some logic (hide it if the count == 0)
        if (HomeActivity.badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_cart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, HomeActivity.badgeCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.action_cart));
        }
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


            Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            // override default transation of activity

            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);


            return true;



        }

        return super.onOptionsItemSelected(item);
    }

    public void init()
    {
        //ivItemMainImg= (ImageView) findViewById(R.id.ivMainImg);
        mRecyclerView= (RecyclerView) findViewById(R.id.rvMain);
        pb_home= (ProgressBar) findViewById(R.id.pb_home);
        srl_main= (SwipeRefreshLayout) findViewById(R.id.srl_main);

    }
    public void setListener()
    {

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                listItems();

            }
        });




      /*  mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(HomeActivity.this,
                mRecyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                selectedItemPostion = position;
                MenuItems menuItems = (MenuItems) itemsList.get(position);

                Intent intent=new Intent(HomeActivity.this,DetailsActivity.class);
                intent.putExtra("name",menuItems.getItemName());
                intent.putExtra("img",menuItems.getItemImg());
                intent.putExtra("code",menuItems.getItemCode());
                intent.putExtra("id",menuItems.getItemId());
                intent.putExtra("price",menuItems.getItemPrice());
                intent.putExtra("quantity",menuItems.getItemQuantity());
                intent.putExtra("description",menuItems.getItemDescription());

                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);





            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/




    }


    public void listItems()
    {
        final ArrayList<Object> itemsList = new ArrayList<Object>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ITEMS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("gallary",response);

                        pb_home
                                .setVisibility(View.GONE);

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

                            Collections.reverse(itemsList);



                            sharedPreferences=getSharedPreferences("HomeListSize", Context.MODE_PRIVATE);
                            // String played=sharedPreferences.getString("id"+idList.get(Constant.postion),"");
                            editor=sharedPreferences.edit();
                            editor.putInt("size",itemsList.size());
                            editor.commit();


                            mLayoutManager = new GridLayoutManager(HomeActivity.this,2);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mAdapter = new ItemsRecyAdapter(itemsList,HomeActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                            mRecyclerView.hasFixedSize();
                            srl_main.setRefreshing(false);



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


    @Override
    protected void onResume() {
        super.onResume();

        ArrayList<CartOrder> list = new ArrayList(Session.getInstance().getOreders());
        HomeActivity.badgeCount =list.size();


//********************************************************************************************************************************

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.drawer_item_crossfade_drawer_layout_drawer);


        User user=Session.getInstance().getUser();
        String username=user.getUsername();
        String email=user.getEmail();


        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName(username).withEmail(email).withIcon(R.drawable.cart);
        //  final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.bg)
                .addProfiles(profile)
               // .withSavedInstance(savedInstanceState)
                .build();



       /* ArrayList<CartOrder> list = new ArrayList(Session.getInstance().getOreders());
        HomeActivity.badgeCount =list.size();*/



        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Cart Shop").withIcon(GoogleMaterial.Icon.gmd_shopping_cart).withBadge(String.valueOf(HomeActivity.badgeCount)).withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("Favourite").withIcon(GoogleMaterial.Icon.gmd_favorite).withIdentifier(3).withSelectable(false),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn")
                        ,
                        new SecondaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_account_box_mail)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {





                        if (drawerItem instanceof Nameable) {
                        //    Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this) +position, Toast.LENGTH_SHORT).show();


                            //  Toast.makeText(HomeActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                            if (((Nameable) drawerItem).getName().getText(HomeActivity.this).equals("Login") ||((Nameable) drawerItem).getName().getText(HomeActivity.this).equals("Logout"))
                            {
                                Session.getInstance().logoutAndGoToLogin(HomeActivity.this);
                            }
                            else if (((Nameable) drawerItem).getName().getText(HomeActivity.this).equals("Favourite"))
                            {


                                Intent intent = new Intent(HomeActivity.this, FavActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                // override default transation of activity

                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

                            }
                            else if (((Nameable) drawerItem).getName().getText(HomeActivity.this).equals("Home"))
                            {


                                crossfadeDrawerLayout.closeDrawers();


                            }
                            else if (((Nameable) drawerItem).getName().getText(HomeActivity.this).equals("Cart Shop"))
                            {

                                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                // override default transation of activity

                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
                            }
                        }



                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })
                //   .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .withSelectedItemByPosition(1)
                .build();


        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });


//**********************************************************************************************************



        networkDisposable =ReactiveNetwork.observeNetworkConnectivity(HomeActivity.this)
                .subscribeOn(Schedulers.io())
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.CONNECTED))
                .filter(ConnectivityPredicate.hasType(ConnectivityManager.TYPE_WIFI))
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.DISCONNECTED))

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Connectivity>() {
                    @Override public void accept(final Connectivity connectivity) {
                        // do something

                        listItems();
                        srl_main.setRefreshing(false);


                    }


                });



        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {


                        listItems();
                        srl_main.setRefreshing(false);


                    }
                });






    }

    @Override protected void onPause() {
        super.onPause();

        crossfadeDrawerLayout.closeDrawers();

        safelyDispose(networkDisposable, internetDisposable);
        //finish();
    }

    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
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
}*/
