package com.example.sofiane.apprest.FullScreenActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sofiane.apprest.AsyncPicturesCreator;
import com.example.sofiane.apprest.R;

public class FullScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);
        TextView title = findViewById(R.id.title);
        TextView UrlWeb = findViewById(R.id.copyurlimg);
        TextView likesnb = findViewById(R.id.likesnb);
        TextView downloads = findViewById(R.id.downloads);
        TextView tags = findViewById(R.id.tagimg);

        Intent i = getIntent();

        title.setText(i.getExtras().getString("title"));
        likesnb.setText(i.getExtras().getString("likesnb"));
        downloads.setText(i.getExtras().getString("downloads"));
        tags.setText(i.getExtras().getString("tags"));
        String WebUrl = i.getExtras().getString("url");
        UrlWeb.setText(WebUrl);
        ImageView imageView = findViewById(R.id.fullscreenimg);
        AsyncPicturesCreator async = new AsyncPicturesCreator(WebUrl,imageView);
        async.execute();
    }

}
