package com.gmsproduction.onlineshop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.gmsproduction.onlineshop.adapter.OrderRecyAdapter;
import com.gmsproduction.onlineshop.other.CartOrder;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.Session;
import com.gmsproduction.onlineshop.other.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OrderActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    AlertDialog dialog;
    LinearLayout ll_no_item;

    Button btnConfirmOrder;
    ArrayList<CartOrder> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Orders");



        list = new ArrayList(Session.getInstance().getOreders());

        init();
        recyclerSetUp(list);

        setListner();








    }


    public void init()
    {

        mRecyclerView= (RecyclerView) findViewById(R.id.rvCartOrder);
        ll_no_item= (LinearLayout) findViewById(R.id.ll_no_item);

        btnConfirmOrder= (Button) findViewById(R.id.btn_order_confirm);
    }



    public void setListner()
    {

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {

                    ll_no_item.setVisibility(View.GONE);
                    btnConfirmOrder.setVisibility(View.VISIBLE);


                    loadingConfirm();
                }
                else {

                    ll_no_item.setVisibility(View.VISIBLE);
                    btnConfirmOrder.setVisibility(View.GONE);
                }
            }
        });



    }


    public void recyclerSetUp(ArrayList<CartOrder> list)
    {

        if (list.size()>0) {

            ll_no_item.setVisibility(View.GONE);
            btnConfirmOrder.setVisibility(View.VISIBLE);
            mRecyclerView.hasFixedSize();
            mLayoutManager = new LinearLayoutManager(OrderActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new OrderRecyAdapter(list, OrderActivity.this);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            ll_no_item.setVisibility(View.VISIBLE);
            btnConfirmOrder.setVisibility(View.GONE);

        }
    }




    public void confirmOrder(final String user_id, final String item_id, final String quantity, final int itemSize, final String desc, final String date
            , final String order_id, View mView)
    {

        final LinearLayout ll_vertify= (LinearLayout) mView.findViewById(R.id.ll_vertify);
        final LinearLayout ll_loading= (LinearLayout) mView.findViewById(R.id.ll_loading);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CONFIRM_ORDER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.d("order",response);

                        try {
                            JSONObject object=new JSONObject(response);
                            String state=object.get("state").toString();
                            if (state.equals("1"))
                            {


                                Session.getInstance().deleteOreder(item_id);



                                if (itemSize == (list.size()))
                                {

                                    int index = itemSize;
                                    for (int i=0; i< itemSize ; i++) {
                                        index =index -1;

                                        OrderRecyAdapter orderRecyAdapter = (OrderRecyAdapter) mAdapter;
                                        orderRecyAdapter.removeItem(index);
                                        orderRecyAdapter.notifyDataSetChanged();


                                    }




                                    new CountDownTimer(2000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onFinish() {
                                            // TODO Auto-generated method stub

                                            ll_loading.setVisibility(View.GONE);
                                            ll_vertify.setVisibility(View.VISIBLE);

                                        }
                                    }.start();





                                }




                            }


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
                params.put("user_id",user_id);
                params.put("item_id",item_id);
                params.put("quantity",quantity);
                params.put("description",desc);
                params.put("date",date);
                params.put("order_id",order_id);





                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }




    public void loadingConfirm()
    {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderActivity.this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_loading_confirm, null);


        Button btn_back= (Button) mView.findViewById(R.id.btn_back);



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this,HomeActivity.class));
            }
        });




        Random r = new Random();
        int i1 = r.nextInt(5000000 - 0) + 0;



        for (int i=0 ; i< list.size() ; i++)
        {

            String quantity=list.get(i).getQuantity();
            String item_id=list.get(i).getItems_id();
            String user_id=list.get(i).getUser_id();
            String desc=list.get(i).getDescription();
            String order_id=user_id+String.valueOf(i1);

            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Log.d("date",date);


            confirmOrder(user_id,item_id,quantity,list.size(),desc,date,order_id,mView);





        }





        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        dialog.setCancelable(false);
        dialog.show();





    }

}
