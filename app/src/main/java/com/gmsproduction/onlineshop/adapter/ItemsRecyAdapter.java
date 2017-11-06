package com.gmsproduction.onlineshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.activity.DetailsActivity;
import com.gmsproduction.onlineshop.activity.FavActivity;
import com.gmsproduction.onlineshop.other.MenuItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmedabouelfadle on 22/03/16.
 */
public class ItemsRecyAdapter extends RecyclerView.Adapter<ItemsRecyAdapter.ViewHolder> {
    private ArrayList<Object> itemsList;
    Context context;
     Typeface t1;
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvItemName;
        public CardView cvIcon;

        public ImageView ivItemImg;

        public TextView tvDetailName,tvDetailCode,tvDetailPrice,tvDetailDescription;

        // public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            //tvItemName = (TextView) v.findViewById(R.id.tvItemName);

            cvIcon= (CardView) v.findViewById(R.id.cvIcon);

            ivItemImg= (ImageView) v.findViewById(R.id.ivItemImg);


            tvDetailCode= (TextView) v.findViewById(R.id.tvCode);
            tvDetailName= (TextView) v.findViewById(R.id.tvName);

            tvDetailPrice= (TextView) v.findViewById(R.id.tvItemPrice12);



            // txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, String item) {
        itemsList.add(position, item);
        //  speechTypeArray.add(position,speech);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = itemsList.indexOf(item);
        itemsList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemsRecyAdapter(ArrayList<Object> itemsList, Context context) {
        this.itemsList=itemsList;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemsRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_main, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        final MenuItems menuItems= (MenuItems) itemsList.get(position);


        Picasso
                .with(context)
                .load(menuItems.getItemImg())
                .into(holder.ivItemImg);
        holder.tvDetailName.setText(menuItems.getItemName());
        holder.tvDetailCode.setText(menuItems.getItemCode());
        holder.tvDetailPrice.setText(menuItems.getItemPrice()+"  L.E");

       // Toast.makeText(context, ""+menuItems.getItemPrice(), Toast.LENGTH_SHORT).show();




        holder.cvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Activity activity = (Activity) context;

                Intent intent=new Intent(context,DetailsActivity.class);
                intent.putExtra("name",menuItems.getItemName());
                intent.putExtra("img",menuItems.getItemImg());
                intent.putExtra("code",menuItems.getItemCode());
                intent.putExtra("id",menuItems.getItemId());
                intent.putExtra("price",menuItems.getItemPrice());
                intent.putExtra("quantity",menuItems.getItemQuantity());
                intent.putExtra("description",menuItems.getItemDescription());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);


                //context.startActivity(new Intent(context, DetailsActivity.class));
            }
        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}