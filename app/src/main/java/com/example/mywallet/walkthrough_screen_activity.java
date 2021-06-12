package com.example.mywallet;

import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.mywallet.adapters.OnBoardingAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class walkthrough_screen_activity extends AppCompatActivity {

    private OnBoardingAdapter adapter;
    private LinearLayout indicatorsContainer;
    private ViewPager2 onBoardingViewPager;
    private ImageView imageNext;
    private TextView textSkip;
    private Button buttonGetStarted;
    private Integer currPosition;
    private GoogleSignInClient client;
    private GoogleSignInAccount currentGoogleAccount;
    private DriveServiceHelper mDriveServiceHelper;
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough_screen_activity);

        indicatorsContainer = findViewById(R.id.indicatorsContainer);

        onBoardingViewPager = findViewById(R.id.onBoardingViewPager);

        imageNext = findViewById(R.id.imageNext);

        textSkip = findViewById(R.id.textSkip);

        buttonGetStarted = findViewById(R.id.buttonGetStarted);

        setupOnboardingItems(null);



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
                    setAnswer();
                    finish();
                }
            }
        });

        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswer();
                finish();
            }
        });

        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (currPosition == 2){
                  openWallet();
              } if (currPosition == 1){
                    requestSignIn();
                }

            }
        });

    }



    private void requestSignIn() {
//        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }


    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
//                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    com.google.api.services.drive.Drive googleDriveService =
                            new com.google.api.services.drive.Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    currentGoogleAccount = googleAccount;

                    setUserInfo();
                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception ->
//                        Log.e("TAG", "Unable to sign in.", exception)
                                Toast.makeText(this, "Unable to sign in", Toast.LENGTH_SHORT).show()
                );
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:

                if (resultCode == RESULT_OK && data != null ) {


                    handleSignInResult(data);

                    setUserInfo();






                }
                break;
//
        }
    }


    private void setUserInfo(){
//        UserName.setText("");
//        UserEmail.setText("");
//        imageView_UserPhoto.setImageResource(R.drawable.user_image);
//
//

        if (currentGoogleAccount != null){
            if (GoogleSignIn.getLastSignedInAccount(this) != null){
//                UserName.setText(currentGoogleAccount.getDisplayName().toString());
//
//                buttonSignIn.setVisibility(View.GONE);
//                buttonSignOut.setVisibility(View.VISIBLE);

                OnBoardingItem item = new OnBoardingItem();

                item.setDescription(currentGoogleAccount.getDisplayName().toString());

                if (GoogleSignIn.getLastSignedInAccount(this).getEmail() != null){
//                    UserEmail.setText(currentGoogleAccount.getEmail().toString());
                    item.setTitle(currentGoogleAccount.getEmail().toString());
                }

                if (GoogleSignIn.getLastSignedInAccount(this).getPhotoUrl() != null){
//                    Picasso.get().load(currentGoogleAccount.getPhotoUrl()).into(imageView_UserPhoto);
//                    item.setImage(Picasso.get().load(currentGoogleAccount.getPhotoUrl()).hashCode());
                }

                setupOnboardingItems(item);

            }

        }
    }



    private void openWallet(){
        Intent intent = new Intent(this,Wallet_Item.class);
        startActivity(intent);
    }


    private void setupOnboardingItems(OnBoardingItem item){
        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem itemOne = new OnBoardingItem();
        itemOne.setTitle("Hello");
        itemOne.setDescription("Welcome to My wallet!");

        OnBoardingItem itemTwo = new OnBoardingItem();

        if (item == null){
            itemTwo.setTitle("Sign in");
            itemTwo.setDescription("Use your Google account");
            itemTwo.setImage(R.drawable.ic_baseline_account_circle_24);
        } else {
            itemTwo = item;
        }




        OnBoardingItem itemThree = new OnBoardingItem();
        itemThree.setTitle("Create wallets");
        itemThree.setDescription("");
        itemThree.setImage(R.drawable.wallet_icon);

        onBoardingItems.add(itemOne);
        onBoardingItems.add(itemTwo);
        onBoardingItems.add(itemThree);

        adapter = new OnBoardingAdapter(onBoardingItems);

        onBoardingViewPager.setAdapter(adapter);


        if (item != null){
            onBoardingViewPager.setCurrentItem(1);
        }


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

        currPosition = position;

        if (position==0){
            buttonGetStarted.setVisibility(View.INVISIBLE);
        } else {
            buttonGetStarted.setVisibility(View.VISIBLE);
        }


        for (int i =0;i<childCount;i++){

            ImageView imageView = (ImageView) indicatorsContainer.getChildAt(i);

            if (i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_active_background));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_inactive_background));
            }

        }


    }

    private void setAnswer(){
        if (GoogleSignIn.getLastSignedInAccount(this) != null){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setAnswer();



    }
}