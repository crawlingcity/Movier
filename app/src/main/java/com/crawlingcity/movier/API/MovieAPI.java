package com.crawlingcity.movier.API;

import com.crawlingcity.movier.Tmdb.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by crawl on 03/04/2016.
 * ${PACKAGE_NAME}
 */
public interface MovieAPI {
//hi
    String BASE_URL ="http://api.themoviedb.org/3/";
    @GET("movie/{endpoint}")
    Call<Movie> movies(@Path("endpoint") String endpoint,
                       @Query("api_key") String api_key,
                       @Query("language") String language);


}
