package com.mastercoding.themovieapp.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.mastercoding.themovieapp.R;
import com.mastercoding.themovieapp.serviceapi.MovieApiService;
import com.mastercoding.themovieapp.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class MovieRepository {
    private ArrayList<Movie> movies = new ArrayList<>();
    private MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    private DatabaseReference databaseReference;


    public MovieRepository(Application application) {
        this.application = application;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public MutableLiveData<List<Movie>> getMutableLiveData(){
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<Result> call = movieApiService.
                getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                // Success
                Result result = response.body();

                if (result != null && result.getResults() != null){
                    movies = (ArrayList<Movie>) result.getResults();
                    mutableLiveData.setValue(movies);
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public void saveMovieToFirebase(Movie movie) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("users").child(userId).child("selectedMovies").child(String.valueOf(movie.getId())).setValue(movie)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("MovieRepository", "Película guardada exitosamente!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("MovieRepository", "Error al guardar la película", e);
                    }
                });
    }
}
