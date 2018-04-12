package com.example.sofiane.apprest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sofiane.apprest.AsyncRestClient.AsyncRestClient;
import com.example.sofiane.apprest.AsyncRestClient.OnReceiveDataListener;
import com.example.sofiane.apprest.FullScreenActivity.FullScreenActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    protected Context cx;
    protected GridView gridView;
    protected EditText inputtxt;
    protected JSONArray albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cx = this;
        gridView = (GridView) findViewById(R.id.gridview);
        inputtxt = (EditText) findViewById(R.id.searchinput);

        loadData();

        findViewById(R.id.btnLoad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputtxt.getText().toString().trim().length() != 0) {
                    loadData();
                } else {
                    Toast.makeText(cx,"L'input ne peux pas être vide",Toast.LENGTH_LONG).show();
                }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (!albums.isNull(position)) {
                    Intent i = new Intent(getApplicationContext(), FullScreenActivity.class);
                    try {
                        i.putExtra("url", albums.getJSONObject(position).getString("webformatURL"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                }
            }
        });

    }

    protected void loadData() {

        ContentValues rqGetAllAlbums = new ContentValues();
        rqGetAllAlbums.put("HTTP_URL", "https://pixabay.com/api/");
        rqGetAllAlbums.put("HTTP_METHOD", "GET");
        rqGetAllAlbums.put("HTTP_ACCEPT", "application/json");
        rqGetAllAlbums.put("key", "8659420-58e5f6d4ca6042f60dc09dc54");
        if (inputtxt.getText().toString().trim().length() != 0) {
            rqGetAllAlbums.put("q",inputtxt.getText().toString());
            } else {
            ArrayList<String> randomsearch = new ArrayList<String>();
            randomsearch.add("flower");
            randomsearch.add("home");
            randomsearch.add("cars");
            Random random = new Random();
            rqGetAllAlbums.put("q", randomsearch.get(random.nextInt(randomsearch.size())));
        }

        rqGetAllAlbums.put("pretty", "true");
        rqGetAllAlbums.put("image_type", "photo");

        AsyncRestClient arc = new AsyncRestClient(this);
        arc.setOnReceiveDataListener(new OnReceiveDataListener() {
            @Override
            public void onReceiveData(JSONObject jsonObject) {
                try {
                    albums = jsonObject.getJSONArray("hits");
                    ArrayList<String> imgapi= new ArrayList<String>();
                    for (int i = 0; i < albums.length(); i++) {
                        imgapi.add(albums.getJSONObject(i).getString("previewURL"));
                    }
                    gridView.setAdapter(new ImageAdapter(cx,imgapi));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        arc.execute(rqGetAllAlbums);
    }
}
