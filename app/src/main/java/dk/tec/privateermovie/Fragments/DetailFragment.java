package dk.tec.privateermovie.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dk.tec.privateermovie.MainActivity;
import dk.tec.privateermovie.Models.Movie;
import dk.tec.privateermovie.R;
import dk.tec.privateermovie.Secrets;

public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "id";
    private int mParam1;
    TextView title, info;
    Button addToWatchlist;
    ImageView poster, backdrop;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public static DetailFragment newInstance(int param1) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }


    @Override
    public void onStart() {
        title = getView().findViewById(R.id.txt_title);
        info = getView().findViewById(R.id.txt_info);
        poster = getView().findViewById(R.id.img_poster);
        backdrop = getView().findViewById(R.id.img_backdrop);
        //webView = getView().findViewById(R.id.web_video);
        getMovieByIdAsync(mParam1);
        super.onStart();
    }

    public void getMovieByIdAsync(int id) {
        {
            String url = "https://api.themoviedb.org/3/movie/" + id;
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                Movie movie = new Gson().fromJson(response, Movie.class);
                ShowMovie(movie);
                Toast.makeText(getContext(), movie.title, Toast.LENGTH_LONG).show();
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

    private void ShowMovie(Movie movie) {
        title.setText(movie.title);
        info.setText(movie.overview
                + "\n\nRelease date: " + movie.release_date
                + "\n\nPopularity: " + movie.popularity
                + "\n\nGenres: " + movie.genres.toString()

        );
        Glide.with(getView()).load("https://image.tmdb.org/t/p/w500" + movie.poster_path).into(poster);
        Glide.with(getView()).load("https://image.tmdb.org/t/p/original" + movie.backdrop_path).into(backdrop);

//        String videoStr = "<html><body>Promo video<br><iframe width=\"420\" height=\"315\" src=\"https://www.youtube.com/embed/ulurgH9ePoM\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return false;
//            }
//        });
//
//        WebSettings ws = webView.getSettings();
//        ws.setJavaScriptEnabled(true);
//        webView.loadData(videoStr, "text/html", "utf-8");
    }
}