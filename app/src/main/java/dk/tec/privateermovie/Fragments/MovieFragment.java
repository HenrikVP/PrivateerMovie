package dk.tec.privateermovie.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.tec.privateermovie.CardAdaptor;
import dk.tec.privateermovie.MainActivity;
import dk.tec.privateermovie.Models.Genre;
import dk.tec.privateermovie.Models.MovieSearch;
import dk.tec.privateermovie.R;
import dk.tec.privateermovie.Secrets;

public class MovieFragment extends Fragment {
    private List sortItems;
    String sort;
    int genre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sortItems = new ArrayList<>(Arrays.asList(
                "popularity.desc",
                "vote_average.desc",
                "primary_release_date.desc",
                ""));

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        sortResults();
    }

    private void sortResults() {
        Spinner sortSpinner = getView().findViewById(R.id.spn_sort);
        ArrayAdapter adapter = new ArrayAdapter(
                getContext(), android.R.layout.simple_spinner_item, sortItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sort = sortItems.get(i).toString();
                getMovieDiscover();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner genreSpinner = getView().findViewById(R.id.spn_genre);
        ArrayAdapter genreAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item, Genre.genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genre = Genre.genres.get(i).id;
                getMovieDiscover();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Spinner mspin = getView().findViewById(R.id.spn_page);
        Integer[] items = new Integer[]{1, 2, 3, 4};
        ArrayAdapter<Integer> pageAdapter = new ArrayAdapter<Integer>(getContext(),
                android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(pageAdapter);

    }

    void getMovieDiscover() {
        String url = "https://api.themoviedb.org/3/discover/movie?" +
                "include_adult=true&include_video=false" +
                "&sort_by=" + sort;
        if (genre != 0) url +="&with_genres=" + genre;
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