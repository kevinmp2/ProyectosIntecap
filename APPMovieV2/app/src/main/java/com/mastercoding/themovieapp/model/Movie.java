package com.mastercoding.themovieapp.model;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.firebase.database.Exclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mastercoding.themovieapp.BR;

import java.util.HashMap;
import java.util.Map;

public class Movie extends BaseObservable {

    public Movie() {
    }

    public Movie(Integer id, String title, String posterPath, String releaseDate, String overview, Double voteAverage) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("poster_path")
    @Expose
    private String posterPath;


    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("overview")
    @Expose
    private String overview;


    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;


    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        // Basic Url: "https://image.tmdb.org/t/p/w500/"
        String imagePath = "https://image.tmdb.org/t/p/w500/" + imageUrl;

        Glide.with(imageView.getContext())
                .load(imagePath)
                .into(imageView);
    }


    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
        notifyPropertyChanged(BR.overview);
    }


    @Bindable
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        notifyPropertyChanged(BR.posterPath);
    }

    @Bindable
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        notifyPropertyChanged(BR.releaseDate);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        notifyPropertyChanged(BR.voteAverage);
    }



}