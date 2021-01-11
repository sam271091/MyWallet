package com.example.mywallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.hasExtra("valueItem")) {
            ValueItem valueItem = (ValueItem) data.getSerializableExtra("valueItem");
            textViewValueItem.setText(valueItem.getName().toString());
        }
    }
}