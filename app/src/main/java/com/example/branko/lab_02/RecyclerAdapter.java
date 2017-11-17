package com.example.branko.lab_02;

/**
 * Created by Branko on 12.11.2017.
 */

import android.view.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Branko on 22.10.2017.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    Context context;
    private List<Movie> data = new ArrayList<>();

    public RecyclerAdapter(List<Movie> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(itemView, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Movie movie = data.get(position);
        holder.movieName.setText(movie.Title);
        holder.movieYear.setText(movie.Year);

        Picasso.with(context)
                .load(movie.Poster)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Movie> data){
        this.data = data;
    }

    public List<Movie> getData() {
        return data;
    }

    public void removeItem(String movieName) {
        int index = -1;

        for(int i = 0; i < data.size(); ++i) {
            if(movieName.equals(data.get(i).Title)) {
                index = i;
                break;
            }
        }

        data.remove(index);
        notifyDataSetChanged();
    }
}