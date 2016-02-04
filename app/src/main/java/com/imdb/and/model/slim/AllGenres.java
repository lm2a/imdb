package com.imdb.and.model.slim;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imdb.and.model.DataObject;


public class AllGenres extends DataObject {

    @SerializedName("genres")
    @Expose
    public ArrayList<Genre> genres = new ArrayList<Genre>();

}