package com.gmsproduction.onlineshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.activity.DetailsActivity;
import com.gmsproduction.onlineshop.other.CartOrder;
import com.gmsproduction.onlineshop.other.ColorsModel;
import com.gmsproduction.onlineshop.other.Constant;
import com.gmsproduction.onlineshop.other.MenuItems;
import com.gmsproduction.onlineshop.other.Session;
import com.gmsproduction.onlineshop.other.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

/**
 * Created by ahmedabouelfadle on 22/03/16.
 */
public class OrderRecyAdapter extends RecyclerView.Adapter<OrderRecyAdapter.ViewHolder> {


     RecyclerView.Adapter mAdapter;
     RecyclerView mColorsRV;
     RecyclerView.LayoutManager mColorsRVManger;


    public ArrayList<CartOrder> order;
    Context context;
    Typeface t1;
     AlertDialog editeDialog,mainDialog;


    int itemNo = 1;
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout rlColorsContaier;
        CardView cvOderRow;


         public TextView tvOrderName,tvOrderPrice,tvOrderQuantity;
        public ImageView ivordeImg;

        public ViewHolder(View v) {
            super(v);



            cvOderRow= (CardView) v.findViewById(R.id.cv_order_row);

            ivordeImg= (ImageView) v.findViewById(R.id.iv_order_img);


            tvOrderName= (TextView) v.findViewById(R.id.tv_order_name);
            tvOrderPrice= (TextView) v.findViewById(R.id.tv_order_price);
            tvOrderQuantity= (TextView) v.findViewById(R.id.tv_order_quantity);



        }
    }

/*    public void add(int position, String item) {
        itemsColorList.add(position, item);
        //  speechTypeArray.add(position,speech);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = itemsColorList.indexOf(item);
        itemsColorList.remove(position);
        notifyItemRemoved(position);






    }*/





    public void removeItem(int position) {
        order.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, order.size());
        notifyDataSetChanged();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderRecyAdapter(ArrayList<CartOrder> order, Context context) {
        this.order=order;
        this.context=context;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderRecyAdapter() {

    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_cart, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


/*        if (itemsColorList.get(position).startsWith("#")) {


            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor(itemsColorList.get(position)), PorterDuff.Mode.DARKEN);
        }
        else {
            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor("#"+itemsColorList.get(position)), PorterDuff.Mode.DARKEN);

        }*/



        // get the article
       // final CartOrder order = get

       // Toast.makeText(context, ""+order.get(position).getUser_id(), Toast.LENGTH_SHORT).show();


        User user= Session.getInstance().getUser();
        String user_id=user.getId();
        if (user_id.equals(order.get(position).getUser_id())) {


            holder.tvOrderName.setText(order.get(position).getName());
            holder.tvOrderQuantity.setText(order.get(position).getQuantity());
            holder.tvOrderPrice.setText(order.get(position).getPrice());


            Picasso.with(context).load(order.get(position).getImg()).into(holder.ivordeImg);


           // Toast.makeText(context, "" + order.get(position).getUser_id(), Toast.LENGTH_SHORT).show();





            holder.cvOderRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Activity activity= (Activity) context;
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                    View mView = activity.getLayoutInflater().inflate(R.layout.dialog_order_confirm, null);


                    Button btn_review= (Button) mView.findViewById(R.id.btn_review);
                    Button btn_edite= (Button) mView.findViewById(R.id.btn_edite);
                    Button btn_delete= (Button) mView.findViewById(R.id.btn_delete);




                    btn_review.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                       //  context.startActivity(new Intent(context,DetailsActivity.class));

                            User user= Session.getInstance().getUser();
                            String user_id=user.getId();
                            if (user_id.equals(order.get(position).getUser_id())) {



                                Activity activity1 = (Activity) context;

                                Intent intent = new Intent(context, DetailsActivity.class);
                                intent.putExtra("name", order.get(position).getName());
                                intent.putExtra("img", order.get(position).getImg());
                                intent.putExtra("code", order.get(position).getCode());
                                intent.putExtra("id", order.get(position).getItems_id());
                                intent.putExtra("price", order.get(position).getPrice());
                                intent.putExtra("quantity", order.get(position).getQuantity());
                                intent.putExtra("description", order.get(position).getDescriptionDb());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                context.startActivity(intent);
                                activity1.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);

                                mainDialog.dismiss();


                            }



                        }
                    });



                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           Session.getInstance().deleteOreder(order.get(position).getItems_id());
                           // Session.getInstance().deleteOrederItemsColors("10");
                            removeItem(position);


                            Toast.makeText(context, "This item is canceled", Toast.LENGTH_SHORT).show();


                            mainDialog.dismiss();

                        }
                    });




                    btn_edite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            orderEdite(context,order.get(position).getItems_id());

                            mainDialog.dismiss();





                        }
                    });








                    mBuilder.setView(mView);
                      mainDialog = mBuilder.create();
                    mainDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
                    mainDialog.show();


                /*  */
                }
            });


        }






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return order.size();
    }





    public void orderEdite(final Context context, final String id)
    {



        Activity activity= (Activity) context;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

        View mView = activity.getLayoutInflater().inflate(R.layout.dialog_details_cart, null);

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
            Toast.makeText(context, "Exist in the Cart ", Toast.LENGTH_SHORT).show();
        }
        else {
            tvDaligChecker.setVisibility(View.GONE);
        }




             itemColors(id,mView);









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


                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    editeDialog.dismiss();
                }
                else {

                  /*  tvDaligChecker.setVisibility(View.GONE);
                    User user= Session.getInstance().getUser();
                    String user_id=user.getId();
                    CartOrder order=new CartOrder();
                    order.setUser_id(user_id);
                    order.setItems_id(id);
                    order.setQuantity(String.valueOf(itemNo));
                    order.setName(name);
                    order.setPrice(price);
                    order.setImg(img);
                    order.setDescriptionDb(description);
                    order.setCode(code);

                    Session.getInstance().insertCartOrder(order);
                    Toast.makeText(DetailsActivity.this, "Added", Toast.LENGTH_SHORT).show();*/



                }

                notifyDataSetChanged();


            }
        });







        mBuilder.setView(mView);
         editeDialog = mBuilder.create();
        editeDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        editeDialog.show();
    }





















    public void itemColors(final String id,final View mView)
    {

        mColorsRV= (RecyclerView) mView.findViewById(R.id.rv_dialog_detail);
        final List<Object> itemsListColors = new ArrayList<Object>();




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


                            }










                            mColorsRVManger = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                            mColorsRV.setLayoutManager(mColorsRVManger);
                            mAdapter = new ColorsRecyDialogAdapter(itemsListColors, id ,context);
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

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }













}