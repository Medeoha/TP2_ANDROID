package com.example.flickrapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

public class GetImageOnClickListener  implements View.OnClickListener {
    ImageView img;

    public GetImageOnClickListener(ImageView img) {
        this.img = img;
    }

    @Override
    public void onClick(View v) {
        URL url = null;

        try {
            url = new URL("https://www.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
       // new Thread((Runnable) new AsyncFlickrJSONData(url)).start();
        new AsyncFlickrJSONData(url,img).execute();
    }
}
