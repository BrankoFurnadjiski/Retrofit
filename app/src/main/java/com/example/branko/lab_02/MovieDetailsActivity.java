package com.example.branko.lab_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Branko on 14.11.2017.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewTitle;
    private TextView textViewPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initActivity();
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewPlot = (TextView) findViewById(R.id.textViewPlot);

        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initActivity() {
        Intent intent = getIntent();
        String movieName = intent.getStringExtra(RecyclerViewHolder.EXTRA_MOVIE_TITLE);

        View view = findViewById(R.id.movieDetailsLayout);
        MovieDetailsLoadingTask movieDetailsLoadingTask = new MovieDetailsLoadingTask(view, movieName);
        movieDetailsLoadingTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                shareMovie();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareMovie() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, String.format("Check out this movie!\n\n\n%s\n\n%s",
                textViewTitle.getText().toString(), textViewPlot.getText().toString()));

        startActivity(Intent.createChooser(intent, "Share via..."));
    }
}
