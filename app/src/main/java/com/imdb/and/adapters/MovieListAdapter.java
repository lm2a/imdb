package com.imdb.and.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imdb.and.R;
import com.imdb.and.helpers.Constants;
import com.imdb.and.helpers.MMLog;
import com.imdb.and.helpers.MySingleton;
import com.imdb.and.helpers.TCImageLoader;
import com.imdb.and.helpers.Util;
import com.imdb.and.model.slim.Genre;
import com.imdb.and.model.slim.Movie;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends BaseAdapter {


	public List<Movie> movies;
	private static LayoutInflater inflater = null;

	private int whiteID;
	private TCImageLoader mTCImageLoader;
	private int greyLightID;

	public MovieListAdapter(Context ctx) {

		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Resources res = ctx.getResources();
		if(res!=null) {
			whiteID = res.getColor(R.color.white);
			greyLightID = res.getColor(R.color.gray_light);
			mTCImageLoader = new TCImageLoader(ctx);
		}
	}

	public int getCount() {
		if(movies != null) {
			return movies.size();
		}else{
			return 0;
		}
	}

	public Movie getItem(int position) {
		return movies.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView thumb;
		TextView title, category, score;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.rel_list_item_movie, null);

			thumb = (ImageView) convertView.findViewById(R.id.thumb);
			title = (TextView) convertView.findViewById(R.id.title);
			category = (TextView) convertView.findViewById(R.id.category);
			score = (TextView) convertView.findViewById(R.id.score);
			convertView.setTag(new ViewHolder(thumb, title, category, score));
		}else{
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			thumb = viewHolder.thumb;
			title = viewHolder.title;
			category = viewHolder.category;
			score = viewHolder.score;

		}

		Movie m = movies.get(position);
		if (m != null) {

			if (m.backdropPath != null){
				String imgUrl = Constants.IMG_END_POINT+m.backdropPath;
				mTCImageLoader.display(imgUrl, thumb, R.drawable.placeholder);
			}
			ArrayList<Genre> genres = MySingleton.getInstance().mGenres;
			String cats = Util.allGenresString(m.genreIds, genres);

			title.setText(m.originalTitle + " ("
					+ m.releaseDate.substring(0, 4) + ")");

			category.setText(cats);

			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.CEILING);
			score.setText(df.format(m.popularity));

			if (position % 2 == 0) {
				// even...
				convertView.setBackgroundColor(greyLightID);

			} else {
				// odd...
				convertView.setBackgroundColor(whiteID);
			}

		}
		return convertView;
	}


	private static class ViewHolder {

		public final ImageView thumb;
		public final TextView title;
		public final TextView category;
		public final TextView score;

		public ViewHolder(ImageView thumb, TextView title, TextView category, TextView score) {
			this.thumb=thumb;
			this.title=title;
			this.category=category;
			this.score=score;
		}
	}



}