package com.design.movie.Fragment;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.design.movie.Activity.MainActivity;
import com.design.movie.Adapter.AdapterMovieList;
import com.design.movie.Listener.CustomListener;
import com.design.movie.Model.Movie;
import com.design.movie.Model.MovieRepository;
import com.design.movie.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
    AdapterMovieList adapterMovieList;
   CustomListener listener;
    SearchView searchView;
    public TabFragment() {
        // Required empty public constructor
    }
    public TabFragment(CustomListener listener){
        this.listener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab_movie, container, false);
        String tabGenre="ACTION";
        if (getArguments() != null) {
            tabGenre = getArguments().getString("TabName");
        }

        ArrayList<Movie>movies= MovieRepository.getGenreMovie(tabGenre);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMovieList=new AdapterMovieList(movies,getContext(),listener);
        recyclerView.setAdapter(adapterMovieList);
        setHasOptionsMenu(true);
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)  {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_detail_activity, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getString(R.string.menu_item_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            });

            int autoCompleteTextViewID = getResources().getIdentifier("android:id/search_src_text", null, null);
            final AutoCompleteTextView searchAutoComplete = (AutoCompleteTextView) searchView.findViewById(autoCompleteTextViewID);
            //searchAutoComplete.setBackgroundColor(Color.BLUE);
            //searchAutoComplete.setTextColor(getResources().getColor(R.color.colorToolbarTextWhite));
            searchAutoComplete.setDropDownBackgroundResource(android.R.color.white);

            // Create a new ArrayAdapter and add data to search auto complete object.

            ArrayAdapter<String> movieAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,MovieRepository.getAllMovieNames());
            searchAutoComplete.setAdapter(movieAdapter);
            searchAutoComplete.setThreshold(1);

            // Listen to search view item on click event.
            searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                    String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                    Movie clickedMovie=MovieRepository.findMovieByName(queryString);
                    Bundle bundle=createMovieDetailBundle(clickedMovie);
                    listener.openDetailMovie(bundle);
                }
            });

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_search:
                Log.i("olog","log" );
                return false;
            case R.id.itemSortAZ:
                adapterMovieList.sortByAZ();
                Toast.makeText(getContext(),"Sorted By: A-Z",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public Bundle createMovieDetailBundle(Movie movie){
        Bundle bundle=new Bundle();
        bundle.putString(getString(R.string.movie_name_key),movie.getName());
        bundle.putString(getString(R.string.movie_image_url_key),movie.getImageUrl());
        bundle.putString(getString(R.string.movie_description_key),movie.getDescription());
        return bundle;
    }

    }