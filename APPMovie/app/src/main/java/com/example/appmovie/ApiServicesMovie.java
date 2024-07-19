package com.example.appmovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServicesMovie {

    @GET("movie/popular")
    Call<Resultado> getPopularMovies(@Query("api_key") String apikey);
}
