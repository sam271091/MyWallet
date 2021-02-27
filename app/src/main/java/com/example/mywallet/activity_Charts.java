package com.example.mywallet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mywallet.adapters.FragmentsPagerAdapter;
import com.example.mywallet.converters.CounterpartyConverter;
import com.example.mywallet.converters.DateConverter;
import com.example.mywallet.converters.TypeConverter;
import com.example.mywallet.converters.WalletConverter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class activity_Charts extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChart;
    MainViewModel viewModel;
    private Wallet wallet;
    private List<Transaction> transactionsCp;
    private Calendar calendar;
    private Date dateOfReport;
    private TextView periodLabel;
    private TabLayout tabLayoutFragmentChooser;
    private TabItem  receiptsTab;
    private TabItem  expenseTab;
    private ViewPager2 chartsViewPager;
    private  FragmentsPagerAdapter fragmentsPagerAdapter;
    private int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__charts);

        currentPos = 0;

        tabLayoutFragmentChooser = findViewById(R.id.tabLayoutFragmentChooser);
        receiptsTab = findViewById(R.id.receiptsTab);
        expenseTab  = findViewById(R.id.expenseTab);
        chartsViewPager = findViewById(R.id.chartsViewPager);

        periodLabel = findViewById(R.id.textViewPeriod);

        calendar = Calendar.getInstance();

//        pieChart = findViewById(R.id.barChart);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        dateOfReport = getStartOfTheMonth(calendar.getTime());

        setDatePresentation();

        Intent intent = getIntent();

        if (intent != null){
            if (intent.hasExtra("currWallet")){
                wallet = (Wallet) intent.getSerializableExtra("currWallet");
            }
        }

        fragmentsPagerAdapter = new FragmentsPagerAdapter(this,tabLayoutFragmentChooser.getTabCount());



        chartsViewPager.setAdapter(fragmentsPagerAdapter);




       tabLayoutFragmentChooser.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               currentPos = tab.getPosition();
               chartsViewPager.setCurrentItem(currentPos);
               createChart();
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });





        createChart();




    }




    void createChart(){

        Type type = Type.receipt;
        if (currentPos == 0){
             type = Type.receipt;
        } else if (currentPos==1){
             type = Type.expense;
        }

        List<PieEntry> entries = new ArrayList<>();

        transactionsCp = viewModel.getDataByWalletAndType(WalletConverter.WalletToString(wallet), TypeConverter.TypeToString(type), DateConverter.dateToTimestamp(dateOfReport),DateConverter.dateToTimestamp(getEndOfTheMonth(dateOfReport)));//viewModel.getDataByWallet(WalletConverter.WalletToString(wallet));
//
//
//        List<IBarDataSet> dataSets = new ArrayList<>();
//
//
//
        for ( Transaction transactionCp : transactionsCp){
            Counterparty counterparty = transactionCp.getCounterparty();
//

//
//           ArrayList<BarEntry> Results = new ArrayList<>();
//
//           List<Transaction> transactions = viewModel.getDataByWalletAndCounterparty(WalletConverter.WalletToString(wallet), CounterpartyConverter.CounterpartyToString(counterparty));
//
//           for ( Transaction transaction : transactions) {
//               calendar.setTime(transaction.getDate());
//               int month = calendar.get(Calendar.MONTH) + 1;
//               Results.add(new BarEntry(month, (float) transaction.getTurnoversum()));
//
//           }
//
            String LegengLabel = "none";

            if (counterparty != null){
                LegengLabel = counterparty.getName();
            }
//


            entries.add(new PieEntry((float) transactionCp.getSum(), LegengLabel));

//           BarDataSet barDataSet = new BarDataSet(Results,LegengLabel);
//           barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//           barDataSet.setValueTextColor(Color.BLACK);
//           barDataSet.setValueTextSize(16f);
//
//
//           dataSets.add(barDataSet);
//
        }
//
//
//
//
//
//        BarData barData = new BarData(dataSets);
//
//        barChart.setFitBars(true);
//
//
//
//        barChart.setData(barData);
//        barChart.getDescription().setText("Bar Chart Example");
//        barChart.animateY(2000);




//        entries.add(new PieEntry(18.5f, "Green"));
//        entries.add(new PieEntry(26.7f, "Yellow"));
//        entries.add(new PieEntry(24.0f, "Red"));
//        entries.add(new PieEntry(30.8f, "Blue"));
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(16f);
        PieData data = new PieData(set);
//        pieChart.setData(data);
//        pieChart.getDescription().setEnabled(false);
//        pieChart.notifyDataSetChanged();
//        pieChart.invalidate();

        fragmentsPagerAdapter.setData(data);
    }



    private Date getStartOfTheMonth(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date newDate = calendar.getTime();

        return newDate;
    }


    private Date getEndOfTheMonth(Date date){

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }


    private void setDatePresentation(){
        DateFormat df = new SimpleDateFormat("MMMM yyyy");
        String date = df.format(dateOfReport);

        periodLabel.setText(date.toString());
    }


    private  Date getCurrentDatePlusMonth(int month)
    {

        calendar.setTime(dateOfReport);
        calendar.add(Calendar.MONTH, month);
        Date newDate = calendar.getTime();
        return newDate;
    }





    public void onClickNext(View view) {
        dateOfReport = getStartOfTheMonth(getCurrentDatePlusMonth(1));
        setDatePresentation();
        createChart();
    }

    public void onClickPrev(View view) {
        dateOfReport = getStartOfTheMonth(getCurrentDatePlusMonth(-1));
        setDatePresentation();
        createChart();
    }
}