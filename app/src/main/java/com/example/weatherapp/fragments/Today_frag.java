package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.DetailActivity;
import com.example.weatherapp.R;

import org.json.simple.JSONObject;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link Today_frag.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link Today_frag#newInstance} factory method to
// * create an instance of this fragment.
// */
public class Today_frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView text1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public Today_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Today_frag newInstance(String param1, String param2) {
        Today_frag fragment = new Today_frag();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_today_frag, container, false);
    }

    private TextView windSpeed;
    private TextView pressure;
    private TextView precipitation;
    private TextView temperature;
    private TextView summary;
    private TextView humidity;
    private TextView visibility;
    private TextView cloudCover;
    private TextView ozone;
    private ImageView summaryIcon;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        JSONObject currently = ((DetailActivity) getActivity()).currently;

        windSpeed =  getView().findViewById(R.id.windSpeed);
        pressure =  getView().findViewById(R.id.pressure);
        precipitation =  getView().findViewById(R.id.precipitation);
        temperature =  getView().findViewById(R.id.temperature);
        summary = getView().findViewById(R.id.summary);
        humidity =  getView().findViewById(R.id.humidity);
        visibility =  getView().findViewById(R.id.visibility);
        cloudCover =  getView().findViewById(R.id.cloudCover);
        ozone =  getView().findViewById(R.id.ozone);

        summaryIcon = getView().findViewById(R.id.summaryIcon);

        String windSpeedVal = String.valueOf(currently.get("windSpeed"));
        String pressureVal = String.valueOf(currently.get("pressure"));
        String precipitationVal = String.valueOf(currently.get("precipIntensity"));
        String temperatureVal = String.valueOf(currently.get("temperature"));
        String summaryTxt = String.valueOf(currently.get("summary"));
        String icon = String.valueOf(currently.get("icon"));
        String humidityVal = String.valueOf(currently.get("humidity"));
        String visibilityVal = String.valueOf(currently.get("visibility"));
        String cloudCoverVal = String.valueOf(currently.get("cloudCover"));
        String ozoneVal = String.valueOf(currently.get("ozone"));

        windSpeed.setText(windSpeedVal + " mph");
        pressure.setText(pressureVal + " mb");
        precipitation.setText(precipitationVal + " mmph");
        temperature.setText(temperatureVal + '\u2109');
        summary.setText(summaryTxt);
        humidity.setText(humidityVal + "%");
        visibility.setText(visibilityVal + " km");
        cloudCover.setText(cloudCoverVal + "%");
        ozone.setText(ozoneVal + " DU");

        if (icon.equals("clear-day")){
            summaryIcon.setImageResource(R.drawable.ic_weather_sunny);
        }
        else if (icon.equals("clear-night")){
            summaryIcon.setImageResource(R.drawable.ic_weather_night);
        }
        else if (icon.equals("rain")){
            summaryIcon.setImageResource(R.drawable.ic_weather_rainy);
        }
        else if (icon.equals("sleet")){
            summaryIcon.setImageResource(R.drawable.ic_weather_snowy_rainy);
        }
        else if (icon.equals("snow")){
            summaryIcon.setImageResource(R.drawable.ic_weather_snowy);
        }
        else if (icon.equals("wind")){
            summaryIcon.setImageResource(R.drawable.ic_weather_windy_variant);
        }
        else if (icon.equals("fog")){
            summaryIcon.setImageResource(R.drawable.ic_weather_fog);
        }
        else if (icon.equals("cloudy")){
            summaryIcon.setImageResource(R.drawable.ic_weather_cloudy);
        }
        else if (icon.equals("partly-cloudy-night")){
            summaryIcon.setImageResource(R.drawable.ic_weather_night_partly_cloudy);
        }
        else if (icon.equals("partly-cloudy-day")){
            summaryIcon.setImageResource(R.drawable.ic_weather_partly_cloudy);
        }




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
