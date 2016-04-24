package com.example.jupiter.brutalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistAdapter extends ArrayAdapter {


    Picasso picasso;
    Context context;
    ArrayList<Artist> artistArrayAdapter;
    int resource;


    public ArtistAdapter(Context context, int resource, ArrayList<Artist> objects) {
        super(context, resource, objects);
        this.context = context;
        this.artistArrayAdapter = objects;
        this.resource = R.layout.artist_layout;
        this.picasso = Picasso.with(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final Artist artist = artistArrayAdapter.get(position);

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resource, parent, false);
        }

        final View finalView = view;

        picasso.load(artist.cover.small).networkPolicy(NetworkPolicy.OFFLINE).into((ImageView) view.findViewById(R.id.imageView), new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                picasso.load(artist.cover.small).into((ImageView) finalView.findViewById(R.id.imageView));

            }
        });
        ((TextView) view.findViewById(R.id.al_Name)).setText(artist.name);
        ((TextView) view.findViewById(R.id.al_Genres)).setText(artist.getGenres());
        ((TextView) view.findViewById(R.id.al_Numbers)).setText("Альбомов: " + artist.albums + ", песен: " + artist.tracks);

        return view;
    }
}
