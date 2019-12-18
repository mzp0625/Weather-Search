package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapp.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DetailActivity extends AppCompatActivity {
    public JSONObject currently;
    public JSONObject daily;
    public String cityName;
    public TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#7657ad"));

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        String darkSky = intent.getExtras().getString("weather_json");
        String locationSummary = intent.getExtras().getString("title_location");
        cityName = intent.getExtras().getString("cityName");
        JSONParser parser = new JSONParser();
        try{
            currently = (JSONObject) ((JSONObject) parser.parse(darkSky)).get("currently");
            daily = (JSONObject) ((JSONObject) parser.parse(darkSky)).get("daily");


        } catch (Exception e){
            getSupportActionBar().setTitle(e.toString());
        }


        getSupportActionBar().setTitle(locationSummary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.tweet:
                newTweet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newTweet(){
        String temperature = String.valueOf(Math.round((Double) currently.get("temperature")));
        String summary = (String) currently.get("summary");
        String url = "https://twitter.com/intent/tweet?text=Check out " + cityName + "'s weather! It is " + temperature + '\u2109' + "!";
        url = url + "The weather conditions are " + summary + ".&" + "hashtags=CSCI571WeatherSearch";

        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        startActivity(viewIntent);
    }
}