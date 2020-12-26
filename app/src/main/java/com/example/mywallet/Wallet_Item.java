package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Wallet_Item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextWalletName;
    private Wallet currWallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet__item);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextWalletName = findViewById(R.id.editTextWalletName);

        Intent intent = getIntent();
        currWallet = (Wallet) intent.getSerializableExtra("wallet");
        if (currWallet != null) {
            editTextWalletName.setText(currWallet.getName().toString());
        }


    }

    public void onClickSave(View view) {

        String name = editTextWalletName.getText().toString().trim();

        if (!name.equals("")){
           if (currWallet == null){
               Wallet newWallet = new Wallet(name);

               viewModel.insertWallet(newWallet);
           } else if (! currWallet.getName().equals(name)){
               currWallet.setName(name);
               viewModel.updateWallet(currWallet);
           }


            finish(); 
        } else {
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        
    }


}