package dk.tec.privateermovie;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

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
        initGui();
        fragmentChanger(StartFragment.class);
        getMovieByIdAsync(17);
    }

    void initGui() {
        findViewById(R.id.nav_movie).setOnClickListener(view -> {
            fragmentChanger(MovieFragment.class);
        });
        findViewById(R.id.nav_series).setOnClickListener(view -> {
            fragmentChanger(SeriesFragment.class);
        });
        findViewById(R.id.nav_watchlist).setOnClickListener(view -> {
            fragmentChanger(WatchlistFragment.class);
        });
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
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        {
            String url = "https://api.themoviedb.org/3/movie/" + id;
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                Movie movie = new Gson().fromJson(response, Movie.class);
                Toast.makeText(getApplicationContext(), movie.title, Toast.LENGTH_LONG).show();
            }, error -> Log.e("Volley", error.toString()))
            {
                @Override
                public Map<String, String> getHeaders(){
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1OWVhNzY5ODA2N2U5YTAyMTllYjNiNDU1MTljZjUxZSIsInN1YiI6IjY0MTk3OTRmMzEwMzI1MDA3YzBiMzk1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.73lcWBwBpTAbR9oFDaed3oKMMsP3UVBTG4XgpGTNYE4");
                    return params;
                }
            };
            rq.add(request);
        }
    }
}