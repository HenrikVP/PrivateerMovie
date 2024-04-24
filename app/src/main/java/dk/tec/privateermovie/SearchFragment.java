package dk.tec.privateermovie;

import static dk.tec.privateermovie.MainActivity.rq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.tec.privateermovie.Models.MovieSearch;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getView().findViewById(R.id.btn_startsearch).setOnClickListener(view -> getMovieBySearch());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.btn_startsearch).setOnClickListener(view -> getMovieBySearch());

    }

    void getMovieBySearch() {
        String query = ((EditText)getView().findViewById(R.id.inp_search)).getText().toString();

        String url = "https://api.themoviedb.org/3/search/movie?query=" + query;
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            MovieSearch movieSearch = new Gson().fromJson(response, MovieSearch.class);
            Toast.makeText(getContext(), "# results: " + movieSearch.total_results, Toast.LENGTH_LONG).show();
            fillAdapter(movieSearch.results);

        }, error -> Log.e("Volley", error.toString()))
        {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", Secrets.Token);
                return params;
            }
        };
        rq.add(request);
    }

    private void fillAdapter(ArrayList<MovieSearch.Result> results) {
        CardAdaptor adaptor = new CardAdaptor(getContext(), results);
        GridView gridView =  getView().findViewById(R.id.view_results);
        gridView.setAdapter(adaptor);
    }
}