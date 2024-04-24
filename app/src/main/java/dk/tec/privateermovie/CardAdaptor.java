package dk.tec.privateermovie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dk.tec.privateermovie.Models.MovieSearch;

public class CardAdaptor extends ArrayAdapter<MovieSearch.Result> {

    Context context;

    public CardAdaptor(@NonNull Context context, @NonNull ArrayList<MovieSearch.Result> movies) {
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

        MovieSearch.Result movie = getItem(position);

        if (movie.poster_path != null) {
            ImageView image = viewItem.findViewById(R.id.img_cardimage);
            Glide.with(viewItem).load("https://image.tmdb.org/t/p/w500"+movie.poster_path).into(image);
            //Picasso.get().load(movie.getImage()+"/low.jpg").into(image);
        }

//        viewItem.setOnClickListener(view -> {
//            StringRequest request = new StringRequest(
//                    Request.Method.GET,
//                    "https://api.tcgdex.net/v2/en/cards/" + movie.getId(),
//                    response -> {
//                        Intent myIntent = new Intent(context, CardActivity.class);
//                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        myIntent.putExtra("card", response); //Optional parameters
//                        context.startActivity(myIntent);
//                    },
//                    volleyError -> {
//                        Log.e("Volley", volleyError.getMessage());
//                    });
//            MainActivity.queue.add(request);
//        });

        return viewItem;
        //return super.getView(position, convertView, parent);
    }
}
