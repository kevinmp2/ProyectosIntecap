package com.mastercoding.themovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mastercoding.themovieapp.model.Movie;
import com.mastercoding.themovieapp.model.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private DatabaseReference databaseReference;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.repository = new MovieRepository(application);
        this.databaseReference = FirebaseDatabase.getInstance().getReference("movies");
    }
    public LiveData<List<Movie>> getAllMovies(){
        return repository.getMutableLiveData();
    }

    public void saveMovieToFirebase(Movie movie) {
        databaseReference.push().setValue(movie).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("Pelicula guardada exitosamente");
            } else {
                System.out.println("Error al guardar la película en Firebase: " + task.getException());
            }
        });
    }
}
