package com.example.flix.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flix.DetailActivity;
import com.example.flix.MainActivity;
import com.example.flix.R;
import com.example.flix.databinding.ActivityMainBinding;
import com.example.flix.databinding.ItemMovieBinding;
import com.example.flix.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    //Usually involves inflating a layout from XML and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        LayoutInflater lf = LayoutInflater.from(context);
        ItemMovieBinding binding = ItemMovieBinding.inflate(lf,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    //Involves populating data into the item through holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    @Override
    //Returns total count of items in the list
    public int getItemCount() {
        return movies.size();
    }

    public ImageView img(){
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, null);
        ImageView img = movieView.findViewById(R.id.ivPoster);
        return img;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ItemMovieBinding binding;

        public ViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

        public void bind(Movie movie) {

            binding.tvTitle.setText(movie.getTitle());
            binding.tvOverview.setText(movie.getOverview());

            String imageUrl;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
               imageUrl = movie.getBackdropPath();
            }
            else{
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(200,10)).placeholder(Drawable.createFromPath("/loading_image")).into(binding.ivPoster);

            //1. Register Click Listener on whole row
            //2. Navigate to new activity on tap

            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((AppCompatActivity)context, (View)binding.ivPoster, "details");
                    context.startActivity(i,options.toBundle());
                }
            });
        }
    }
}
