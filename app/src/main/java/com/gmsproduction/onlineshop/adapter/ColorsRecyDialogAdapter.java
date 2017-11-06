package com.gmsproduction.onlineshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gmsproduction.onlineshop.R;
import com.gmsproduction.onlineshop.other.CartOrder;
import com.gmsproduction.onlineshop.other.ColorsModel;
import com.gmsproduction.onlineshop.other.OrderColor;
import com.gmsproduction.onlineshop.other.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by ahmed on 8/29/2017.
 */

public class ColorsRecyDialogAdapter extends RecyclerView.Adapter<ColorsRecyDialogAdapter.MyViewHolder> {

    private List<Object> mModelList;
    private RealmList<ColorsArray> colorsList= new RealmList<ColorsArray>();
   OrderColor realmListColors;
    ArrayList<OrderColor> list;
    Context context;
    String id;
    int index = 0;


    public ColorsRecyDialogAdapter(List<Object> modelList,String id,Context context) {
        mModelList = modelList;
        this.id=id;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_items_colors, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ColorsModel model = (ColorsModel) mModelList.get(position);
       // holder.textView.setText(model.getText());





        if (model.getText().startsWith("#")) {


            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor(model.getText()), PorterDuff.Mode.DARKEN);
        }
        else {
            holder.cvColorsContainer.getBackground().setColorFilter(Color.parseColor("#"+model.getText()), PorterDuff.Mode.DARKEN);

        }



/*        //------------------------------------------------------------------------------------------------------------------------



         list = new ArrayList(Session.getInstance().getOrederColors(id));


        if (list.size()> 0 )
        {
            for (int i=0;i<list.size();i++)
            {
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);

                if (list.get(i).getColor_code().equals(model.getText()))
                {

                    if (model.getText().startsWith("#")) {
                        model.setSelected(!model.isSelected());
                        holder.view.setBackgroundColor(true ? Color.CYAN : Color.WHITE);
                    }
                    else {
                        model.setSelected(!model.isSelected());
                        holder.view.setBackgroundColor(true ? Color.CYAN : Color.WHITE);
                    }
                }
            }
        }


        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.cvColorsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
                holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                if (model.isSelected())
                {
                    OrderColor color=new OrderColor();
                    color.setItem_id(id);
                    color.setColor_code(model.getText());



                    Session.getInstance().insertCartOrderColor(color);

                }
                else
                {

                    Session.getInstance().deleteOrederColors(model.getText(),id);
                }
            }
        });




        //-------------------------------------------------------------------------------------------

    */

    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private CardView cvColorsContainer;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cvColorsContainer = (CardView) view.findViewById(R.id.fab);
        }
    }
}