package com.gmsproduction.onlineshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.activity.DetailsActivity;
import com.gmsproduction.onlineshop.other.MenuItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ahmedabouelfadle on 22/03/16.
 */
public class ColorsRecyAdapter extends RecyclerView.Adapter<ColorsRecyAdapter.ViewHolder> {
    private ArrayList<String> itemsColorList;
    Context context;
    Typeface t1;
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout rlColorsContaier;
        CardView cvColorsContainer;


        // public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);



            rlColorsContaier = (RelativeLayout) v.findViewById(R.id.rl_colors_container);
            cvColorsContainer = (CardView) v.findViewById(R.id.fab);

        }
    }

    public void add(int position, String item) {
        itemsColorList.add(position, item);
        //  speechTypeArray.add(position,speech);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = itemsColorList.indexOf(item);
        itemsColorList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ColorsRecyAdapter(ArrayList<String> itemsColorList, Context context) {
        this.itemsColorList=itemsColorList;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ColorsRecyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_items_colors, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        if (itemsColorList.get(position).startsWith("#")) {


            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor(itemsColorList.get(position)), PorterDuff.Mode.DARKEN);
        }
        else {
            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor("#"+itemsColorList.get(position)), PorterDuff.Mode.DARKEN);

        }





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsColorList.size();
    }

}