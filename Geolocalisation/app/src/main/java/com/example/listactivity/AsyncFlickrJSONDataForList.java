package com.example.listactivity;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
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

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    URL url;
    ListView img;
    MyAdapter adap;

    public AsyncFlickrJSONDataForList( ListView im, MyAdapter myadap, URL url) throws MalformedURLException {

        img = im;
        adap = myadap;
        this.url = url;
        im.setAdapter(adap);
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
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
    protected void onPostExecute(JSONObject jsonObject) {

        try {

            for(int i = 0; i<( jsonObject.getJSONObject("photos").getJSONArray("photo")).length(); i++)
            {
                //adap.dd(((JSONObject) jsonObject.getJSONArray("items").get(i)).getJSONObject("media").getString("m"));
                String newURL;
                String id = ((JSONObject)jsonObject.getJSONObject("photos").getJSONArray("photo").get(i)).getString("id");
                String server = ((JSONObject)jsonObject.getJSONObject("photos").getJSONArray("photo").get(i)).getString("server");
                String secret = ((JSONObject)jsonObject.getJSONObject("photos").getJSONArray("photo").get(i)).getString("secret");
                newURL = new String("https://live.staticflickr.com/" + server+"/"+id+"_"+secret+".jpg");
                adap.dd(newURL);
            }
           // w = ((JSONObject) jsonObject.getJSONArray("items").get(0)).getJSONObject("media").getString("m");
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }

        adap.notifyDataSetChanged();


    }
}
