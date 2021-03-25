package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;

public class activity_value_item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextValueItemName;
    private ValueItem currValueItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_item);

        setTitle(getString(R.string.label_value_item));

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextValueItemName = findViewById(R.id.editTextValueItemName);


        Intent intent = getIntent();
        if (intent.hasExtra("curr_valueItem")){
            currValueItem = (ValueItem) intent.getSerializableExtra("curr_valueItem");
            fillData();

        }

    }



    void fillData(){
        editTextValueItemName.setText(currValueItem.getName().toString());

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

        if (!name.equals("")){
            if (currValueItem == null){
                ValueItem newValueItem = new ValueItem(name,"");

                viewModel.insertValueItem(newValueItem);
            } else if (! currValueItem.getName().equals(name)){
                String oldValueItem = ValueItemConverter.ValueItemToString(currValueItem);
                currValueItem.setName(name);
                viewModel.updateTransactionsByValueItem(oldValueItem,ValueItemConverter.ValueItemToString(currValueItem));
                viewModel.updateValueItem(currValueItem);
            }


            finish();
        } else {
            Toast.makeText(this, R.string.empty_name_warning, Toast.LENGTH_SHORT).show();
        }

    }
}