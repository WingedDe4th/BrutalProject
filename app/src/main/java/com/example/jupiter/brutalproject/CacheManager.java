package com.example.jupiter.brutalproject;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CacheManager {

    private static CacheManager instance;
    private Context context;

    private CacheManager(Context context) {
        this.context = context;
    }

    public static CacheManager getInstance(Context context) {
        if (instance == null) {
            instance = new CacheManager(context);
        }
        return instance;
    }


    /**
     *
     * @param artists список артистов, которые будут помещены в кэш
     */

    public void writeCache(ArrayList<Artist> artists) {

        try {
            File cacheFile = new File(context.getExternalCacheDir() + "/Artists.dat");
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(cacheFile);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeInt(artists.size());
            for (Artist artist : artists)
                objectOutStream.writeObject(artist);
            objectOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * @return список с артистами
     */

    public ArrayList<Artist> readCache() {

        try {
            FileInputStream inStream = new FileInputStream(context.getExternalCacheDir() + "/Artists.dat");
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            int count = objectInStream.readInt();
            ArrayList<Artist> artists = new ArrayList<>();
            for (int c = 0; c < count; c++)
                artists.add((Artist) objectInStream.readObject());
            objectInStream.close();
            return artists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}