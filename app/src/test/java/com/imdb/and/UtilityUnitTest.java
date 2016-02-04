package com.imdb.and;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import com.imdb.and.adapters.MovieListAdapter;
import com.imdb.and.helpers.Util;
import com.imdb.and.model.slim.Genre;
import com.imdb.and.model.slim.Movie;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UtilityUnitTest extends AndroidTestCase {

    Genre mGenre1, mGenre2, mGenre3;
    List<Integer> mGenreIdsT1, mGenreIdsT2, mGenreIdsT3;
    ArrayList<Genre> mData;


    protected void setUp() throws Exception {
        super.setUp();

        mGenreIdsT1 = new ArrayList<Integer>();
        mGenreIdsT1.add(new Integer(1));
        mGenreIdsT1.add(new Integer(2));
        mGenreIdsT1.add(new Integer(3));

        mGenreIdsT2 = new ArrayList<Integer>();
        mGenreIdsT2.add(new Integer(2));
        mGenreIdsT2.add(new Integer(3));

        mGenreIdsT3 = new ArrayList<Integer>();
        mGenreIdsT3.add(new Integer(0));

        mData = new ArrayList<Genre>();
        mGenre1 = new Genre();
        mGenre2 = new Genre();
        mGenre3 = new Genre();

        mGenre1.id=1;
        mGenre1.name="Drama";
        mGenre2.id=2;
        mGenre2.name="Comedy";
        mGenre3.id=3;
        mGenre3.name="Horror";

        mData.add(mGenre1);
        mData.add(mGenre2);
        mData.add(mGenre3);

    }


    public void testGenreOne() {

        assertEquals("Genre expected.", "Drama Comedy Horror",
                Util.allGenresString(mGenreIdsT1, mData));
    }

    public void testGenreTwo() {

        assertNotSame("Genre expected.", "Drama Comedy",
                Util.allGenresString(mGenreIdsT2, mData));
    }

    public void testGenreThree() {

        assertNotSame("Genre expected.", "Comedy Horror",
                Util.allGenresString(mGenreIdsT2, mData));
    }

    public void testGetNull() {
        assertNull(Util.allGenresString(mGenreIdsT3, mData));
    }
}