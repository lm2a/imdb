package com.imdb.and.helpers;



import com.imdb.and.model.slim.Genre;
import com.imdb.and.model.slim.Movie;

import java.util.ArrayList;


public class MySingleton
{
  private static MySingleton instance;
   

  public ArrayList<Movie> mMovies;
  public ArrayList<Genre> mGenres;

  public int mMovieId;
   
  public static MySingleton getInstance()
  {
    if (instance == null)
    {
      // Create the instance
      instance = new MySingleton();
    }
    return instance;
  }
 

   
  private MySingleton()
  {
    // Constructor hidden because this is a singleton
  }
   

}