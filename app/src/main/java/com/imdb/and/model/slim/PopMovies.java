package com.imdb.and.model.slim;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imdb.and.model.DataObject;


public class PopMovies extends DataObject {

@SerializedName("page")
@Expose
public Integer page;
@SerializedName("results")
@Expose
public ArrayList<Movie> results = new ArrayList<Movie>();

}