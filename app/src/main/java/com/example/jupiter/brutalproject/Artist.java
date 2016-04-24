package com.example.jupiter.brutalproject;

import java.io.Serializable;

public class Artist implements Serializable {

    String link;
    Cover cover;
    int albums, tracks;
    String name, description;
    String[] genres;
    class Cover implements Serializable{
        String big;
        String small;
    }

    /**
     * преобразует массив жанров в строку
     * @return строка с жанрами
     */

    public String getGenres(){
        String tmp = "";
        for (int i = 0; i < genres.length; i++) {
            tmp += genres[i];
            if (i != genres.length - 1)
            tmp += ", ";
        }
        return tmp;
    }
}
