package com.design.movie.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.design.movie.Activity.MainActivity;
import com.design.movie.Activity.MovieDetail;
import com.design.movie.Listener.CustomListener;
import com.design.movie.Model.Movie;
import com.design.movie.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterMovieList extends RecyclerView.Adapter<AdapterMovieList.MovieInfo> {
    ArrayList<Movie> movies;
    Context context;
    CustomListener listener;
    public AdapterMovieList(ArrayList<Movie> movies, Context context,CustomListener listener){
        this.movies=movies;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MovieInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieInfo(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieInfo holder, int position) {
        final int pos=position;
        String movieName = movies.get(position).getName();
        holder.name.setText(movieName);

        String imageUrl=movies.get(position).getImageUrl();
        Glide.with(context).load(imageUrl).centerCrop().into(holder.imageUrl);

        holder.stringImageUrl=imageUrl;
        holder.description=movies.get(position).getDescription();

        holder.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    protected void showDialog(View view,final int pos){

        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(context);
        alertBuilder.setTitle("DELETE");
        alertBuilder.setMessage("Dali ste sigurni?");
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(pos);
            }
        });
        alertBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context,"Delete Canceled!",Toast.LENGTH_LONG).show();
            }
        });
        alertBuilder.show();
    }
    public void removeItem(int position) {
        movies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, movies.size());
    }

    class MovieInfo extends RecyclerView.ViewHolder{
        final TextView name;
        private  ImageView imageUrl;
        final ImageButton buttonClose;
        String stringImageUrl;
        String description;
         MovieInfo(@NonNull final View itemView) {
            super(itemView);
             stringImageUrl="";
             description="";
            name=itemView.findViewById(R.id.tvNameOfMovie);
            imageUrl=itemView.findViewById(R.id.imageVUrl);
            buttonClose=itemView.findViewById(R.id.imageButtonClose);
            itemView.findViewById(R.id.LinearLayoutItem).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString(context.getString(R.string.movie_name_key),name.getText().toString());
                    bundle.putString(context.getString(R.string.movie_image_url_key),stringImageUrl);
                    bundle.putString(context.getString(R.string.movie_description_key),description);
                    listener.openDetailMovie(bundle);
                }
            });
        }

        public ImageView getImageUrl() {
            return imageUrl;
        }

    }
        public Context getContext(){
        return context;
        }

        public void sortByAZ(){
        Log.e("before entering sort",movies.get(0).getName());
            Collections.sort(movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie t1) {
                   return compareMovieName(movie,t1);
                }
            });
            notifyDataSetChanged();
        }
        private int compareMovieName(Movie movie,Movie t1){
            char []movieCharArr=movie.getName().toLowerCase().toCharArray();
            char []t1CharArr=t1.getName().toLowerCase().toCharArray();
            int i=0;
            for(i=0;i<movieCharArr.length;i++){
                if(i==t1CharArr.length){
                    return -1;
                }
                char firstMovieCharacter=movieCharArr[i];
                char secondMovieCharacter=t1CharArr[i];
                if(firstMovieCharacter>secondMovieCharacter){
                    return 1;
                }
                if(firstMovieCharacter<secondMovieCharacter){
                    return -1;
                }
            }
            if(i<t1CharArr.length){
                return 1;
            }
            return 0;
        }

}
