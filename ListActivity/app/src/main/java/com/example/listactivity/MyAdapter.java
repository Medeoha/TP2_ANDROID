package com.example.listactivity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.net.URL;
import java.util.Vector;

public class MyAdapter extends BaseAdapter {
    Vector<String> vector;//pour stocker les urls

    public MyAdapter() {
        vector = new Vector<>();
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if (convertView == null) {


            LayoutInflater inflater = LayoutInflater.from(parent.getContext());// Permet d'ajouter des vues dans un Layout
            convertView = inflater.inflate(R.layout.bitmaplayout, parent, false);

           ImageView imgview = convertView.findViewById(R.id.imageView);
           RequestQueue queue = MySingleton.getInstance(parent.getContext()).getRequestQueue();

           ImageRequest request = new ImageRequest(vector.get(position),
                   new Response.Listener<Bitmap>() {
                       @Override
                       public void onResponse(Bitmap response) {

                           imgview.setImageBitmap(response);// on sait l'image ici
                          // Log.i("CFG", vector.get(position));
                       }
                   }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
               @Override
                   public void onErrorResponse(VolleyError error) {

                       error.printStackTrace();
                   }
           });
           queue.add(request);// On add la request a la queue

        }
        return convertView;// On retourne la "nouvelle" view

    }
    public void dd(String url)
    {
        vector.add(url);

        Log.i("JFL", "Adding to adapter url : " + url);
    }
}
