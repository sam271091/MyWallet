package com.example.mywallet.adapters;

import com.example.mywallet.R;
import com.example.mywallet.fragments.receiptsFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

public class FragmentsPagerAdapter extends FragmentStateAdapter {
     private int numOfTabs;
     private PieData data;


    public FragmentsPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numOfTabs) {
        super(fragmentActivity);
        this.numOfTabs = numOfTabs;
    }



    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        PieChart pieChart = holder.itemView.findViewById(R.id.pieChart);

        if (pieChart != null){
            createChart(pieChart);
        }

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new receiptsFragment();
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }

    public void setData(PieData data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private void createChart(PieChart pieChart){

        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }
}
