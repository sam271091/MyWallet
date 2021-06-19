package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;

public class activity_value_item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextValueItemName;
    private ValueItem currValueItem;
    private Toolbar toolbar;
    private ImageView imageViewPicture;
    private String pictureStringID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_item);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.label_value_item));

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextValueItemName = findViewById(R.id.editTextValueItemName);

        imageViewPicture = findViewById(R.id.imageViewPictureId);

        Intent intent = getIntent();
        if (intent.hasExtra("curr_valueItem")){
            currValueItem = (ValueItem) intent.getSerializableExtra("curr_valueItem");
            fillData();

        }


        imageViewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),activity_picture_chooser.class);
                startActivityForResult(intent,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.hasExtra("pictureID" ) && requestCode == 1) {
            pictureStringID = data.getStringExtra("pictureID");
            int pictureId = getResources().getIdentifier(pictureStringID,null,null);
            imageViewPicture.setImageResource(pictureId);
        }
    }

    void fillData(){
        editTextValueItemName.setText(currValueItem.getName().toString());

        String picStringID = currValueItem.getPictureID();

        if (picStringID != null){
            int pictureId = getResources().getIdentifier(picStringID.toString(),null,null);
            imageViewPicture.setImageResource(pictureId);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("currValueItem",currValueItem);

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currValueItem = (ValueItem) savedInstanceState.getSerializable("currValueItem");

        if (currValueItem != null){
            fillData();
        }

    }

    public void onClickValueItemSave(View view) {

        String name = editTextValueItemName.getText().toString().trim();

        String picStringID = null;
        if (currValueItem!=null){
            picStringID = currValueItem.getPictureID();
        }


        if (!name.equals("")){
            if (currValueItem == null){
                ValueItem newValueItem = new ValueItem(name,"",pictureStringID);

                viewModel.insertValueItem(newValueItem);
            } else if (! currValueItem.getName().equals(name) || (picStringID == null || !picStringID.equals(pictureStringID))){
                String oldValueItem = ValueItemConverter.ValueItemToString(currValueItem);
                currValueItem.setName(name);
                currValueItem.setPictureID(pictureStringID);
                viewModel.updateTransactionsByValueItem(oldValueItem,ValueItemConverter.ValueItemToString(currValueItem));
                viewModel.updateValueItem(currValueItem);
            }


            finish();
        } else {
            Toast.makeText(this, R.string.empty_name_warning, Toast.LENGTH_SHORT).show();
        }

    }
}