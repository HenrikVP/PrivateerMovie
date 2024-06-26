package dk.tec.privateermovie;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dk.tec.privateermovie.Fragments.MovieFragment;
import dk.tec.privateermovie.Fragments.SearchFragment;
import dk.tec.privateermovie.Fragments.SeriesFragment;
import dk.tec.privateermovie.Fragments.StartFragment;
import dk.tec.privateermovie.Fragments.WatchlistFragment;
import dk.tec.privateermovie.Models.Genre;
import dk.tec.privateermovie.Models.Movie;
import dk.tec.privateermovie.Models.WatchlistItem;

public class MainActivity extends AppCompatActivity {

    public static RequestQueue rq;
    public static ArrayList<WatchlistItem> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rq = Volley.newRequestQueue(getApplicationContext());

        watchlist = new ArrayList<>(0);
        Genre.createGenres();
        initGui();
        fragmentChanger(StartFragment.class);
        //getUser();
    }

    void initGui() {
        findViewById(R.id.nav_movie).setOnClickListener(view -> fragmentChanger(MovieFragment.class));
        findViewById(R.id.nav_series).setOnClickListener(view -> fragmentChanger(SeriesFragment.class));
        findViewById(R.id.nav_watchlist).setOnClickListener(view -> fragmentChanger(WatchlistFragment.class));
        findViewById(R.id.btn_search).setOnClickListener(view -> fragmentChanger(SearchFragment.class));
    }

    private void fragmentChanger(Class c) {
        // if (getSupportFragmentManager().getBackStackEntryCount() > 0)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, c, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // Name can be null
                .commit();
    }


    public void getMovieByIdAsync(int id) {
        {
            String url = "https://api.themoviedb.org/3/movie/" + id;
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                Movie movie = new Gson().fromJson(response, Movie.class);
                Toast.makeText(getApplicationContext(), movie.title, Toast.LENGTH_LONG).show();
            }, error -> Log.e("Volley", error.toString())) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", Secrets.Token);
                    return params;
                }
            };
            rq.add(request);
        }
    }

    public void getUser() {
        {
            String url = "http://192.168.0.246:8989/api/user";
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                //Movie movie = new Gson().fromJson(response, Movie.class);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }, error -> Log.e("Volley", error.toString()));
//            {
////                @Override
////                public Map<String, String> getHeaders() {
////                    Map<String, String> params = new HashMap<>();
////                    params.put("Authorization", Secrets.Token);
////                    return params;
////                }
//            };
            rq.add(request);
        }
    }
}