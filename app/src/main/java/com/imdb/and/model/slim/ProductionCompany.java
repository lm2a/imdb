package com.imdb.and.model.slim;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductionCompany {

@SerializedName("name")
@Expose
public String name;
@SerializedName("id")
@Expose
public Integer id;

}