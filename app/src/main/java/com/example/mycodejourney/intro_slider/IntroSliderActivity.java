package com.example.mycodejourney.intro_slider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mycodejourney.MainActivity;
import com.example.mycodejourney.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStarted;
    int position = 0;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefData()) {

            Intent authActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(authActivity);
            finish();
            
        }

        setContentView(R.layout.activity_intro_slider);

        getSupportActionBar().hide();

        btnNext = findViewById(R.id.btn_next_intro);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_anim);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("#100DaysOfCode", "Code minimum an hour every day for the next 100 days.\n" +
                "Tweet your progress every day with the #100DaysOfCode hashtag.", R.drawable.intro_one));
        mList.add(new ScreenItem("Publicly commit to the challenge", "Tweet to commit to the challenge!\n" +
                "Plan: Formulate what you want to work on during the challenge. It might be - learning a framework, or improving your skill level with a particular technology or a programming language. Don’t spend too much time planning, but having a plan like this will help you on your path.", R.drawable.intro_four));
        mList.add(new ScreenItem("Encourage others", "By giving them props when they are posting updates on their progress, supporting them when things get difficult. \n Thus we will grow a community that is helpful and effective, which will lead to a higher success rate for each person involved.", R.drawable.intro_five));

        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);

                }

                if (position == mList.size() - 1) {
                    
                    loadLastScreen();

                }

            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    loadLastScreen();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    btnNext.setVisibility(View.VISIBLE);
                    btnGetStarted.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent authActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(authActivity);

                savePrefsData();
                finish();

            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActOpenedBefore = preferences.getBoolean("isIntroOpened", false);
        return isIntroActOpenedBefore;
    }

    private void savePrefsData() {

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();

    }

    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        btnGetStarted.setAnimation(btnAnim);

    }
}
