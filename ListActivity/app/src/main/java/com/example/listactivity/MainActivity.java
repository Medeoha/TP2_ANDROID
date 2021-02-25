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
                MyAdapter adap = new MyAdapter();//On crée un adapter pour gerer comment les elements de la vue seront affichés
                try {
                    new AsyncFlickrJSONDataForList(findViewById(R.id.list),adap).execute();// On lance de facon asynchrone comme pour l'exercice precedent une class permettant de recuperer les urls
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}