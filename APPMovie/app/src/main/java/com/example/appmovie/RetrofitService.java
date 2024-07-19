package com.example.appmovie;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitService {

    private static Retrofit retrofit;
    private static String BASE_URL  = "https://api.themoviedb.org/3/";

    public static ApiServicesMovie getService(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiServicesMovie.class);
    }
}
