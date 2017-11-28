package com.example.branko.lab_02;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Branko on 14.11.2017.
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public ImageView poster;
    public TextView movieName;
    public TextView movieYear;
    public RecyclerAdapter recyclerAdapter;

    public static final String EXTRA_MOVIE_TITLE = "movie title";

    public RecyclerViewHolder(View itemView, final RecyclerAdapter recyclerAdapter) {
        super(itemView);
        poster = itemView.findViewById(R.id.moviePoster);
        movieName = itemView.findViewById(R.id.movieName);
        movieYear = itemView.findViewById(R.id.movieYear);
        this.recyclerAdapter = recyclerAdapter;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailsActivity.class);
                intent.putExtra(EXTRA_MOVIE_TITLE, movieName.getText().toString());
                if(isNetworkConnected()) {
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(recyclerAdapter.context,"No Internet connection!!! Please connect to Internet" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerAdapter.removeItem(movieName.getText().toString());
                return false;
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) recyclerAdapter.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}