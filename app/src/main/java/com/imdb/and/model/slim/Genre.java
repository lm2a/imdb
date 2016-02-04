package com.imdb.and.model.slim;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imdb.and.model.DataObject;


public class Genre extends DataObject {

@SerializedName("id")
@Expose
public int id;
@SerializedName("name")
@Expose
public String name;

}