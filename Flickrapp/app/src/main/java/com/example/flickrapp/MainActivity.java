package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity { //Class principale

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newImgButt = findViewById(R.id.getImage);
        newImgButt.setOnClickListener(new GetImageOnClickListener(findViewById(R.id.imageView)));//On definit le click de notre bouton avec notre class GetImageOnClickListener

    }
}

 class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject>// Class permettant de recuperer le JSON de maniere asynchrone
{
    URL url;
    ImageView img;

    public AsyncFlickrJSONData(URL url, ImageView im) {
        this.url = url;
        img = im;
    }


    @Override
    protected JSONObject doInBackground(String... strings) {// On se connecte et on recupere le JSON
        HttpURLConnection urlConnection = null;
        JSONObject j = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String s = readStream(in);
            System.out.println(s);
            j = new JSONObject(s);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        //urlConnection.setUseCaches(false);

        return j;
    }

    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {// On va chercher les urls que fournissent l'API
        String w = null;
        try {
            w = ((JSONObject) jsonObject.getJSONArray("items").get(0)).getJSONObject("media").getString("m");//On recupere les urls des images via le JSON qu'on a obtenu precedement
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(w);
        try {
            new AsyncBitmapDownloader(w,img).execute();// On lance une nouvelle tache qui va telecharger l'image
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}

class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap>//Meme principe que la classe du dessus mais ici on recupere une Bitmap(l'image) via l'url
{
    URL url;
    ImageView img ;

    public AsyncBitmapDownloader(String s, ImageView im) throws MalformedURLException {
        this.url = new URL(s);
        img = im;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {//Ici qu'on recupere l'image !
        HttpURLConnection urlConnection = null;
        Bitmap bm = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            bm = BitmapFactory.decodeStream(in);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)//On modifie l'image
    {
        img.setImageBitmap(bitmap);
    }
}