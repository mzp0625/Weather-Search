package com.example.weatherapp.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.DetailActivity;
import com.example.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Main_frag extends Fragment {
    private FusedLocationProviderClient client;


    public String cityName;
    public String stateName;
    public String countryName;
    public String city_name_to_fragment;
    public String locationSummary;
    public JSONObject darkSky;

    public TextView currentLocation;
    public TextView currentSummary;
    public TextView currentTemperature;
    public TextView currentHumidity;
    public TextView currentVisibility;
    public TextView currentWindSpeed;
    public TextView currentPressure;

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

    private ImageView weeklyIcon1;
    private ImageView weeklyIcon2;
    private ImageView weeklyIcon3;
    private ImageView weeklyIcon4;
    private ImageView weeklyIcon5;
    private ImageView weeklyIcon6;
    private ImageView weeklyIcon7;
    private ImageView weeklyIcon8;


    public ImageView currentIcon;

    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    final String serverURL = "http://weather-search-mzp0625.us-west-1.elasticbeanstalk.com";

    FloatingActionButton fab;

    JSONParser parser = new JSONParser();



    public LinearLayout progress_layout;
    public RelativeLayout main_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_typical, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progress_layout = getView().findViewById(R.id.progress_layout);
        main_layout = getView().findViewById(R.id.main_layout);

        currentLocation = getView().findViewById(R.id.currentLocation);getActivity();
        currentSummary = getView().findViewById(R.id.currentSummary);
        currentTemperature = getView().findViewById(R.id.currentTemperature);
        currentHumidity = getView().findViewById(R.id.currentHumidity);
        currentVisibility = getView().findViewById(R.id.currentVisibility);
        currentWindSpeed = getView().findViewById(R.id.currentWindSpeed);
        currentPressure = getView().findViewById(R.id.currentPressure);

        weekDate1 = getView().findViewById(R.id.weeklyDate1);
        weekDate2 = getView().findViewById(R.id.weeklyDate2);
        weekDate3 = getView().findViewById(R.id.weeklyDate3);
        weekDate4 = getView().findViewById(R.id.weeklyDate4);
        weekDate5 = getView().findViewById(R.id.weeklyDate5);
        weekDate6 = getView().findViewById(R.id.weeklyDate6);
        weekDate7 = getView().findViewById(R.id.weeklyDate7);
        weekDate8 = getView().findViewById(R.id.weeklyDate8);

        weeklyIcon1 = getView().findViewById(R.id.weeklyIcon1);
        weeklyIcon2 = getView().findViewById(R.id.weeklyIcon2);
        weeklyIcon3 = getView().findViewById(R.id.weeklyIcon3);
        weeklyIcon4 = getView().findViewById(R.id.weeklyIcon4);
        weeklyIcon5 = getView().findViewById(R.id.weeklyIcon5);
        weeklyIcon6 = getView().findViewById(R.id.weeklyIcon6);
        weeklyIcon7 = getView().findViewById(R.id.weeklyIcon7);
        weeklyIcon8 = getView().findViewById(R.id.weeklyIcon8);

        weeklyLow1 = getView().findViewById(R.id.weeklyLow1);
        weeklyLow2 = getView().findViewById(R.id.weeklyLow2);
        weeklyLow3 = getView().findViewById(R.id.weeklyLow3);
        weeklyLow4 = getView().findViewById(R.id.weeklyLow4);
        weeklyLow5 = getView().findViewById(R.id.weeklyLow5);
        weeklyLow6 = getView().findViewById(R.id.weeklyLow6);
        weeklyLow7 = getView().findViewById(R.id.weeklyLow7);
        weeklyLow8 = getView().findViewById(R.id.weeklyLow8);

        weeklyHigh1 = getView().findViewById(R.id.weeklyHigh1);
        weeklyHigh2 = getView().findViewById(R.id.weeklyHigh2);
        weeklyHigh3 = getView().findViewById(R.id.weeklyHigh3);
        weeklyHigh4 = getView().findViewById(R.id.weeklyHigh4);
        weeklyHigh5 = getView().findViewById(R.id.weeklyHigh5);
        weeklyHigh6 = getView().findViewById(R.id.weeklyHigh6);
        weeklyHigh7 = getView().findViewById(R.id.weeklyHigh7);
        weeklyHigh8 = getView().findViewById(R.id.weeklyHigh8);

        currentIcon = getView().findViewById(R.id.currentIcon);

        fab = getView().findViewById(R.id.fav_button);
        ((ViewManager) fab.getParent()).removeView(fab);


        showSummary();


        CardView cardView1 = getView().findViewById(R.id.summaryCard1);

        cardView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("weather_json", darkSky.toJSONString());
                intent.putExtra("title_location", locationSummary);
                intent.putExtra("cityName", city_name_to_fragment);
                startActivity(intent);
            }
        });



    }

    private void showSummary(){


        client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null){

                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    requestWeatherInfoFromLatLong(latitude, longitude);

                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
                    }
                }
            }
        });

    }

    public void requestWeatherInfoFromLatLong(double latitude, double longitude){

        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                    currentTemperature.setText(String.valueOf(Math.round(temperature + 0.5)) +  "\u00B0"  +  "F");
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
                        weekHighs[i].setText(Integer.toString((highTemp)));
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

}