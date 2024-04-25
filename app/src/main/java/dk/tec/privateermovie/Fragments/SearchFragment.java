package dk.tec.privateermovie.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
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

public class SearchFragment extends Fragment {

    EditText input;
    ImageButton searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View v = getView();
        if (v == null) return;
        searchButton = v.findViewById(R.id.btn_startsearch);
        input = v.findViewById(R.id.inp_search);

        searchButton.setOnClickListener(view -> getMovieBySearch());
        input.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) getMovieBySearch();
            return false;
        });
    }

    void getMovieBySearch() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        String query = input.getText().toString();
        String url = "https://api.themoviedb.org/3/search/movie?query=" + query;
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