package com.imdb.and;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.imdb.and.helpers.MMLog;
import com.imdb.and.helpers.MySingleton;
import com.imdb.and.helpers.TCImageLoader;
import com.imdb.and.helpers.Util;
import com.imdb.and.model.slim.Genre;
import com.imdb.and.model.slim.Movie;

import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MovieDetailFragment extends Fragment {

	public static final String ARG_ITEM_ID = "item_id";
	private int id;
	private TCImageLoader mTCImageLoader;

	public MovieDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTCImageLoader = new TCImageLoader(getContext());
		if (getArguments().containsKey(ARG_ITEM_ID)) {
			String sid = getArguments().getString(ARG_ITEM_ID);
			MMLog.i(sid);
			id = Integer.parseInt(sid);
			MySingleton.getInstance().mMovieId = id;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_movie_detail,
				container, false);

		Movie movie = MySingleton.getInstance().mMovies.get(id);

		if (movie != null) {

			ImageView vposter = (ImageView) rootView.findViewById(R.id.poster);

			TextView vtitle = (TextView) rootView.findViewById(R.id.title);

			TextView vscore = (TextView) rootView.findViewById(R.id.score);

			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.CEILING);
			vscore.setText(df.format(movie.popularity));

			TextView vcategories = (TextView) rootView
					.findViewById(R.id.category);

			TextView vstars = (TextView) rootView.findViewById(R.id.stars);

			TextView vsinopsis = (TextView) rootView
					.findViewById(R.id.sinopsis);

			populateImage(vposter, movie.posterPath);
			vtitle.setText(movie.originalTitle);
			ArrayList<Genre> genres = MySingleton.getInstance().mGenres;
			vcategories.setText(Util.allGenresString(movie.genreIds, genres));
			vstars.setText(movie.releaseDate);
			vsinopsis.setText(movie.overview);
		}

		return rootView;
	}



	public void populateImage(ImageView img, String imageUrl) {
		String basePosterThumb = "https://image.tmdb.org/t/p/w500"+imageUrl;
		mTCImageLoader.display(basePosterThumb, img, R.drawable.placeholder);
	}

}
