package com.imdb.and;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.test.mock.MockContext;
import com.imdb.and.adapters.MovieListAdapter;
import com.imdb.and.model.slim.Movie;

import java.util.ArrayList;

public class MovieListAdapterTest extends AndroidTestCase {
    private MovieListAdapter mAdapter;

    private Movie mOne;
    private Movie mTwo;
    private Movie mThree;

    public MovieListAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        ArrayList<Movie> data = new ArrayList<Movie>();
        mOne = new Movie();
        mOne.popularity=10f;
        mOne.title="Movie One";
        mOne.releaseDate="1890";
        mTwo = new Movie();
        mTwo.popularity=20f;
        mTwo.title="Movie Two";
        mTwo.releaseDate="1452";
        mThree = new Movie();
        mThree.popularity=50f;
        mThree.title="Movie Three";
        mThree.releaseDate="1670";

        data.add(mOne);
        data.add(mTwo);
        data.add(mThree);
        Context ctx = new MockContext();
        mAdapter = new MovieListAdapter(ctx);
        mAdapter.movies=data;
    }


    public void testGetItem() {
        assertEquals("Movie 1 was expected.", mOne.title,
                ((Movie) mAdapter.getItem(0)).title);
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Movies amount incorrect.", 3, mAdapter.getCount());
    }

}