package com.mastercoding.themovieapp.serviceapi;

import com.mastercoding.themovieapp.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    // The service interface defines the structure
    // and behavior of the API requests.
    // Acts as a bridge between your app and the API

    // Call<ResponseType>: Represents a network request
    // and its response. 'ResponseType' should be replaced
    // with the actual data model class that represents
    // the expected response from the API.

    @GET("movie/popular")
    Call<Result> getPopularMovies(@Query("api_key") String apiKey);

}
