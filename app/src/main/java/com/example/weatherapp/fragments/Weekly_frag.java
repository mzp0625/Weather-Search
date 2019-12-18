package com.example.weatherapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.DetailActivity;
import com.example.weatherapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Weekly_frag.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Weekly_frag#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Weekly_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public Weekly_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Weekly_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Weekly_frag newInstance(String param1, String param2) {
        Weekly_frag fragment = new Weekly_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly_frag, container, false);
    }




    TextView weeklySummary;
    ImageView weeklyIcon;
    LineChart lineChart;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        weeklySummary = getView().findViewById(R.id.weeklySummary);
        weeklyIcon = getView().findViewById(R.id.weeklyIcon);

        JSONObject daily = ((DetailActivity) getActivity()).daily;
        String icon = (String) daily.get("icon");
        String summary = (String) daily.get("summary");

        weeklySummary.setText(summary);
        if (icon.equals("clear-day")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_sunny);
        }
        else if (icon.equals("clear-night")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_night);
        }
        else if (icon.equals("rain")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_rainy);
        }
        else if (icon.equals("sleet")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_snowy_rainy);
        }
        else if (icon.equals("snow")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_snowy);
        }
        else if (icon.equals("wind")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_windy_variant);
        }
        else if (icon.equals("fog")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_fog);
        }
        else if (icon.equals("cloudy")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_cloudy);
        }
        else if (icon.equals("partly-cloudy-night")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_night_partly_cloudy);
        }
        else if (icon.equals("partly-cloudy-day")){
            weeklyIcon.setImageResource(R.drawable.ic_weather_partly_cloudy);
        }

        lineChart = (LineChart) getView().findViewById(R.id.lineChart);

        JSONArray data = (JSONArray) daily.get("data");
        JSONParser parser = new JSONParser();


        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> lowTEMP = new ArrayList<>();
        ArrayList<Entry> highTEMP = new ArrayList<>();
        int numDataPoints = 8;


        for (int i = 0; i<numDataPoints; i++){
            try{
                JSONObject daily_data = (JSONObject) parser.parse(data.get(i).toString());
                float temperatureHIGH = Float.parseFloat(String.valueOf(daily_data.get("temperatureHigh")));
                float temperatureLOW = Float.parseFloat(String.valueOf(daily_data.get("temperatureLow")));
                lowTEMP.add(new Entry(i, temperatureLOW));
                highTEMP.add(new Entry(i, temperatureHIGH));
                xAXES.add(i, String.valueOf(i));
            }catch (Exception e){
                Log.e("WeatherApp", "unexpected JSON exception", e);
            }


        }
        String[] xaxes = new String[xAXES.size()];
        for (int i = 0; i<xAXES.size(); i++){
            xaxes[i] = xAXES.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(highTEMP,"Maximum Temperature");
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setColor(Color.parseColor("#fcba03"));
        LineDataSet lineDataSet2 = new LineDataSet(lowTEMP,"Minimum Temperature");
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setColor(Color.parseColor("#7657ad"));

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);
        lineChart.setData(new LineData(lineDataSets));

        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(16f);
    }



//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
