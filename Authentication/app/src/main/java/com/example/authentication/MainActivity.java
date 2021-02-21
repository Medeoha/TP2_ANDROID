package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button buttonAuth;

        setContentView(R.layout.activity_main);

        buttonAuth = findViewById(R.id.buttonAuth);
        buttonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((TextView)findViewById(R.id.EditName)).setText("Lapin");
                EditText EName = findViewById(R.id.EditName);
                EditText Epwd = findViewById(R.id.EditPwd);
                ((TextView)findViewById(R.id.Resultext)).setText("le pain c'st bon -1");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.Resultext)).setText("le pain c'st bon -2");

                        try {
                            URL url = null;
                            url = new URL("https://httpbin.org/basic-auth/bob/sympa");

                            String basicAuth = "Basic " + Base64.encodeToString((EName.getText()+":"+Epwd.getText()).getBytes(), Base64.NO_WRAP);
                            //String basicAuth = "Basic " + Base64.encodeToString("bob:sympa".getBytes(),                             Base64.NO_WRAP);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setRequestProperty ("Authorization", basicAuth);
                            //urlConnection.setUseCaches(false);
                            urlConnection.connect();

                            try {
                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                String s = readStream(in);

                                Log.i("JFL", s);
                                runThread("My result here:\n" + s);
                            } finally {
                                urlConnection.disconnect();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });

    }
    private void runThread(String msg) {

        new Thread() {
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.Resultext)).setText(msg);
                    }
                });

            }
        }.start();
    }
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

}