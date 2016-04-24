package com.example.jupiter.brutalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Artist> artists = new ArrayList<Artist>();
    ListView listView;
    ArtistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Исполнители");
        this.listView = (ListView) findViewById(R.id.listView);
        if (artists.isEmpty()) {
            new MainTask().execute();
        } else {
            setLV();
        }
    }

    /**
     * Приклрепляет к {@link android.widget.ListView ListView} адаптер
     * Добавляет слушателя {@link android.widget.ListView ListView}
     */
    public void setLV() {
        adapter = new ArtistAdapter(this, -1, artists);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent i = new Intent(MainActivity.this, ArtistActivity.class);
                i.putExtra("chosen artist", artists.get(position));
                startActivity(i);
            }
        });
    }

    public class MainTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) (new URL(Variables.RESOURCE).openConnection());
                connection.connect();
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                JsonReader jsonReader = new JsonReader(streamReader);
                Gson gson = new GsonBuilder().create();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Artist artist = gson.fromJson(jsonReader, Artist.class);
                    artists.add(artist);
                }
                gson = null;
                jsonReader.close();
                streamReader.close();
                connection.disconnect();
                CacheManager.getInstance(getApplicationContext()).writeCache(artists);
            } catch (IOException e) {
                e.printStackTrace();
                artists = CacheManager.getInstance(getApplicationContext()).readCache();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setLV();
        }
    }
}
