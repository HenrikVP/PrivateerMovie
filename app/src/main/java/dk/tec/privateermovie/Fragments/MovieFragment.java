package dk.tec.privateermovie.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.tec.privateermovie.CardAdaptor;
import dk.tec.privateermovie.MainActivity;
import dk.tec.privateermovie.Models.MovieSearch;
import dk.tec.privateermovie.R;
import dk.tec.privateermovie.Secrets;

public class MovieFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovieDiscover();
    }

    void getMovieDiscover() {
        String url = "https://api.themoviedb.org/3/discover/movie";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            MovieSearch movieSearch = new Gson().fromJson(response, MovieSearch.class);
            Toast.makeText(getContext(), "# results: " + movieSearch.total_results,
                    Toast.LENGTH_LONG).show();

            fillAdapter(movieSearch.results);

        }, error -> Log.e("Volley", error.toString())) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", Secrets.Token);
                return params;
            }
        };
        MainActivity.rq.add(request);
    }

    private void fillAdapter(ArrayList<MovieSearch.Result> results) {
        CardAdaptor adaptor = new CardAdaptor(getContext(), results);
        GridView gridView = getView().findViewById(R.id.view_results);
        gridView.setAdapter(adaptor);
    }
}