package com.example.mywallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class transaction_item extends AppCompatActivity {

    private Wallet wallet;
    private ImageView imageViewType;
    private Type type;
    private TextView textViewDate;
    private TextView textViewWallet;
    private TextView textViewValueItem;
    private TextView textViewCounterparty;
    private EditText editTextSum;
    private ValueItem valueItem;
    private Counterparty counterparty;
    private double sum;
    private Date date;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item);

        calendar = Calendar.getInstance();

        if (date == null){
            date = calendar.getTime();
        }

        setDateValues();


        imageViewType = findViewById(R.id.imageViewType);
        textViewDate =  findViewById(R.id.textViewDate);
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


//        textViewDate.setText(date.toString());

        showDate();

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

    }

    void setDateValues(){
        calendar.setTime(date);

        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
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

        outState.putSerializable("date",date);
        outState.putSerializable("valueItem",valueItem);
        outState.putSerializable("counterparty",counterparty);
        outState.putDouble("sum",sum);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        date = (Date) savedInstanceState.getSerializable("date");

        setDateValues();

        sum = savedInstanceState.getDouble("sum");
        valueItem = (ValueItem) savedInstanceState.getSerializable("valueItem");
        counterparty = (Counterparty) savedInstanceState.getSerializable("counterparty");
        if (valueItem != null) {
            textViewValueItem.setText(valueItem.getName());
        }

        if (counterparty != null){
            textViewCounterparty.setText(counterparty.getName());
        }

//        textViewDate.setText(date.toString());

        showDate();
    }




    @SuppressWarnings("deprecation")
    public void setDate() {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker,
                                      int arg1, int arg2, int arg3) {

                     day = datePicker.getDayOfMonth();
                     month = datePicker.getMonth();
                     year =  datePicker.getYear();

                    calendar.set(year, month, day);

                    date = calendar.getTime();

                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate();
                }
            };

    private void showDate() {


        textViewDate.setText(new StringBuilder().append(day).append("/")
                .append(month+1).append("/").append(year));
    }

}