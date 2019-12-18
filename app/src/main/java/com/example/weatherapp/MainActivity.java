package com.example.weatherapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.fragments.Favorite_frag;
import com.example.weatherapp.fragments.Main_frag;
import com.example.weatherapp.ui.main.SlidePageAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private FusedLocationProviderClient client;
    private TextView currentLocation;
    private TextView currentSummary;
    private TextView currentTemperature;
    private TextView currentHumidity;
    private TextView currentVisibility;
    private TextView currentWindSpeed;
    private TextView currentPressure;

    private TextView weekDate1;
    private TextView weekDate2;
    private TextView weekDate3;
    private TextView weekDate4;
    private TextView weekDate5;
    private TextView weekDate6;
    private TextView weekDate7;
    private TextView weekDate8;

    private TextView weeklyLow1;
    private TextView weeklyLow2;
    private TextView weeklyLow3;
    private TextView weeklyLow4;
    private TextView weeklyLow5;
    private TextView weeklyLow6;
    private TextView weeklyLow7;
    private TextView weeklyLow8;

    private TextView weeklyHigh1;
    private TextView weeklyHigh2;
    private TextView weeklyHigh3;
    private TextView weeklyHigh4;
    private TextView weeklyHigh5;
    private TextView weeklyHigh6;
    private TextView weeklyHigh7;
    private TextView weeklyHigh8;

    private ImageView currentIcon;

    private ImageView weeklyIcon1;
    private ImageView weeklyIcon2;
    private ImageView weeklyIcon3;
    private ImageView weeklyIcon4;
    private ImageView weeklyIcon5;
    private ImageView weeklyIcon6;
    private ImageView weeklyIcon7;
    private ImageView weeklyIcon8;

    private ImageView searchIcon;

    private Toolbar toolbar;

    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    final String DARKSKY_KEY = "2ed59c1609f6584f55d88a6ee9a82ca6";
    private String cityName;
    private String stateName;
    private String countryName;
    private JSONObject darkSky;

    final String serverURL = "http://weather-search-mzp0625.us-west-1.elasticbeanstalk.com";

    private String locationSummary;
    private String city_name_to_fragment;
    ListView search_city;
    ArrayAdapter<String> adapter;
    JSONParser parser = new JSONParser();

    public ViewPager pager;

    public SlidePageAdapter pagerAdapter;

    DotsIndicator  dotsIndicator;

    public LinearLayout progress_layout;
    public RelativeLayout main_layout;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        Log.d("shared pref", sharedPreferences.toString());
        Map<String, ?>keys = sharedPreferences.getAll();



        if(keys.size() !=0 ){

            setContentView(R.layout.activity_main_fragment_launch);

            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

            List<Fragment> list = new ArrayList<>();
            list.add(new Main_frag());
            for(Map.Entry<String,?> entry: keys.entrySet()){
                System.out.println("map key: " + entry.getKey() + "map value: " + entry.getValue());
                String location_summary = entry.getKey();
                String dark_sky = entry.getValue().toString();
                list.add(new Favorite_frag(location_summary, dark_sky, list.size()));
            }

            pager = findViewById(R.id.pager);
            pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), list);
            pager.setAdapter(pagerAdapter);

            dotsIndicator= findViewById(R.id.dots_indicator);
            dotsIndicator.setViewPager(pager);

        } else{
            setContentView(R.layout.activity_main);

            progress_layout = findViewById(R.id.progress_layout);
            main_layout = findViewById(R.id.main_layout);

            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));

            CardView cardView1 = findViewById(R.id.summaryCard1);

//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("WeatherApp");

//        search_city = (ListView) findViewById(R.id.search_city);
//        ArrayList<String> arrayCity = new ArrayList<>();
//        arrayCity.addAll(Arrays.asList(getResources().getStringArray(R.array.my_cities)));
//
//        adapter = new ArrayAdapter<String>(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                arrayCity
//        );
//
//        search_city.setAdapter(adapter);
//        ViewCompat.setTranslationZ(search_city, 20);



            currentLocation = findViewById(R.id.currentLocation);
            currentSummary = findViewById(R.id.currentSummary);
            currentTemperature = findViewById(R.id.currentTemperature);
            currentHumidity = findViewById(R.id.currentHumidity);
            currentVisibility = findViewById(R.id.currentVisibility);
            currentWindSpeed = findViewById(R.id.currentWindSpeed);
            currentPressure = findViewById(R.id.currentPressure);

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
            weeklyLow7 = findViewById(R.id.weeklyLow7);
            weeklyLow8 = findViewById(R.id.weeklyLow8);

            weeklyHigh1 = findViewById(R.id.weeklyHigh1);
            weeklyHigh2 = findViewById(R.id.weeklyHigh2);
            weeklyHigh3 = findViewById(R.id.weeklyHigh3);
            weeklyHigh4 = findViewById(R.id.weeklyHigh4);
            weeklyHigh5 = findViewById(R.id.weeklyHigh5);
            weeklyHigh6 = findViewById(R.id.weeklyHigh6);
            weeklyHigh7 = findViewById(R.id.weeklyHigh7);
            weeklyHigh8 = findViewById(R.id.weeklyHigh8);

            currentIcon = findViewById(R.id.currentIcon);


            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission is needed")
                        .setMessage("Permission is needed to locate user's current location. Please revise your permission settings for this application.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        })
                        .create().show();

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                // Permission has already been granted
                showSummary();

            }

            cardView1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("weather_json", darkSky.toJSONString());
                    intent.putExtra("title_location", locationSummary);
                    intent.putExtra("cityName", city_name_to_fragment);
                    startActivity(intent);
                }
            });

        }
    }




    RequestQueue autoRequestQueue;
    ArrayList<String> suggestions = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        autoRequestQueue = Volley.newRequestQueue(this);

        MenuItem item = menu.findItem(R.id.search_city);

//        MenuItem auto_item = menu.findItem(R.id.auto_complete);
//        AutoCompleteTextView actv = (AutoCompleteTextView) auto_item.getActionView();
//        actv.setThreshold(0);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.list_content, COUNTRIES);
//        actv.setAdapter(adapter);
//        actv.setBackgroundColor(Color.WHITE);


        final SearchView searchView = (SearchView) item.getActionView();

        searchView.setQueryHint("Search...");
        searchView.setBackgroundColor(Color.parseColor("#333333"));





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                new AlertDialog.Builder(MainActivity.this).setMessage(query).create().show();
                Intent intent = new Intent(MainActivity.this, SearchableActivity.class);
                intent.putExtra("query_string", query);
                Log.d("query: ", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d("new text", newText);

                String url = serverURL + "/" + newText;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<org.json.JSONObject>() {
                    @Override
                    public void onResponse(org.json.JSONObject response) {
                        try{
                            SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

                            searchAutoComplete.setOnItemClickListener(
                                    new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String query = (String) parent.getItemAtPosition(position);
                                            searchView.setQuery(query, true);
                                        }
                                    }
                            );
                            searchAutoComplete.setThreshold(100);
                            searchAutoComplete.setDropDownBackgroundDrawable(new ColorDrawable(Color.WHITE));

                            suggestions = new ArrayList<>();


                            org.json.JSONArray predictions = (org.json.JSONArray) response.get("predictions");
                            for (int i = 0; i<predictions.length(); i++){
                                String description = (String) ((org.json.JSONObject) predictions.get(i)).get("description");
                                suggestions.add(description);
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_activated_1,suggestions);
                            Log.d("suggestions: ", suggestions.toString());

                            searchAutoComplete.setAdapter(dataAdapter);
                            searchAutoComplete.showDropDown();

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                autoRequestQueue.add(request);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }







    public void requestWeatherInfoFromLatLong(double latitude, double longitude){

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final String url = serverURL + '/' + latitude + '/' + longitude;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONParser parser = new JSONParser();
                try{



                    JSONObject jsonObject = (JSONObject) parser.parse(response);
                    darkSky = jsonObject;
                    JSONObject currently = (JSONObject) jsonObject.get("currently");
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
                    currentTemperature.setText( String.valueOf(Math.round(temperature + 0.5))+  "\u00B0"  +  "F");
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
                        weekHighs[i].setText("     "+ Integer.toString(highTemp) + "     ");
                    }

                    progress_layout.setVisibility(View.INVISIBLE);
                    main_layout.setVisibility(View.VISIBLE);

                } catch(Exception e){

                    currentSummary.setText(e.toString());
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                currentSummary.setText(error.toString());
            }
        });

        queue.add(stringRequest);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    finish();
                    startActivity(getIntent());
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    new AlertDialog.Builder(this)
                            .setTitle("Permission is needed")
                            .setMessage("Permission is needed to locate user's current location. Please revise your permission settings for this application.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                }
                            })
                            .create().show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }



    private void showSummary(){
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null){

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    requestWeatherInfoFromLatLong(latitude, longitude);

                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try{
                        List<Address> addresses = geocoder.getFromLocation( latitude, longitude,1);
                        cityName = addresses.get(0).getLocality()==null? "" : addresses.get(0).getLocality() + ", ";

                        System.out.println("city name is" + cityName);
                        stateName = addresses.get(0).getAdminArea()==null? "" : addresses.get(0).getAdminArea() + ", ";
                        countryName = addresses.get(0).getCountryName()==null? "" :addresses.get(0).getCountryName();
                        city_name_to_fragment = addresses.get(0).getLocality()==null?stateName:addresses.get(0).getLocality();
                        locationSummary = cityName + stateName + countryName;
                        currentLocation.setText(locationSummary);

                    }catch(Exception error){
                        error.printStackTrace();
                        Log.d("geocoder error: ", error.toString());
                    }
                }
            }
        });
    }

    public void getLatLng(){
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null){

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();}
                }
            });
    }

    public void reloadFrags(String location_summ){
        Toast toast = Toast.makeText(getApplicationContext(), location_summ + " was removed from favorites.", Toast.LENGTH_SHORT);
        toast.show();

        SharedPreferences sharedPreferences = getSharedPreferences("myKey",MODE_PRIVATE);
        Map<String, ?>keys = sharedPreferences.getAll();

        List<Fragment> list = new ArrayList<>();
        list.add(new Main_frag());
        for(Map.Entry<String,?> entry: keys.entrySet()){
            System.out.println("map key: " + entry.getKey() + "map value: " + entry.getValue());
            String location_summary = entry.getKey();
            String dark_sky = entry.getValue().toString();
            list.add(new Favorite_frag(location_summary, dark_sky, list.size()));
        }

        pagerAdapter = new SlidePageAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(pagerAdapter);
        dotsIndicator.setViewPager(pager);
    }
}


