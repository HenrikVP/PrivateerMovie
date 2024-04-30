package dk.tec.privateermovie.Models;

import java.time.Instant;

public class WatchlistItem {
    private int movieId;
    private Instant watched;
    private Instant added;
    private float rating;
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Instant getWatched() {
        return watched;
    }

    public void setWatched(Instant watched) {
        this.watched = watched;
    }

    public Instant getAdded() {
        return added;
    }

    public void setAdded(Instant added) {
        this.added = added;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public WatchlistItem(int movieId, Instant added) {
        this.movieId = movieId;
        this.added = added;
    }

    public WatchlistItem(){}
}
