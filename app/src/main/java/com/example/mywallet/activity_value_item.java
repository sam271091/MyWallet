package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_value_item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextValueItemName;
    private ValueItem currValueItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_item);


        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextValueItemName = findViewById(R.id.editTextValueItemName);


    }


    public void onClickValueItemSave(View view) {

        String name = editTextValueItemName.getText().toString().trim();

        if (!name.equals("")){
            if (currValueItem == null){
                ValueItem newValueItem = new ValueItem(name,"");

                viewModel.insertValueItem(newValueItem);
            } else if (! currValueItem.getName().equals(name)){
                currValueItem.setName(name);
                viewModel.updateValueItem(currValueItem);
            }


            finish();
        } else {
            Toast.makeText(this, R.string.empty_name_warning, Toast.LENGTH_SHORT).show();
        }

    }
}