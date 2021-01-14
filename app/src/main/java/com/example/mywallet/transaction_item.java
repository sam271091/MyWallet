package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class transaction_item extends AppCompatActivity {

    private Wallet wallet;
    private ImageView imageViewType;
    private Type type;
    private TextView textViewWallet;
    private TextView textViewValueItem;
    private TextView textViewCounterparty;
    private EditText editTextSum;
    private ValueItem valueItem;
    private Counterparty counterparty;
    private double sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);

        imageViewType = findViewById(R.id.imageViewType);
        textViewWallet = findViewById(R.id.textViewWallet);
        textViewValueItem = findViewById(R.id.textViewValueItem);
        textViewCounterparty = findViewById(R.id.textViewCounterparty);
        editTextSum = findViewById(R.id.editTextSum);

        Intent intent = getIntent();
        if (intent.hasExtra("wallet")){
            wallet = (Wallet) intent.getSerializableExtra("wallet");
            type   =  (Type) intent.getSerializableExtra("type");
            textViewWallet.setText(wallet.getName().toString());

            if (type == Type.receipt){
                imageViewType.setImageResource(R.drawable.add);
            } else {
                imageViewType.setImageResource(R.drawable.minus);
            }

        }


        textViewValueItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ValueItemListActivity.class);
                startActivityForResult(intent,1);

            }
        });


        textViewCounterparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Counterparties_List.class);
                startActivityForResult(intent,2);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.hasExtra("valueItem" ) && requestCode == 1) {
            valueItem = (ValueItem) data.getSerializableExtra("valueItem");
            textViewValueItem.setText(valueItem.getName().toString());
        } else if (data != null && data.hasExtra("counterparty" ) && requestCode == 2){
            counterparty = (Counterparty) data.getSerializableExtra("counterparty");
            textViewCounterparty.setText(counterparty.getName().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        sum = 0;

        String sumApp = editTextSum.getText().toString().trim();
       if (!sumApp.equals("")){
           sum = Double.parseDouble(sumApp);
       }


        outState.putSerializable("valueItem",valueItem);
        outState.putSerializable("counterparty",counterparty);
        outState.putDouble("sum",sum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        sum = savedInstanceState.getDouble("sum");
        valueItem = (ValueItem) savedInstanceState.getSerializable("valueItem");
        counterparty = (Counterparty) savedInstanceState.getSerializable("counterparty");
        textViewValueItem.setText(valueItem.getName());
        textViewCounterparty.setText(counterparty.getName());
    }


}