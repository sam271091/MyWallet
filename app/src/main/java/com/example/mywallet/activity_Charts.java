package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.mywallet.converters.CounterpartyConverter;
import com.example.mywallet.converters.WalletConverter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class activity_Charts extends AppCompatActivity {

    private BarChart barChart;
    MainViewModel viewModel;
    private Wallet wallet;
    private List<Transaction> transactionsCp;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__charts);

        calendar = Calendar.getInstance();

        barChart = findViewById(R.id.barChart);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);



        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("currWallet")){
                wallet = (Wallet) intent.getSerializableExtra("currWallet");
            }
        }


        transactionsCp = viewModel.getDataByWallet(WalletConverter.WalletToString(wallet));


        List<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();



       for ( Transaction transactionCp : transactionsCp){
           Counterparty counterparty = transactionCp.getCounterparty();


           ArrayList<BarEntry> Results = new ArrayList<>();

           List<Transaction> transactions = viewModel.getDataByWalletAndCounterparty(WalletConverter.WalletToString(wallet), CounterpartyConverter.CounterpartyToString(counterparty));

           for ( Transaction transaction : transactions) {
               calendar.setTime(transaction.getDate());
               int month = calendar.get(Calendar.MONTH) + 1;
               Results.add(new BarEntry(month, (float) transaction.getTurnoversum()));

           }

           String LegengLabel = "none";

           if (counterparty != null){
               LegengLabel = counterparty.getName();
           }

           BarDataSet barDataSet = new BarDataSet(Results,LegengLabel);
           barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
           barDataSet.setValueTextColor(Color.BLACK);
           barDataSet.setValueTextSize(16f);


           dataSets.add(barDataSet);

       }





        BarData barData = new BarData(dataSets);

        barChart.setFitBars(true);



        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);




    }
}