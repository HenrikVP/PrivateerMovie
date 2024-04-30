package dk.tec.privateermovie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.tec.privateermovie.Fragments.DetailFragment;
import dk.tec.privateermovie.Models.Movie;
import dk.tec.privateermovie.Models.MovieSearch;
import dk.tec.privateermovie.Models.WatchlistItem;

public class WatchlistAdaptor extends ArrayAdapter<WatchlistItem> {

    Context context;

    public WatchlistAdaptor(@NonNull Context context, @NonNull ArrayList<WatchlistItem> movies) {
        super(context, 0, movies);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View viewItem = convertView;
        if (viewItem == null)
            viewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.card_view, parent, false);

        WatchlistItem movie = getItem(position);

        getMovieByIdAsync(viewItem, movie.getMovieId());

        return viewItem;
    }

    public void getMovieByIdAsync(View viewItem, int id) {
        {
            String url = "https://api.themoviedb.org/3/movie/" + id;
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                Movie movie = new Gson().fromJson(response, Movie.class);
                Toast.makeText(getContext(), movie.title, Toast.LENGTH_LONG).show();

                if (movie.poster_path != null) {
                    ImageView image = viewItem.findViewById(R.id.img_cardimage);
                    Glide.with(viewItem).load("https://image.tmdb.org/t/p/w500" + movie.poster_path).into(image);
                    image.setOnClickListener(view -> {
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, DetailFragment.newInstance(movie.id), null)
                                .setReorderingAllowed(true)
                                .addToBackStack("name") // Name can be null
                                .commit();
                    });
                }

            }, error -> Log.e("Volley", error.toString())) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", Secrets.Token);
                    return params;
                }
            };
            MainActivity.rq.add(request);
        }
    }
}
