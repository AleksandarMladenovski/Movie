package com.design.movie.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.design.movie.R;

import java.util.ArrayList;

public class MovieDetail extends AppCompatActivity {
    private String title;
    private String imageUrl;
    private String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initAttributes();
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        initUpButton();

    }

    void initUpButton(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        }
    }
     void initAttributes(){
        title="Something Went Wrong";
        imageUrl="http://www.clker.com/cliparts/2/l/m/p/B/b/error-md.png";
        description="Something Went Wrong";
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra(getString(R.string.bundle_extra_key));
        if(bundle!=null){
            title=bundle.getString(getString(R.string.movie_name_key));
            imageUrl=bundle.getString(getString(R.string.movie_image_url_key));
            description=bundle.getString(getString(R.string.movie_description_key));
        }
         TextView movieName=findViewById(R.id.textViewTitle);
         movieName.setText(title);
         movieName.setPaintFlags(movieName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
         TextView movieDescription=findViewById(R.id.textViewDescription);
         movieDescription.setText(description);

         ImageView movieImage=findViewById(R.id.buttonImageUrl);
         Glide.with(this)
                 .load(imageUrl)
                 .centerCrop()
                 .into(movieImage);
    }

}
