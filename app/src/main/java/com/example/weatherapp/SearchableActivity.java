package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchableActivity extends AppCompatActivity {
    final String serverURL = "http://weather-search-mzp0625.us-west-1.elasticbeanstalk.com";
    JSONObject darkSky;

    TextView currentLocation;
    TextView currentSummary;
    TextView currentTemperature;
    TextView currentHumidity;
    TextView currentVisibility;
    TextView currentWindSpeed;
    TextView currentPressure;
    ImageView currentIcon;

    TextView weekDate1;
    TextView weekDate2;
    TextView weekDate3;
    TextView weekDate4;
    TextView weekDate5;
    TextView weekDate6;
    TextView weekDate7;
    TextView weekDate8;

    ImageView weeklyIcon1;
    ImageView weeklyIcon2;
    ImageView weeklyIcon3;
    ImageView weeklyIcon4;
    ImageView weeklyIcon5;
    ImageView weeklyIcon6;
    ImageView weeklyIcon7;
    ImageView weeklyIcon8;

    TextView weeklyLow1;
    TextView weeklyLow2;
    TextView weeklyLow3;
    TextView weeklyLow4;
    TextView weeklyLow5;
    TextView weeklyLow6;
    TextView weeklyLow7;
    TextView weeklyLow8;

    TextView weeklyHigh1;
    TextView weeklyHigh2;
    TextView weeklyHigh3;
    TextView weeklyHigh4;
    TextView weeklyHigh5;
    TextView weeklyHigh6;
    TextView weeklyHigh7;
    TextView weeklyHigh8;
    private String locationSummary;
    private JSONObject currently;

    public ImageView fav_icon;


    String cityName;
    String stateName;
    String countryName;

    String city_name_to_fragment;

    public LinearLayout progress_layout;
    public RelativeLayout main_layout;


    String queryURL = "https://api.opencagedata.com/geocode/v1/json?";
    final String OpenCageKey = "a9942479691d46a1a6b8a531b394a7d6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        progress_layout = findViewById(R.id.progress_layout);
        main_layout = findViewById(R.id.main_layout);

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));



        currentLocation = findViewById(R.id.currentLocation);
        currentSummary = findViewById(R.id.currentSummary);
        currentTemperature = findViewById(R.id.currentTemperature);
        currentHumidity = findViewById(R.id.currentHumidity);
        currentVisibility = findViewById(R.id.currentVisibility);
        currentWindSpeed = findViewById(R.id.currentWindSpeed);
        currentPressure = findViewById(R.id.currentPressure);
        currentIcon = findViewById(R.id.currentIcon);

        weekDate1 = findViewById(R.id.weeklyDate1);
        weekDate2 = findViewById(R.id.weeklyDate2);
        weekDate3 = findViewById(R.id.weeklyDate3);
        weekDate4 = findViewById(R.id.weeklyDate4);
        weekDate5 = findViewById(R.id.weeklyDate5);
        weekDate6 = findViewById(R.id.weeklyDate6);
        weekDate7 = findViewById(R.id.weeklyDate7);
        weekDate8 = findViewById(R.id.weeklyDate8);

        weeklyIcon1 = findViewById(R.id.weeklyIcon1);
        weeklyIcon2 = findViewById(R.id.weeklyIcon2);
        weeklyIcon3 = findViewById(R.id.weeklyIcon3);
        weeklyIcon4 = findViewById(R.id.weeklyIcon4);
        weeklyIcon5 = findViewById(R.id.weeklyIcon5);
        weeklyIcon6 = findViewById(R.id.weeklyIcon6);
        weeklyIcon7 = findViewById(R.id.weeklyIcon7);
        weeklyIcon8 = findViewById(R.id.weeklyIcon8);

        weeklyLow1 = findViewById(R.id.weeklyLow1);
        weeklyLow2 = findViewById(R.id.weeklyLow2);
        weeklyLow3 = findViewById(R.id.weeklyLow3);
        weeklyLow4 = findViewById(R.id.weeklyLow4);
        weeklyLow5 = findViewById(R.id.weeklyLow5);
        weeklyLow6 = findViewById(R.id.weeklyLow6);
        weeklyLow8 = findViewById(R.id.weeklyLow8);
        weeklyLow7 = findViewById(R.id.weeklyLow7);

        weeklyHigh1 = findViewById(R.id.weeklyHigh1);
        weeklyHigh2 = findViewById(R.id.weeklyHigh2);
        weeklyHigh3 = findViewById(R.id.weeklyHigh3);
        weeklyHigh4 = findViewById(R.id.weeklyHigh4);
        weeklyHigh5 = findViewById(R.id.weeklyHigh5);
        weeklyHigh6 = findViewById(R.id.weeklyHigh6);
        weeklyHigh7 = findViewById(R.id.weeklyHigh7);
        weeklyHigh8 = findViewById(R.id.weeklyHigh8);
        CardView cardView1 = findViewById(R.id.summaryCard1);

        fav_icon = findViewById(R.id.fav_button);




        FloatingActionButton fab = findViewById(R.id.fav_button);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                toggleFavorite();
            }
        });

        Intent intent = getIntent();
        final String query_string = intent.getExtras().getString("query_string");

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = queryURL + "key=" + OpenCageKey + "&q=" + query_string;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                JSONParser parser = new JSONParser();
                try{
                    JSONObject jsonObject = (JSONObject) parser.parse(response);
                    JSONArray results = (JSONArray) jsonObject.get("results");
                    JSONObject result = (JSONObject) parser.parse(results.get(0).toString());
                    JSONObject geometry = (JSONObject) result.get("geometry");
                    Double latitude = (Double) geometry.get("lat");
                    Double longitude = (Double) geometry.get("lng");

                    Geocoder geocoder = new Geocoder(SearchableActivity.this, Locale.getDefault());
                    try{
                        List<Address> addresses = geocoder.getFromLocation( latitude, longitude,1);

                        cityName = addresses.get(0).getLocality()==null? "" : addresses.get(0).getLocality() + ", ";
                        stateName = addresses.get(0).getAdminArea()==null? "" : addresses.get(0).getAdminArea() + ", ";
                        countryName = addresses.get(0).getCountryName()==null? "" :addresses.get(0).getCountryName();
                        System.out.println("city: " +  cityName + "state" + stateName + "country" + countryName);

                        locationSummary = cityName + stateName + countryName;
                        if(locationSummary.trim().indexOf(",") == locationSummary.trim().length()-1){
                            locationSummary = query_string;
                        }
                        currentLocation.setText( locationSummary);
                        getSupportActionBar().setTitle(locationSummary);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                        Log.d("location summary", locationSummary);
                        if (sharedPreferences.contains(locationSummary)){
                            fav_icon.setImageResource(R.drawable.ic_map_marker_minus);
                        }
                        else {
                            fav_icon.setImageResource(R.drawable.ic_map_marker_plus);
                        }


                    }catch(Exception error){
                        error.printStackTrace();
                    }

                    requestWeatherInfoFromLatLong(latitude, longitude);

//                    currentSummary.setText(latitude + " , " + longitude);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);

        cardView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SearchableActivity.this, DetailActivity.class);
                intent.putExtra("weather_json", darkSky.toJSONString());
                intent.putExtra("title_location", locationSummary);
                intent.putExtra("cityName", locationSummary.substring(0, locationSummary.indexOf(",")-1));

                startActivity(intent);
            }
        });




    }


    public void requestWeatherInfoFromLatLong(double latitude, double longitude){

        RequestQueue queue = Volley.newRequestQueue(SearchableActivity.this);
        final String url = serverURL + '/' + latitude + '/' + longitude;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONParser parser = new JSONParser();
                try{
                    JSONObject jsonObject = (JSONObject) parser.parse(response);
                    darkSky = jsonObject;
                    currently = (JSONObject) jsonObject.get("currently");
                    String summary = (String) currently.get("summary");
                    Double temperature = ((Number) currently.get("temperature")).doubleValue();
                    Double humidity = ((Number) currently.get("humidity")).doubleValue();
                    Double pressure = ((Number) currently.get("pressure")).doubleValue();
                    Double windSpeed = ((Number) currently.get("windSpeed")).doubleValue();
                    Double visibility = ((Number) currently.get("visibility")).doubleValue();

                    temperature = Math.round(temperature*100.00)/100.00;
                    humidity = Math.round(humidity*100.00)/100.00;
                    pressure = Math.round(pressure*100.00)/100.00;
                    windSpeed = Math.round(windSpeed*100.00)/100.00;
                    visibility = Math.round(visibility*100.00)/100.00;
                    String icon = (String) currently.get("icon");

                    currentSummary.setText(summary);
                    currentTemperature.setText(Math.round(temperature + 0.5) +  "\u00B0"  +  "F");
                    currentHumidity.setText(humidity + " %");
                    currentPressure.setText(pressure + " mb");
                    currentVisibility.setText(visibility + " km");
                    currentWindSpeed.setText(windSpeed + " mph");

                    if (icon.equals("clear-day")){
                        currentIcon.setImageResource(R.drawable.ic_weather_sunny);
                    }
                    else if (icon.equals("clear-night")){
                        currentIcon.setImageResource(R.drawable.ic_weather_night);
                    }
                    else if (icon.equals("rain")){
                        currentIcon.setImageResource(R.drawable.ic_weather_rainy);
                    }
                    else if (icon.equals("sleet")){
                        currentIcon.setImageResource(R.drawable.ic_weather_snowy_rainy);
                    }
                    else if (icon.equals("snow")){
                        currentIcon.setImageResource(R.drawable.ic_weather_snowy);
                    }
                    else if (icon.equals("wind")){
                        currentIcon.setImageResource(R.drawable.ic_weather_windy_variant);
                    }
                    else if (icon.equals("fog")){
                        currentIcon.setImageResource(R.drawable.ic_weather_fog);
                    }
                    else if (icon.equals("cloudy")){
                        currentIcon.setImageResource(R.drawable.ic_weather_cloudy);
                    }
                    else if (icon.equals("partly-cloudy-night")){
                        currentIcon.setImageResource(R.drawable.ic_weather_night_partly_cloudy);
                    }
                    else if (icon.equals("partly-cloudy-day")){
                        currentIcon.setImageResource(R.drawable.ic_weather_partly_cloudy);
                    }

                    // Weekly scroll view
                    JSONObject daily = (JSONObject) jsonObject.get("daily");
                    JSONArray data = (JSONArray) daily.get("data");

                    // Daily data

                    JSONObject daily_1 = (JSONObject) parser.parse(data.get(0).toString());
                    JSONObject daily_2 = (JSONObject) parser.parse(data.get(1).toString());
                    JSONObject daily_3 = (JSONObject) parser.parse(data.get(2).toString());
                    JSONObject daily_4 = (JSONObject) parser.parse(data.get(3).toString());
                    JSONObject daily_5 = (JSONObject) parser.parse(data.get(4).toString());
                    JSONObject daily_6 = (JSONObject) parser.parse(data.get(5).toString());
                    JSONObject daily_7 = (JSONObject) parser.parse(data.get(6).toString());
                    JSONObject daily_8 = (JSONObject) parser.parse(data.get(7).toString());


                    ImageView[] weeklyIcons = new ImageView[]{weeklyIcon1, weeklyIcon2, weeklyIcon3, weeklyIcon4, weeklyIcon5, weeklyIcon6, weeklyIcon7, weeklyIcon8};
                    TextView[] weekDates  = new TextView[]{weekDate1, weekDate2, weekDate3, weekDate4, weekDate5, weekDate6, weekDate7, weekDate8};
                    TextView[] weekLows = new TextView[]{weeklyLow1, weeklyLow2, weeklyLow3, weeklyLow4, weeklyLow5, weeklyLow6, weeklyLow7, weeklyLow8};
                    TextView[] weekHighs = new TextView[]{weeklyHigh1, weeklyHigh2, weeklyHigh3, weeklyHigh4, weeklyHigh5, weeklyHigh6, weeklyHigh7, weeklyHigh8};

                    JSONObject[] daily_data = new JSONObject[]{daily_1, daily_2, daily_3, daily_4, daily_5, daily_6, daily_7, daily_8};
                    for(int i = 0; i < 8; i++){
                        long time = ((Number) daily_data[i].get("time")).longValue();
                        int lowTemp = (int) (((Number) daily_data[i].get("temperatureLow")).doubleValue() + 0.5);
                        int highTemp = (int) (((Number) daily_data[i].get("temperatureHigh")).doubleValue() + 0.5);
                        String dailyIcon = (String) daily_data[i].get("icon");

                        // Set date
                        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date(time*1000));
                        weekDates[i].setText(date);

                        // Set icon
                        if (dailyIcon.equals("clear-day")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_sunny);
                        }
                        else if (dailyIcon.equals("clear-night")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_night);
                        }
                        else if (dailyIcon.equals("rain")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_rainy);
                        }
                        else if (dailyIcon.equals("sleet")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_snowy_rainy);
                        }
                        else if (dailyIcon.equals("snow")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_snowy);
                        }
                        else if (dailyIcon.equals("wind")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_windy_variant);
                        }
                        else if (dailyIcon.equals("fog")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_fog);
                        }
                        else if (dailyIcon.equals("cloudy")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_cloudy);
                        }
                        else if (dailyIcon.equals("partly-cloudy-night")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_night_partly_cloudy);
                        }
                        else if (dailyIcon.equals("partly-cloudy-day")){
                            weeklyIcons[i].setImageResource(R.drawable.ic_weather_partly_cloudy);
                        }
                        // Set low temperature
                        weekLows[i].setText(Integer.toString(lowTemp));

                        // Set high temperature
                        weekHighs[i].setText("     " + Integer.toString(highTemp) + "     ");
                    }

                    progress_layout.setVisibility(View.INVISIBLE);
                    main_layout.setVisibility(View.VISIBLE);

                } catch(Exception e){

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchable_menu, menu);

//        MenuItem item = menu.findItem(R.id.search_city);
//
//
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                new AlertDialog.Builder(MainActivity.this).setMessage(query).create().show();
//                Intent intent = new Intent(SearchableActivity.this, SearchableActivity.class);
//                intent.putExtra("query_string", query);
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // adapter.getFilter().filter(newText);
//                return false;
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    public void toggleFavorite(){
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.contains(locationSummary)){
            editor.remove(locationSummary);
            editor.commit();
            fav_icon.setImageResource(R.drawable.ic_map_marker_plus);
            Toast toast = Toast.makeText(getApplicationContext(), locationSummary + " was removed from favorites.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            editor.putString(locationSummary, darkSky.toJSONString());
            editor.apply();
            fav_icon.setImageResource(R.drawable.ic_map_marker_minus);
            Toast toast = Toast.makeText(getApplicationContext(), locationSummary + " was added to favorites.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
