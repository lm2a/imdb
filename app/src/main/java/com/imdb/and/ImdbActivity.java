package com.imdb.and;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.imdb.and.helpers.MMLog;
import com.imdb.and.helpers.MySingleton;
import com.imdb.and.model.DataObject;
import com.imdb.and.model.slim.PopMovies;
import com.imdb.and.webservice.API;
import com.imdb.and.webservice.Interfaces;
import com.imdb.and.webservice.Response;

public class ImdbActivity extends FragmentActivity
        implements MovieListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);



        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MovieListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.movie_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link MovieListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MovieDetailFragment.ARG_ITEM_ID, id);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, MovieDetailActivity.class);
            detailIntent.putExtra(MovieDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.update) {
            MMLog.i("Eligio refresh");
            launchRingDialog();
            getPopularMovies();

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imdb, menu);
        return true;
    }

    ProgressDialog ringProgressDialog;

    public void launchRingDialog() {
        ringProgressDialog = ProgressDialog.show(ImdbActivity.this, "Please wait ...",	"Refreshing ...", true);
        ringProgressDialog.setCancelable(true);

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
                            ringProgressDialog.cancel();
                            MySingleton.getInstance().mMovies = ((PopMovies)dataObjects).results;

                            MovieListFragment fragment = ((MovieListFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.movie_list));
                            BaseAdapter adapter = (BaseAdapter) fragment.getListAdapter();
                            adapter.notifyDataSetChanged();


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