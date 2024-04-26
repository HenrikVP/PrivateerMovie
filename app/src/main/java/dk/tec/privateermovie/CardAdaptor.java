package dk.tec.privateermovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dk.tec.privateermovie.Fragments.DetailFragment;
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
            Glide.with(viewItem).load("https://image.tmdb.org/t/p/w500" + movie.poster_path).into(image);
            image.setOnClickListener(view -> {
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, DetailFragment.newInstance(movie.id), null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            });
        }
        return viewItem;
    }
}
