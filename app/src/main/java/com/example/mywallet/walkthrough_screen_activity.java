package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywallet.adapters.OnBoardingAdapter;

import java.util.ArrayList;
import java.util.List;

public class walkthrough_screen_activity extends AppCompatActivity {

    private OnBoardingAdapter adapter;
    private LinearLayout indicatorsContainer;
    private ViewPager2 onBoardingViewPager;
    private ImageView imageNext;
    private TextView textSkip;
    private Button buttonGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough_screen_activity);

        indicatorsContainer = findViewById(R.id.indicatorsContainer);

        onBoardingViewPager = findViewById(R.id.onBoardingViewPager);

        imageNext = findViewById(R.id.imageNext);

        textSkip = findViewById(R.id.textSkip);

        buttonGetStarted = findViewById(R.id.buttonGetStarted);

        setupOnboardingItems();

        onBoardingViewPager.setAdapter(adapter);

        setUpIndicators();

        setCurrentIndicator(0);


        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onBoardingViewPager.getCurrentItem() + 1 < adapter.getItemCount()){
                    onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
                } else {
                    finish();
                }
            }
        });

        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWallet();
            }
        });

    }


    private void openWallet(){
        Intent intent = new Intent(this,Wallet_Item.class);
        startActivity(intent);
    }


    private void setupOnboardingItems(){
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemOne = new OnBoardingItem();
        itemOne.setTitle("Hello");
        itemOne.setDescription("Welcome to My wallet!");

        OnBoardingItem itemTwo = new OnBoardingItem();
        itemTwo.setTitle("Create wallets");
        itemTwo.setDescription("");
        itemTwo.setImage(R.drawable.wallet_icon);

        onBoardingItems.add(itemOne);
        onBoardingItems.add(itemTwo);

        adapter = new OnBoardingAdapter(onBoardingItems);
    }


    private void setUpIndicators(){
        ImageView[] indicators = new ImageView[adapter.getItemCount()];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);

        for (int i=0;i<indicators.length;i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_inactive_background));
            indicators[i].setLayoutParams(layoutParams);

            indicatorsContainer.addView(indicators[i]);
        }

    }

    private void setCurrentIndicator(int position){
        int childCount = indicatorsContainer.getChildCount();

        for (int i =0;i<childCount;i++){

            ImageView imageView = (ImageView) indicatorsContainer.getChildAt(i);

            if (i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_active_background));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_inactive_background));
            }

        }


    }
}