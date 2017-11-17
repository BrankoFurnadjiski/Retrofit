package com.example.branko.lab_02;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MenuItem searchAction;
    private boolean isSearchOpened = false;
    private EditText editSearch;

    private List<Movie> data;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecycler();
    }

    private void initRecycler(){
        data = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.movies);
        recyclerAdapter = new RecyclerAdapter(data, getApplicationContext());
        layoutManager = new LinearLayoutManager(this.getApplicationContext());

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleMenuSearch() {
        ActionBar actionBar = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            actionBar.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            actionBar.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            //add the search icon in the action bar
            searchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));

            isSearchOpened = false;
        } else { //open the search entry

            actionBar.setDisplayShowCustomEnabled(true); //enable it to display a custom view in the action bar.
            actionBar.setCustomView(R.layout.search_bar);//add the custom view
            actionBar.setDisplayShowTitleEnabled(false); //hide the title

            editSearch = actionBar.getCustomView().findViewById(R.id.editSearch); //the text editor


            //this is a listener to do a search when the user clicks on search button
            editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        EditText movieTitle = (EditText) findViewById(R.id.editSearch);
                        doSearch(movieTitle.getText().toString());
                        return true;
                    }
                    return false;
                }
            });

            editSearch.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            searchAction.setIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
            isSearchOpened = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch(String movieName){
        MovieLoadingTask asyncTask = new MovieLoadingTask(recyclerAdapter, movieName);
        asyncTask.execute();
    }
}