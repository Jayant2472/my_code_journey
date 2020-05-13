package com.example.mycodejourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mycodejourney.intro_slider.IntroSliderActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, IntroSliderActivity.class);
        startActivity(intent);
        finish();

    }
}
