package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Wallet_Item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextWalletName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet__item);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextWalletName = findViewById(R.id.editTextWalletName);

    }

    public void onClickSave(View view) {

        String name = editTextWalletName.getText().toString().trim();

        Wallet newWallet = new Wallet(name);

        viewModel.insertWallet(newWallet);

        finish();
    }
}