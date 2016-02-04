package com.imdb.and.webservice;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.imdb.and.helpers.Constants;
import com.imdb.and.helpers.MMLog;
import com.imdb.and.model.DataObject;
import com.imdb.and.model.slim.AllGenres;
import com.imdb.and.model.slim.Movie;
import com.imdb.and.model.slim.PopMovies;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


@SuppressLint("DefaultLocale")
public class API {

    private Context ctx;
    public Interfaces.APICallback apiCallback;



    public static final int TIMEOUT_ERROR = -1001;
    public static final int OFFLINE_ERROR = -1009;


    public API(Context ctx) {
        this.ctx = ctx;
    }

    public void setCallback(Interfaces.APICallback apiCallback) {
        this.apiCallback = apiCallback;
    }

   

    
    public void getGenres(){
        new Thread(new Runnable() {
            public void run() {
                String service = Constants.GENRE_END_POINT;
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("api_key", Constants.API_KEY);

                Response response = execute(urlForService(service, parameters), "GET",
                        service, null, false, false);

                AllGenres responseDataObject = new Gson().fromJson(
                        response.jsonResponse, AllGenres.class);

                apiCallback.apiDidFinish(response, responseDataObject);
            }
        }).start();
    }

    public void popularMovies() {
        new Thread(new Runnable() {
            public void run() {
                String service = Constants.END_POINT+"popular";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("api_key", Constants.API_KEY);
                
                Response response = execute(urlForService(service, parameters), "GET",
                        service, null, false, false);
                List<DataObject> movies = new ArrayList<DataObject>();

                List<? extends DataObject> bases = null;

                PopMovies responseDataObject = new Gson().fromJson(
                        response.jsonResponse, PopMovies.class);

                apiCallback.apiDidFinish(response, responseDataObject);
            }
        }).start();
    }

    public Movie getMovieById(final int id) {
        String service = Constants.END_POINT+id;
        String languageCode = Locale.getDefault().getLanguage();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("api_key", Constants.API_KEY);
        //parameters.put("append_to_response", "trailers%2Ccasts%2Cimages");
        parameters.put("include_image_language", languageCode+"%2Cnull");                
        
        Response response = execute(urlForService(service, parameters), "GET",
                service, null, false, false);
        
        Movie responseDataObject = new Gson().fromJson(
                response.jsonResponse, Movie.class);
        return responseDataObject;
    }
    





    private URL urlForService(String urlString, Map<String, String> parameters) {

        // If we've no params, just return the basic URL
        if (parameters != null) {
            int i = 0;

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (i == 0) {
                    urlString = urlString.concat("?");
                }
                urlString = urlString.concat(key + "=" + value);

                if (i + 1 != parameters.size()) {
                    urlString = urlString.concat("&");
                }

                i++;
            }
        }

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * method ready to add more headers
     * @param service
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> headers(String service)
            throws UnsupportedEncodingException {
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private Response response(boolean success, String jsonResponse,
                              int statusCode, String error, String service, String location) {
        Response response = new Response();
        response.success = success;
        response.statusCode = statusCode;
        response.jsonResponse = jsonResponse;
        response.errorCode = error;
        response.location = location;
        response.generateDisplayMessage(ctx);

        response.logDetails();
        return response;
    }

    public static String convertStreamToString(InputStream inputStream) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    // Network operation
    public Response execute(URL url, String method, String service,
                             DataObject dataObject, boolean getRedirectLocation,
                             boolean followRedirects) {
        String responseString = "";
        String responseMessage = "";
        String location = "";
        int statusCode = 0;
        boolean success = false;
        HttpURLConnection connection = null;
        MMLog.i("Requesting -->: " + service + "(" + url + ")");
        try {
            if (url.getProtocol().contains("https")) {
                connection = (HttpsURLConnection) url.openConnection();
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setInstanceFollowRedirects(followRedirects);
            connection.setConnectTimeout(Constants.TIMEOUT_CONNECTION);
            connection.setReadTimeout(Constants.TIMEOUT_CONNECTION);
            connection.setRequestMethod(method);

            // Add headers
            for (Map.Entry<String, String> entry : this.headers(service)
                    .entrySet()) {
                MMLog.v("header---> " + entry.getKey() + ": "
                        + entry.getValue());
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // If its a POST, add the body as JSON
            if (method.equals("POST") && dataObject != null) {
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                MMLog.v(dataObject.toJsonString());
                wr.writeBytes(dataObject.toJsonString());
                wr.flush();
                wr.close();
            }

            if (getRedirectLocation) {
                location = connection.getHeaderField("Location");
            }

            responseMessage = connection.getResponseMessage();
            MMLog.i("responseMessage: " + responseMessage);
            statusCode = connection.getResponseCode();
            MMLog.i("responseCode: " + statusCode);
            InputStream inputStream = connection.getInputStream();
            responseString = API.convertStreamToString(inputStream);

            inputStream.close();


            success = true;
        } catch (Exception e) {
            e.printStackTrace();

            if (e.getClass().equals(SocketTimeoutException.class)) {
                statusCode = TIMEOUT_ERROR;
            } else if (e.getClass().equals(UnknownHostException.class)) {
                statusCode = OFFLINE_ERROR;
            }

            // This determines if the user has an invalid login token
            else if (e.getClass().equals(IOException.class)) {
                if (e.getMessage().equals("No authentication challenges found")) {
                    statusCode = 401;
                }
            }

            try {
                String errorResponse = API.convertStreamToString(connection
                        .getErrorStream());
                JSONObject jsonError = new JSONObject(errorResponse);
                responseMessage = jsonError.getString("errorCode");
            } catch (Exception error) {
                e.printStackTrace();
            }

            MMLog.v("Error API execute for service: " + service + "("
                    + e.getClass().getName() + ")");
        }

        MMLog.i("bebay: " + "\n sucess=" + success + "\n responseString=" + responseString + "\n statusCode=" + statusCode + "\n responseMessage=" + responseMessage + "\n service=" + service + "\n location=" + location);

        return response(success, responseString, statusCode, responseMessage,
                service, location);
    }
}

//API EXAMPLES

//Ejemplo para traer una peli con trailer
//https://api.themoviedb.org/3/movie/41602?api_key=c804980d1f7e6be70c7d58ed0e41f0fd&append_to_response=releases,trailers

//Ejemplo para pelis mas populares
//https://api.themoviedb.org/3/movie/popular?api_key=c804980d1f7e6be70c7d58ed0e41f0fd

//Ejemplo para sacar los actores de una peli
//http://api.themoviedb.org/3/movie/550/casts?api_key=c804980d1f7e6be70c7d58ed0e41f0fd

//Ejemplo para sacar las imagenes de una peli
//https://api.themoviedb.org/3/movie/1948/images?api_key=c804980d1f7e6be70c7d58ed0e41f0fd

//Ejemplo para ver una imagen de una peli
//http://image.tmdb.org/t/p/w300/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg

//Ejemplo para ver category, popularity, year, synopsis, actors, releases and images in one call
//https://api.themoviedb.org/3/movie/550?api_key=c804980d1f7e6be70c7d58ed0e41f0fd&append_to_response=trailers,casts,images&include_image_language=en,null

/*
    https://api.themoviedb.org/3/movie/popular?api_key=c804980d1f7e6be70c7d58ed0e41f0fd
    https://api.themoviedb.org/3/movie/550?api_key=c804980d1f7e6be70c7d58ed0e41f0fd&append_to_response=trailers,casts,images&include_image_language=en,null
*/
//aca devuelve los valores posibles de imagenes de cada tipo (backdrops, posters, etc.)
//http://api.themoviedb.org/3/configuration?api_key=c804980d1f7e6be70c7d58ed0e41f0fd
//elijo una imagen poster y le pongo w92 para la lista en telefono
//https://image.tmdb.org/t/p/w92/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg

//un backdrop es un fotograma usado para promocion
//  https://image.tmdb.org/t/p/w92/hNFMawyNDWZKKHU4GYCBz1krsRM.jpg

// -------------------------- Generic Functions --------------------------


//    https://api.themoviedb.org/3/movie/popular?api_key=c804980d1f7e6be70c7d58ed0e41f0fd
//    https://api.themoviedb.org/3/movie/550?api_key=c804980d1f7e6be70c7d58ed0e41f0fd&append_to_response=trailers,casts,images&include_image_language=en,null

