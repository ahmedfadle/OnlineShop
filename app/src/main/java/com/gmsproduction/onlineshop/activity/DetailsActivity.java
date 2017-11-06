package com.gmsproduction.onlineshop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cleveroad.splittransformation.SquareViewPagerIndicator;
import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.adapter.ColorsRecyAdapter;
import com.gmsproduction.onlineshop.adapter.ColorsRecyDialogAdapter;
import com.gmsproduction.onlineshop.adapter.DepthPageTransformer;
import com.gmsproduction.onlineshop.adapter.GallarySwipeAdapter;
import com.gmsproduction.onlineshop.adapter.ItemsRecyAdapter;
import com.gmsproduction.onlineshop.other.CartOrder;
import com.gmsproduction.onlineshop.other.ColorsModel;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.Session;
import com.gmsproduction.onlineshop.other.User;
import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

     AlertDialog dialog;


    ImageView ivDetailImg,ivFavItem;
    Button btn_cart;
    TextView tvDetailName,tvDetailCode,tvDetailPrice,tvDetailDescription;
    ViewPager viewPager;
    GallarySwipeAdapter customSwip;
    private SquareViewPagerIndicator indicator;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final List<Object> itemsListColors = new ArrayList<Object>();


    RecyclerView mColorsRV;
    RecyclerView.LayoutManager mColorsRVManger;
    RecyclerView.Adapter mAdapter;

    int itemNo=1;
    String id;
    int fav=0;
    String img="";
    String name="";
    String price="";
     String description="";
    String code="";
    ArrayList<String> imgList=new ArrayList<String>();
    ArrayList<String> colorsList=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        init();
        setListner();

    }


    public void init()
        {
           // ivDetailImg= (ImageView) findViewById(R.id.ivDetailImg);

            btn_cart= (Button) findViewById(R.id.btn_cart);

            tvDetailCode= (TextView) findViewById(R.id.tvDetailCode);
            tvDetailName= (TextView) findViewById(R.id.tvDetailName);
            tvDetailPrice= (TextView) findViewById(R.id.tvDetailPrice);
            tvDetailDescription= (TextView) findViewById(R.id.tvDetailDesc);


            ivFavItem= (ImageView) findViewById(R.id.ivFavItem);

            viewPager = (ViewPager) findViewById(R.id.ImageViewPager);
            viewPager.setPageTransformer(true, new DepthPageTransformer());


            mColorsRV= (RecyclerView) findViewById(R.id.rv_items_colors);

            //  indicator = (SquareViewPagerIndicator) findViewById(R.id.indicator);
        }


        public void setListner()
        {
            Intent intent=getIntent();
             name= intent.getStringExtra("name");
             img= intent.getStringExtra("img");
             code= intent.getStringExtra("code");
             id= intent.getStringExtra("id");
             price= intent.getStringExtra("price");
             String quantity= intent.getStringExtra("quantity");
             description= intent.getStringExtra("description");


          //  Picasso.with(DetailsActivity.this).load(img).into(ivDetailImg);
            tvDetailCode.setText(code);
            tvDetailName.setText(name);

            tvDetailDescription.setText(description);
            tvDetailPrice.setText(price+"  L.E");

            View view = null;


            itemImg(id);
            itemColors(id);






            ivFavItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sharedPreferences=getSharedPreferences("likes", Context.MODE_PRIVATE);
                    // String played=sharedPreferences.getString("id"+idList.get(Constant.postion),"");
                    editor=sharedPreferences.edit();



                    String favId=sharedPreferences.getString("id"+id,"1");
                    if (favId.equals("liked")) {

                        editor.remove("id"+id);
                        Toast.makeText(DetailsActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                        editor.commit();

                        ivFavItem.setBackground(getResources().getDrawable(R.drawable.like_button));
                    }
                    else {

                        editor.putString("id"+id,"liked");
                        Toast.makeText(DetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        ivFavItem.setBackground(getResources().getDrawable(R.drawable.liked));
                        editor.commit();

                    }





                }
            });











            btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailsActivity.this);

                    View mView = getLayoutInflater().inflate(R.layout.dialog_details_cart, null);

                    ImageView ivPlus= (ImageView) mView.findViewById(R.id.ivPlus);
                    final EditText etItemNo= (EditText) mView.findViewById(R.id.etItemNo);
                    final EditText etDescription= (EditText) mView.findViewById(R.id.et_order_desc_dialog);

                    ImageView ivMinus= (ImageView) mView.findViewById(R.id.ivMinus);
                    Button btnConfirm= (Button) mView.findViewById(R.id.btnConfirm);
                    final TextView tvDaligChecker= (TextView) mView.findViewById(R.id.tv_dialog_checker);


                    if (Session.getInstance().isItemExist(id)) {
                        tvDaligChecker.setVisibility(View.VISIBLE);



                       CartOrder order= Session.getInstance().getOreder(id);
                        String quantity =order.getQuantity();
                        String desc =order.getDescription();


                        etItemNo.setText(quantity);
                        etDescription.setText(desc);
                        Toast.makeText(DetailsActivity.this, "Exist in the Cart ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        tvDaligChecker.setVisibility(View.GONE);
                    }




                    RecyclerView mColorsRV= (RecyclerView) mView.findViewById(R.id.rv_dialog_detail);
                    RecyclerView.LayoutManager mColorsRVManger;
                    RecyclerView.Adapter mAdapter;

                    mColorsRVManger = new LinearLayoutManager(DetailsActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    mColorsRV.setLayoutManager(mColorsRVManger);
                    mAdapter = new ColorsRecyDialogAdapter(itemsListColors,id, DetailsActivity.this);
                    mColorsRV.setAdapter(mAdapter);




                    ivPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemNo ++;
                            etItemNo.setText(String.valueOf(itemNo));
                        }
                    });



                    ivMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!(itemNo <= 1)) {
                                itemNo--;
                                etItemNo.setText(String.valueOf(itemNo));
                            }
                        }
                    });








                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (Session.getInstance().isItemExist(id)) {

                                tvDaligChecker.setVisibility(View.VISIBLE);



                                Session.getInstance().updateOrderCart(String.valueOf(itemNo),etDescription.getText().toString(),id);
                                dialog.dismiss();
                                Toast.makeText(DetailsActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                            }
                            else {

                                tvDaligChecker.setVisibility(View.GONE);
                                User user= Session.getInstance().getUser();
                                String user_id=user.getId();
                                CartOrder order=new CartOrder();
                                order.setUser_id(user_id);
                                order.setItems_id(id);
                                order.setQuantity(String.valueOf(itemNo));
                                order.setName(name);
                                order.setPrice(price);
                                order.setImg(img);
                                order.setDescription(etDescription.getText().toString());
                                order.setDescriptionDb(description);
                                order.setCode(code);

                                Session.getInstance().insertCartOrder(order);
                                Toast.makeText(DetailsActivity.this, "Added to your CART ", Toast.LENGTH_SHORT).show();


                                dialog.dismiss();

                            }











                        }
                    });







                    mBuilder.setView(mView);
                     dialog = mBuilder.create();
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    dialog.show();
                }
            });






        }




    public void itemImg(final String id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.GALLAERY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("gallary",response);

                            try {
                                JSONArray read=new JSONArray(response);
                                for (int i=0;i<read.length();i++) {
                                    JSONObject item = read.getJSONObject(i);
                                    String img = item.get("img_url").toString();
                                    imgList.add(img);

                                    //Toast.makeText(DetailsActivity.this, ""+imgList.size(), Toast.LENGTH_SHORT).show();

                                }




                                customSwip=new GallarySwipeAdapter(DetailsActivity.this, imgList);
                                viewPager.setAdapter(customSwip);


                                PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
                                pageIndicatorView.setAnimationType(AnimationType.DROP);
                                pageIndicatorView.setUnselectedColor(getResources().getColor(R.color.colorWhite));

                                pageIndicatorView.setSelectedColor(getResources().getColor(R.color.colorOrange));
                                pageIndicatorView.setViewPager(viewPager);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }





                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("item_id",id);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void itemColors(final String id)
    {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.COLORS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("colors",response);

                        try {
                            JSONArray read=new JSONArray(response);
                            for (int i=0;i<read.length();i++) {
                                JSONObject item = read.getJSONObject(i);

                                String img = item.get("color").toString();


                                ColorsModel colorsModel=new ColorsModel();
                                colorsModel.setText(img);

                                itemsListColors.add(colorsModel);
                                colorsList.add(img);

                              //  Toast.makeText(DetailsActivity.this, "co"+colorsList.size(), Toast.LENGTH_SHORT).show();

                            }




                                mColorsRVManger = new LinearLayoutManager(DetailsActivity.this);
                                mColorsRV.setLayoutManager(mColorsRVManger);
                                mAdapter = new ColorsRecyAdapter(colorsList, DetailsActivity.this);
                                mColorsRV.setAdapter(mAdapter);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }





                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("item_id",id);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences=getSharedPreferences("likes", Context.MODE_PRIVATE);
        // String played=sharedPreferences.getString("id"+idList.get(Constant.postion),"");
        editor=sharedPreferences.edit();



        String favId=sharedPreferences.getString("id"+id,"1");
        if (favId.equals("liked")) {


            ivFavItem.setBackground(getResources().getDrawable(R.drawable.liked));
        }
        else {


            ivFavItem.setBackground(getResources().getDrawable(R.drawable.like_button));

        }
    }


  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailsActivity.this,HomeActivity.class));
    }*/
}
