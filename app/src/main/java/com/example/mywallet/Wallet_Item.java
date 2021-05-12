package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywallet.converters.TypeConverter;
import com.example.mywallet.converters.WalletConverter;

public class Wallet_Item extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextWalletName;
    private Wallet currWallet;
    private TextView textViewCurrentBalance_Wallet;
    private TextView textViewTotalReceipts;
    private TextView textViewTotalExpences;
    private Double currentBalance;
    private Double totalReceipts;
    private Double totalExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet__item);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextWalletName = findViewById(R.id.editTextWalletName);

        textViewCurrentBalance_Wallet = findViewById(R.id.textViewCurrentBalance_Wallet);
        textViewTotalReceipts = findViewById(R.id.textViewTotalReceipts);
        textViewTotalExpences = findViewById(R.id.textViewTotalExpences);

        Intent intent = getIntent();
        currWallet = (Wallet) intent.getSerializableExtra("wallet");
        if (currWallet != null) {
            editTextWalletName.setText(currWallet.getName().toString());

             currentBalance = viewModel.getCurrentBalance(WalletConverter.WalletToString(currWallet));

             totalReceipts = viewModel.getTotalByType(WalletConverter.WalletToString(currWallet), TypeConverter.TypeToString(Type.receipt));

             totalExpenses = viewModel.getTotalByType(WalletConverter.WalletToString(currWallet), TypeConverter.TypeToString(Type.expense));

            if (currentBalance == null){
                currentBalance = 0.00;
            }

            if (totalReceipts == null){
                totalReceipts = 0.00;
            }

            if (totalExpenses == null){
                totalExpenses = 0.00;
            }

            textViewCurrentBalance_Wallet.setText(getString(R.string.label_current_balance) + " :"+ Double.toString(currentBalance));

            textViewTotalReceipts.setText(getString(R.string.totalReceipts_Label) + " :"+ Double.toString(totalReceipts));

            textViewTotalExpences.setText(getString(R.string.total_Expenses_Label) + " :"+ Double.toString(totalExpenses));

        }


    }

    public void onClickSave(View view) {

        String name = editTextWalletName.getText().toString().trim();

        if (!name.equals("")){
           if (currWallet == null){
               Wallet newWallet = new Wallet(name);

               viewModel.insertWallet(newWallet);
           } else if (! currWallet.getName().equals(name)){
               String oldWallet = WalletConverter.WalletToString(currWallet);
               currWallet.setName(name);
               viewModel.updateTransactionsByWallet(oldWallet,WalletConverter.WalletToString(currWallet));
               viewModel.updateWallet(currWallet);

           }


            finish(); 
        } else {
            Toast.makeText(this, R.string.empty_name_warning, Toast.LENGTH_SHORT).show();
        }
        
    }


}