package com.imdb.and.helpers;



import com.imdb.and.model.slim.Genre;

import java.util.ArrayList;
import java.util.List;

public class Util {


    public static String allGenresString(List<Integer> genreIds, ArrayList<Genre> genres){
        StringBuffer sb = new StringBuffer();
        String result = null;
        for(Integer id: genreIds){
            sb.append(" "+getGenreById(id, genres));
        }
        if(!sb.toString().contains("null")){
            result = sb.length()>0?sb.toString().trim(): null;
        }

        return result;
    }

    public static String getGenreById(int id, ArrayList<Genre> genres){

        for(Genre g: genres){
            if(g.id==id){
                return g.name;
            }
        }
        return null;
    }

}
