package com.example.appmovie;

import android.app.Application;


import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieRepositorio {

    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application aplicacion;

    public MovieRepositorio(Application aplicacion) {
        this.aplicacion = aplicacion;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData(){
        ApiServicesMovie apiServicesMovie = RetrofitService.getService();

        Call<Resultado> call = apiServicesMovie
                .getPopularMovies(aplicacion.getApplicationContext().getString(R.string.api_key));

        call.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Resultado resultado = response.body();
                if(resultado != null && resultado.getResultados() != null){
                    movies = (ArrayList<Movie>) resultado.getResultados();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }

        });
        return mutableLiveData;
    }
}
