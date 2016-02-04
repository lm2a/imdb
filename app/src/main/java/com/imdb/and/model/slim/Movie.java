package com.imdb.and.model.slim;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.imdb.and.model.DataObject;


public class Movie extends DataObject {

    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("video")
    @Expose
    public Boolean video;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage;

}













//    ///---------
//@SerializedName("adult")
//@Expose
//public Boolean adult;
//@SerializedName("backdrop_path")
//@Expose
//public String backdropPath;
//@SerializedName("belongs_to_collection")
//@Expose
//public BelongsToCollection belongsToCollection;
//@SerializedName("budget")
//@Expose
//public Integer budget;
//
//    @SerializedName("genre_ids")
//    @Expose
//    public List<Integer> genreIds = new ArrayList<Integer>();
//@SerializedName("homepage")
//@Expose
//public String homepage;
//@SerializedName("id")
//@Expose
//public Integer id;
//@SerializedName("imdb_id")
//@Expose
//public String imdbId;
//@SerializedName("original_language")
//@Expose
//public String originalLanguage;
//@SerializedName("original_title")
//@Expose
//public String originalTitle;
//@SerializedName("overview")
//@Expose
//public String overview;
//@SerializedName("popularity")
//@Expose
//public Float popularity;
//@SerializedName("poster_path")
//@Expose
//public String posterPath;
//@SerializedName("production_companies")
//@Expose
//public List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
//@SerializedName("production_countries")
//@Expose
//public List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
//@SerializedName("release_date")
//@Expose
//public String releaseDate;
//@SerializedName("revenue")
//@Expose
//public Integer revenue;
//@SerializedName("runtime")
//@Expose
//public Integer runtime;
//@SerializedName("spoken_languages")
//@Expose
//public List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();
//@SerializedName("status")
//@Expose
//public String status;
//@SerializedName("tagline")
//@Expose
//public String tagline;
//@SerializedName("title")
//@Expose
//public String title;
//@SerializedName("video")
//@Expose
//public Boolean video;
//@SerializedName("vote_average")
//@Expose
//public Float voteAverage;
//@SerializedName("vote_count")
//@Expose
//public Integer voteCount;
//
//}