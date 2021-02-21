package com.example.listactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button listImgButt = findViewById(R.id.buttonImg);
        listImgButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_list);
                ListView listView = findViewById(R.id.list);
                MyAdapter adap = new MyAdapter();
                try {
                    new AsyncFlickrJSONDataForList(findViewById(R.id.list),adap).execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}