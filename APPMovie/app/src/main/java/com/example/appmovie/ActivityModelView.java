package com.example.appmovie;

import android.app.Application;
import android.util.AndroidException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ActivityModelView extends AndroidViewModel {
    private MovieRepositorio repositorio;

    public ActivityModelView(@NonNull Application application, MovieRepositorio repositorio) {
        super(application);
        this.repositorio = new MovieRepositorio(application);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return repositorio.getMutableLiveData();

    }
}
