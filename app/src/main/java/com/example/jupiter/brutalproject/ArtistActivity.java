package com.example.jupiter.brutalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Artist artist = (Artist) getIntent().getSerializableExtra("chosen artist");
        setTitle(artist.name);
        final ImageView imageView = (ImageView) findViewById(R.id.aa_big_cover);

        Picasso.with(this).load(artist.cover.big).networkPolicy(NetworkPolicy.OFFLINE).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(artist.cover.big).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext()).load(artist.cover.small).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
                    }
                });
            }
        });
        ((TextView) findViewById(R.id.aa_description)).setText(artist.description);
    }
}
