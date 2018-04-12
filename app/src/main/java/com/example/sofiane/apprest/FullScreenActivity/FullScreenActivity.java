package com.example.sofiane.apprest.FullScreenActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.sofiane.apprest.AsyncPicturesCreator;
import com.example.sofiane.apprest.R;

public class FullScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);

        Intent i = getIntent();
        String WebUrl = i.getExtras().getString("url");
        ImageView imageView = findViewById(R.id.fullscreenimg);
        AsyncPicturesCreator async = new AsyncPicturesCreator(WebUrl,imageView);
        async.execute();
    }
}
