package com.imdb.and.helpers;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class TCImageLoader implements ComponentCallbacks2 {
    private TCLruCache cache;

    public TCImageLoader(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
            Context.ACTIVITY_SERVICE);
        int maxBytes = am.getMemoryClass() * 1024 * 1024;
        int limitBytes = maxBytes / 8; // 1/8th of total ram
        MMLog.i("Adapter - imagen limitBytes: "+limitBytes);
        cache = new TCLruCache(limitBytes);
    }

    public void display(String url, ImageView imageview, int defaultresource) {
        MMLog.i("Adapter - url: "+url);
        imageview.setImageResource(defaultresource);
        Bitmap image = cache.get(url);
        if (image != null) {
            imageview.setImageBitmap(image);
            MMLog.i("Adapter - imagen "+url+" no nula");
        }
        else {
            MMLog.i("Adapter - imagen "+url+" NULA");
            new SetImageTask(imageview).execute(url);
        }
    }

    private class TCLruCache extends LruCache<String, Bitmap> {

        public TCLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            int kbOfBitmap = value.getByteCount() / 1024;
            return kbOfBitmap;
        }

    }

    private class SetImageTask extends AsyncTask<String, Void, Integer> {
        private ImageView imageview;
        private Bitmap bmp;

        public SetImageTask(ImageView imageview) {
            this.imageview = imageview;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String url = params[0];
            try {
                bmp = getBitmapFromURL(url);
                if (bmp != null) {
                    MMLog.i("Adapter - imagen "+url+" guardada en cache");
                    cache.put(url, bmp);
                    MMLog.i("Adapter - Cache size: "+cache.size());
                }
                else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                imageview.setImageBitmap(bmp);
            }
            super.onPostExecute(result);
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }
}