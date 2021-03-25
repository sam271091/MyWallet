package com.example.mywallet.adapters;

import android.content.Context;
import android.graphics.Color;

import com.example.mywallet.R;
import com.example.mywallet.Transaction;
import com.example.mywallet.fragments.BarChartFragment;
import com.example.mywallet.fragments.receiptsFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class FragmentsPagerAdapter extends FragmentStateAdapter {
     private int numOfTabs;
     private PieData pieData;
    private BarData barData;
    private List<String[]> tableData;


    public FragmentsPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numOfTabs) {
        super(fragmentActivity);
        pieData = new PieData();
        barData = new BarData();
        this.numOfTabs = numOfTabs;
    }



    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (position == 0 || position == 1){
            PieChart pieChart = holder.itemView.findViewById(R.id.pieChart);

            if (pieChart != null){
                createPieChart(pieChart);

                final TableView<String[]> tableView = (TableView<String[]>) holder.itemView.findViewById(R.id.tableView);
//                final TableView tableView = (TableView) holder.itemView.findViewById(R.id.tableView);

                createTable(tableView,holder.itemView.getContext());

            }
        } else if (position == 2){
            BarChart barChart = holder.itemView.findViewById(R.id.barChart);
            if (barChart != null){
                createBarChart(barChart);
            }
        }


    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0 || position == 1){
            return new receiptsFragment();
        } else if (position == 2){
            return new BarChartFragment();
        } else  return null;

    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
        notifyDataSetChanged();
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
        notifyDataSetChanged();
    }


    public void setTableData(List<String[]> tableData) {
        this.tableData = tableData;
    }

    private void createPieChart(PieChart pieChart){

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }


    private void createBarChart(BarChart barChart){
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }


    public void createTable(TableView tableView, Context context){


        tableView.setColumnCount(2);

        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));

        String[] Columns={context.getString(R.string.value_item_label),context.getString(R.string.sum_label)};

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(context,Columns));
        TableDataAdapter tableDataAdapter = new SimpleTableDataAdapter(context, tableData);
        tableView.setDataAdapter(tableDataAdapter);
//        tableView.setDataAdapter(adapter);
    }
}
