package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread background =  new Thread(){
            public void run(){
                try{
                    sleep(2000);

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        background.start();


    }

}
