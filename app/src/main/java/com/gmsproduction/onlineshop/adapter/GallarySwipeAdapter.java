package com.gmsproduction.onlineshop.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmsproduction.onlineshop.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user2 on 1/10/2017.
 */

public class GallarySwipeAdapter extends PagerAdapter {
private ArrayList<String> imageResources =new ArrayList<String>();
private ArrayList<String> idList =new ArrayList<String>();

private Context ctx;
private LayoutInflater layoutInflater;

     public GallarySwipeAdapter(Context c, ArrayList<String> imageList) {
        ctx=c;
        imageResources=imageList;
        }

@Override
public int getCount() {

        return imageResources.size();
        }



@Override
public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater= (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.image_custom_swip,container,false);
     ImageView imageView=(ImageView) itemView.findViewById(R.id.imgView);

        Picasso
        .with(ctx)
        .load(imageResources.get(position)).skipMemoryCache()
        .into(imageView);


        container.addView(itemView);
        return itemView;
        }


@Override
public void destroyItem(ViewGroup container, int position, Object object) {

        }

@Override
public boolean isViewFromObject(View view, Object object) {

        return  (view==object);
        }






        }
