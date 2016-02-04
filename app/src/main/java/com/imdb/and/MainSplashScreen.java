package com.imdb.and;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;


import com.imdb.and.helpers.MMLog;
import com.imdb.and.helpers.MySingleton;
import com.imdb.and.model.DataObject;
import com.imdb.and.model.slim.AllGenres;
import com.imdb.and.model.slim.Genre;
import com.imdb.and.model.slim.PopMovies;
import com.imdb.and.webservice.API;
import com.imdb.and.webservice.Interfaces;
import com.imdb.and.webservice.Response;

import java.util.ArrayList;

public class MainSplashScreen extends Activity {
 

	long a, b;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main_splash_screen);

		b = getTimeStamp();
		MMLog.i("xTIME before: "+b);
		getAllGenres();
        getPopularMovies();





    }

	private void getAllGenres() {
		API api = new API(this);
		api.getGenres();
		api.setCallback(new Interfaces.APICallback() {
			public void apiDidFinish(final Response response,
									 final DataObject dataObjects) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					if (response.statusCode == 200) {

						ArrayList<Genre> genres = ((AllGenres)dataObjects).genres;
						MySingleton.getInstance().mGenres = genres;
					}
					else {
						MMLog.i("Genres loading failed...");
						showMessage();
					}
				}
			});
		}

	});
	}


	@Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    
	private void getPopularMovies() {

		API api = new API(this);
		api.popularMovies();
		api.setCallback(new Interfaces.APICallback() {

			public void apiDidFinish(final Response response,
					final DataObject dataObjects) {


						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (response.statusCode == 200) {

							        MySingleton.getInstance().mMovies = ((PopMovies)dataObjects).results;
									setTime();

				                    Intent i=new Intent(getBaseContext(), ImdbActivity.class);
				                    startActivity(i);
				                    finish();
								}
								else {
									MMLog.i("Movies loading failed...");
									showMessage();
								}
							}
						});
					}

				});

			}

	private void setTime() {
		a = getTimeStamp();
		MMLog.i("xTIME after: "+a);
		MMLog.i("xTIME delay: "+(a-b));
	}



	public long getTimeStamp(){
		long tsLong = System.currentTimeMillis();
		return tsLong;
	}

	public void showMessage(){
		new AlertDialog.Builder(this)
				.setTitle("Ops...")
				.setMessage("Problem conectiong with the server. Is you device connected?")
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	}

}