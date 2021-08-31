package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywallet.adapters.FilesSelectorAdapter;
import com.example.mywallet.adapters.TransactionsAdapter;
import com.example.mywallet.adapters.WalletsAdapter;
import com.example.mywallet.adapters.WalletsPagerAdapter;
import com.example.mywallet.converters.CounterpartyConverter;
import com.example.mywallet.converters.DateConverter;
import com.example.mywallet.converters.TransactionConverter;
import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.CreateFileActivityOptions;
//import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.google.api.services.drive.Drive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  TransactionsAdapter adapter;
    private  RecyclerView recyclerViewWallets;
    private MainViewModel viewModel;
    private ViewPager2 walletViewPager;
    private WalletsPagerAdapter pagerAdapter;
    private Wallet currwallet;
    private List<Wallet> walletsList;
    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;

    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    private SharedPreferences preferences;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private  TextView UserEmail;
    private  TextView UserName;
    private ImageView imageView_UserPhoto;
    private Button buttonSignOut;
    private Button buttonSignIn;

    private DriveServiceHelper mDriveServiceHelper;

    private GoogleSignInAccount currentGoogleAccount;

    private GoogleSignInClient client;
    private String mOpenFileId;

    private String fileName = "MyWalletdata.json";
    private String fileContents;

    private Calendar calendar;

    private String folderId;

    private boolean fileSelected;

    private TabLayout tabDateChooser;

    private Date currDate;

    private int currentPos;

    private Date startOfTheDay;
    private Date endOfTheDay;

    private Date startOfTheMonth;
    private Date endOfTheMonth;

    private Date startOfTheYear;
    private Date endOfTheYear;

    private Date startOfThePeriod;
    private Date endOfThePeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );





//        startActivity(intent);

        tabDateChooser = findViewById(R.id.tabDateChooser);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        UserName = navigationView.getHeaderView(0).findViewById(R.id.textViewUserName);
        UserEmail = navigationView.getHeaderView(0).findViewById(R.id.textViewEmail);
        imageView_UserPhoto = navigationView.getHeaderView(0).findViewById(R.id.imageView_UserPhoto);

        buttonSignOut = navigationView.getHeaderView(0).findViewById(R.id.buttonSignOut);

        buttonSignIn = navigationView.getHeaderView(0).findViewById(R.id.buttonSignIn);

        setTitle(getString(R.string.MainLabel));

        calendar = Calendar.getInstance();

        currDate = calendar.getTime();

        startOfTheDay = getStartOfTheDay(currDate);

        endOfTheDay   = getEndOfTheDay(currDate);

        startOfTheMonth = getStartOfTheMonth(currDate);

        endOfTheMonth = getEndOfTheMonth(currDate);

        startOfTheYear = getStartOfTheYear(currDate);

        endOfTheYear  = getEndOfTheYear(currDate);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String currUserEmail = preferences.getString("UserEmail","");

        if ( currUserEmail != ""){
            signIn();
        }




        walletsList = new ArrayList<>();

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);





        walletViewPager = findViewById(R.id.ViewPagerWallets);

        pagerAdapter = new WalletsPagerAdapter(this);

        adapter = new TransactionsAdapter();

        viewModel.getWallets().observe(this, new Observer<List<Wallet>>() {
            @Override
            public void onChanged(List<Wallet> wallets) {
//                adapter.setWallets(wallets);
                pagerAdapter.setWallets(wallets);
                walletsList.clear();
                walletsList .addAll(wallets);
                int pagerPosition = walletViewPager.getCurrentItem();

//


                setCurrentWallet(pagerPosition);

//


            }
        });



        if (viewModel.getListOfWallets().size() == 0){
            Intent intent = new Intent(this,walkthrough_screen_activity.class);
            startActivityForResult(intent,100);
        }





        adapter.setOnTransactionClickListener(new TransactionsAdapter.OnTransactionClickListener() {
            @Override
            public void OnTransactionClick(int position) {
                List<Transaction> transactions = adapter.getTransactions();
                Transaction currtransaction = transactions.get(position);




                OpenTransaction(currtransaction);
            }
        });




        pagerAdapter.setOnWalletClickListener(new WalletsPagerAdapter.OnWalletClickListener() {
            @Override
            public void OnWalletClick(int position) {
                List<Wallet> wallets = pagerAdapter.getWallets();
                Wallet currwallet = wallets.get(position);
                openWallet(currwallet);
            }
        });

        pagerAdapter.setOnWalletDeleteClickListener(new WalletsPagerAdapter.OnWalletDeleteClickListener() {
            @Override
            public void OnWalletDeleteClick(final int position) {
                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.question_delete_wallet)
                        .setTitle(R.string.warning_title)
                        .setCancelable(true)
                        .setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                List<Wallet> wallets = pagerAdapter.getWallets();
                                Wallet currwallet = wallets.get(position);
                                viewModel.deleteTransactionsByWallet(WalletConverter.WalletToString(currwallet));
                                viewModel.deleteWallet(currwallet);

                            }
                        })
                        .setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        recyclerViewWallets = findViewById(R.id.RecyclerViewWallets);
        LinearLayoutManager manager = new LinearLayoutManager(this);

//        GridLayoutManager manager = new GridLayoutManager(this,2);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewWallets.setLayoutManager(manager);
        recyclerViewWallets.setAdapter(adapter);




        walletViewPager.setAdapter(pagerAdapter);

        walletViewPager.setClipToPadding(false);
        walletViewPager.setClipChildren(false);
        walletViewPager.setOffscreenPageLimit(3);
        walletViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(60));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                Float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r*0.05f);
            }
        });

        walletViewPager.setPageTransformer(compositePageTransformer);


        walletViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentWallet(position);
                pagerAdapter.notifyDataSetChanged();

            }
        });



//        GoogleSignInClient mGoogleSignInClient = buildGoogleSignInClient();
//
//        Intent intent = mGoogleSignInClient.getSignInIntent();
//
//        startActivity(intent);

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                GoogleSignInClient GoogleSignInClient = buildGoogleSignInClient();
//                GoogleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                   client.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        setUserInfo();
                        buttonSignIn.setVisibility(View.VISIBLE);
                        buttonSignOut.setVisibility(View.GONE);
                    }
                });
            }
        });


        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        buttonSignIn.setVisibility(View.VISIBLE);
        buttonSignOut.setVisibility(View.GONE);



        tabDateChooser.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPos = tab.getPosition();

                setTransactionObserver();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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


    private void signIn() {
//        Log.i(TAG, "Start sign in");
//        GoogleSignInClient GoogleSignInClient = buildGoogleSignInClient();
//        startActivityForResult(GoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        requestSignIn();
    }




    private void setUserInfo(){
        UserName.setText("");
        UserEmail.setText("");
        imageView_UserPhoto.setImageResource(R.drawable.user_image);
//
//

        if (currentGoogleAccount != null){
            if (GoogleSignIn.getLastSignedInAccount(this) != null){
                UserName.setText(currentGoogleAccount.getDisplayName().toString());



                buttonSignIn.setVisibility(View.GONE);
                buttonSignOut.setVisibility(View.VISIBLE);

                if (GoogleSignIn.getLastSignedInAccount(this).getEmail() != null){
                    UserEmail.setText(currentGoogleAccount.getEmail().toString());
                    preferences.edit().putString("UserEmail",currentGoogleAccount.getEmail().toString()).apply();
                }

                if (GoogleSignIn.getLastSignedInAccount(this).getPhotoUrl() != null){
                    Picasso.get().load(currentGoogleAccount.getPhotoUrl()).into(imageView_UserPhoto);
                }

            }

        }
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
            case 100:
                if (resultCode == RESULT_OK){
                    signIn();
                }

                break;
//
        }
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






    private void saveFileToDrive(String fileName,String sBody ) {

//        final Task<DriveFolder> rootFolderTask = mDriveResourceClient.getRootFolder();
//        final Task<DriveContents> createContentsTask = mDriveResourceClient.createContents();
//        Tasks.whenAll(rootFolderTask, createContentsTask)
//                .continueWithTask(task -> {
//                    DriveFolder parent = rootFolderTask.getResult();
//                    DriveContents contents = createContentsTask.getResult();
//                    OutputStream outputStream = contents.getOutputStream();
//                    try (Writer writer = new OutputStreamWriter(outputStream)) {
//                        writer.write(sBody);
//                    }
//
//                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
//                            .setTitle(fileName)
//                            .setMimeType("text/json")
//                            .setStarred(true)
//                            .build();
//
//                    return mDriveResourceClient.createFile(parent, changeSet, contents);
//                })
//                .addOnSuccessListener(this,
//                        driveFile -> {
////                            showMessage(getString(R.string.file_created,
////                                    driveFile.getDriveId().encodeToString()));
////                            finish();
//                        })
//                .addOnFailureListener(this, e -> {
////                    Log.e(TAG, "Unable to create file", e);
//                    Toast.makeText(this, "Unable to create file", Toast.LENGTH_SHORT).show();;
////                    finish();
//                });

        createData();
//        saveFile();

    }


    private void findFolder(){
         mDriveServiceHelper.findFolder()
                 .addOnSuccessListener(new OnSuccessListener<String>() {
                     @Override
                     public void onSuccess(String s) {
                         folderId = s;
                        if (folderId != null){
                            createFile();
                        } else {
                            createFolder();
                        }

                     }

                 }
                 )
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MainActivity.this, R.string.folder_creation_fail, Toast.LENGTH_SHORT).show();
                     }
                 });
    }

    private void createData() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            findFolder();



        }

    }

    private void createFolder(){
        mDriveServiceHelper.createFolder()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        folderId = s;
                        createFile();
                    }
                });
    }

    private void createFile() {
        if (mDriveServiceHelper != null) {
//            Log.d(TAG, "Creating a file.");


            mDriveServiceHelper.createFile(folderId)
                    .addOnSuccessListener(fileId -> readFile(fileId))
                                    .addOnFailureListener(exception ->
                    Toast.makeText(this, "Couldn't create file.", Toast.LENGTH_SHORT).show());
        }


    }


    /**
     * Retrieves the title and content of a file identified by {@code fileId} and populates the UI.
     */
    private void readFile(String fileId) {
        if (mDriveServiceHelper != null) {
//            Log.d(TAG, "Reading file " + fileId);

            mDriveServiceHelper.readFile(fileId)
                    .addOnSuccessListener(nameAndContent -> {
                        String name = nameAndContent.first;
                        String content = nameAndContent.second;



                       if (fileSelected != true){
                           setReadWriteMode(fileId);

                           saveFile();
                       } else {
                           fileSelected = false;
                           readFileContents(content);
                       }

                    })
                    .addOnFailureListener(exception ->
                            Toast.makeText(this, "Couldn't read file.", Toast.LENGTH_SHORT).show());
        }
    }


    private void readFileContents(String content){
        //Wallets
        try {
            JSONObject jObject = new JSONObject(content);
            JSONArray jArraywallets = jObject.getJSONArray("wallets");

            for (int i=0; i < jArraywallets.length(); i++)
            {
                try {
                    JSONObject oneObject = jArraywallets.getJSONObject(i);
                   Wallet wallet = WalletConverter.StringToWallet(oneObject.toString());

                    viewModel.insertWallet(wallet);

                } catch (JSONException e) {
                    // Oops
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //ValueItems
        try {
            JSONObject jObject = new JSONObject(content);
            JSONArray jArrayValueItems = jObject.getJSONArray("valueItems");

            for (int i=0; i < jArrayValueItems.length(); i++)
            {
                try {
                    JSONObject oneObject = jArrayValueItems.getJSONObject(i);
                    ValueItem valueItem = ValueItemConverter.StringToValueItem(oneObject.toString());

                    viewModel.insertValueItem(valueItem);

                } catch (JSONException e) {
                    // Oops
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Counterparties
        try {
            JSONObject jObject = new JSONObject(content);
            JSONArray jArrayCounterparties = jObject.getJSONArray("counterparties");

            for (int i=0; i < jArrayCounterparties.length(); i++)
            {
                try {
                    JSONObject oneObject = jArrayCounterparties.getJSONObject(i);
                    Counterparty counterparty = CounterpartyConverter.StringToCounterparty(oneObject.toString());

                    viewModel.insertCounterparty(counterparty);

                } catch (JSONException e) {
                    // Oops
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Transactions
        try {
            JSONObject jObject = new JSONObject(content);
            JSONArray jArrayTransactions = jObject.getJSONArray("transactions");

            for (int i=0; i < jArrayTransactions.length(); i++)
            {
                try {
                    JSONObject oneObject = jArrayTransactions.getJSONObject(i);
                    Transaction transaction = TransactionConverter.StringToTransaction(oneObject.toString());

                    viewModel.insertTransaction(transaction);

                } catch (JSONException e) {
                    // Oops
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * Saves the currently opened file created via {@link #createFile()} if one exists.
     */
    private void saveFile() {
        if (mDriveServiceHelper != null && mOpenFileId != null) {
//            Log.d(TAG, "Saving " + mOpenFileId);

//            String fileName = mFileTitleEditText.getText().toString();
//            String fileContent = mDocContentEditText.getText().toString();

             currDate = calendar.getTime();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = df.format(currDate);

            mDriveServiceHelper.saveFile(mOpenFileId, fileName + " " + date.toString(), fileContents)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "File upload success!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(exception ->
                            Toast.makeText(this, "Unable to save file via REST.", Toast.LENGTH_SHORT).show());
        }
    }


    /**
     * Updates the UI to read/write mode on the document identified by {@code fileId}.
     */
    private void setReadWriteMode(String fileId) {
//        mFileTitleEditText.setEnabled(true);
//        mDocContentEditText.setEnabled(true);
        mOpenFileId = fileId;
    }





    public void generateData( String sFileName, String sBody) {
//        try {
//            File root = new File(Environment.getExternalStorageDirectory(), "MyWalletData");
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            File gpxfile = new File(root, sFileName);
//            FileWriter writer = new FileWriter(gpxfile,true);
//            writer.append(sBody);
//            writer.flush();
//            writer.close();

//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(sFileName, Context.MODE_PRIVATE));
//            outputStreamWriter.write(sBody);
//            outputStreamWriter.close();



//

//        if (mDriveClient != null){
//            saveFileToDrive(sFileName,sBody);
//        }

        if (currentGoogleAccount != null){
            saveFileToDrive(sFileName,sBody);
        }

//            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void onClickCreateWallet(View view) {
        openWallet();
    }


    private void openWallet(){
        Intent intent = new Intent(this,Wallet_Item.class);
        startActivity(intent);
    }

    private void openWallet(Wallet wallet){
        Intent intent = new Intent(this,Wallet_Item.class);
        intent.putExtra("wallet",wallet);

        startActivity(intent);
    }


    public void onClickAdd(View view) {
        onOperationClick(Type.receipt);
    }


    void setCurrentWallet(){


        setTransactionObserver();


    }

    void setCurrentWallet(int pagerPosition){
//        int pagerPosition = walletViewPager.getCurrentItem();
        List<Wallet> wallets = pagerAdapter.getWallets();
        if (wallets.size() != 0){
            if (wallets.size() <= pagerPosition){
                currwallet = wallets.get(pagerPosition-1);
            } else {
                currwallet = wallets.get(pagerPosition);
            }

        }


        setTransactionObserver();


    }

    void setTransactionObserver(){
       if (currentPos == 0){
           setTransactionObserver_All();
       } else   {
            setTransactionObserver_By_Date();
        }



    }


    private Date getStartOfTheDay(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);


        Date newDate = calendar.getTime();

        return newDate;
    }

    private Date getEndOfTheDay(Date date){

        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    private Date getStartOfTheYear(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_YEAR, 1);

        Date newDate = calendar.getTime();

        return newDate;
    }

    private Date getEndOfTheYear(Date date){

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }


    private Date getStartOfTheMonth(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date newDate = calendar.getTime();

        return newDate;
    }


    private Date getEndOfTheMonth(Date date){

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }


    void setTransactionObserver_By_Date(){

        switch (currentPos){
            case 1 :
                startOfThePeriod = startOfTheDay;
                endOfThePeriod   = endOfTheDay;
                break;
            case 2 :
                startOfThePeriod = startOfTheMonth;
                endOfThePeriod   = endOfTheMonth;
                break;
            case 3 :
                startOfThePeriod = startOfTheYear;
                endOfThePeriod   = endOfTheYear;
                break;
        }


        viewModel.getTransactionsByWalletForThePeriod(WalletConverter.WalletToString(currwallet), DateConverter.dateToTimestamp(startOfThePeriod),DateConverter.dateToTimestamp(endOfThePeriod)).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                boolean updateTransactions = true;



                if (transactions.size() > 0){
                    if (!transactions.get(0).getWallet().getId().equals(currwallet.getId())){
                        updateTransactions = false;
                    }
                }


                if (updateTransactions) {
                    adapter.setTransactions(transactions);
                    pagerAdapter.setWallets(walletsList);
//                pagerAdapter.notifyDataSetChanged();
                }

            }
        });
    }


    void setTransactionObserver_All(){
        viewModel.getTransactionsByWallet(WalletConverter.WalletToString(currwallet)).observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                boolean updateTransactions = true;



                if (transactions.size() > 0 ){
                    if (!transactions.get(0).getWallet().getId().equals(currwallet.getId())){
                        updateTransactions = false;
                    }
                }

                if (currentPos != 0){
                    updateTransactions = false;
                }

                if (updateTransactions) {
                    adapter.setTransactions(transactions);
                    pagerAdapter.setWallets(walletsList);
//                pagerAdapter.notifyDataSetChanged();
                }

            }
        });
    }





    void onOperationClick(Type type){
        int pagerPosition = walletViewPager.getCurrentItem();
        List<Wallet> wallets = pagerAdapter.getWallets();

        if (wallets.size() != 0){
            Wallet currwallet = wallets.get(pagerPosition);

            Intent intent = new Intent(this,transaction_item.class);
            intent.putExtra("wallet",currwallet);
            intent.putExtra("type",type);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_no_wallet_for_transaction, Toast.LENGTH_SHORT).show();
        }


    }



    void OpenTransaction(Transaction currTransaction){
        Intent intent = new Intent(this,transaction_item.class);
        intent.putExtra("currTransaction",currTransaction);

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(this
                    ,new Pair<View,String>(findViewById(R.id.imageViewListType),"imageViewListType"));
        }

        if (options != null){
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }
    }


    public void onClickMinus(View view) {
        onOperationClick(Type.expense);
    }

    public void onClickAnalytics(View view) {
        Intent intent = new Intent(this,activity_Charts.class);
        intent.putExtra("currWallet",currwallet);
        startActivity(intent);
    }


    private void getFiles(){


        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            mDriveServiceHelper.getFiles()
                    .addOnSuccessListener(new OnSuccessListener<ArrayList<File>>() {
                        @Override
                        public void onSuccess(ArrayList<File> files) {

                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                                    MainActivity.this,R.style.BottomSheetDialogTheme);

                            View bottomSheetView = LayoutInflater.from(getApplicationContext())
                                    .inflate(R.layout.activity__drive_file_selector,
                                            (CardView)findViewById(R.id.bottomSheetContainer)
                                    );

                            RecyclerView filesListRecyclerView = bottomSheetView.findViewById(R.id.filesListRecyclerView);

                            FilesSelectorAdapter filesSelectorAdapter = new FilesSelectorAdapter();

                            filesSelectorAdapter.setFiles(files);


                            filesSelectorAdapter.setOnFileClickListener(new FilesSelectorAdapter.OnFileClickListener() {
                                @Override
                                public void onFileClick(int position) {
                                  File currentFile =   files.get(position);
                                    fileSelected = true;
                                    readFile(currentFile.getId());


                                }
                            });


                            LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                            manager.setOrientation(LinearLayoutManager.VERTICAL);
                            filesListRecyclerView.setLayoutManager(manager);
                            filesListRecyclerView.setAdapter(filesSelectorAdapter);


                            bottomSheetDialog.setContentView(bottomSheetView);
                            bottomSheetDialog.show();

                        }
                    });
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

//        if (id == R.id.item_backup ){
//            Gson gson = new Gson();
//            String json = gson.toJson(walletsList);
//            generateData("MyWalletdata.json",json);
//        }

        switch (id){
            case  R.id.item_backup:

                HashMap<String,Object> map = new HashMap<>();
                map.put("wallets",walletsList);
                map.put("valueItems",viewModel.getAllValueItems());
                map.put("counterparties",viewModel.getAllCounterparties());
                map.put("transactions",TransactionConverter.transactionsToArrayList(viewModel.getAllTransactions()));



                Gson gson = new Gson();
                String json = gson.toJson(map);

                fileContents = json;

                generateData("MyWalletdata.json",json);
                break;
            case R.id.item_counterparties :
                Intent intent = new Intent(this,Counterparties_List.class);
                intent.putExtra("notForResult",true);
                startActivity(intent);
                break;
            case R.id.item_value_items :
                Intent intent_VI = new Intent(this,ValueItemListActivity.class);
                intent_VI.putExtra("notForResult",true);
                startActivity(intent_VI);
                break;
            case R.id.item_restore :
                getFiles();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}