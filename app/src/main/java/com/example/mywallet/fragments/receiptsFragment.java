package com.example.mywallet.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentViewHolder;
import de.codecrafters.tableview.TableView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mywallet.MainViewModel;
import com.example.mywallet.R;
import com.example.mywallet.ReportsGenerator;
import com.example.mywallet.Type;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;
import com.example.mywallet.adapters.FragmentsPagerAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;
import java.util.List;


public class receiptsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ReportsGenerator reportsGenerator;
    private PieData pieData;

    private List<String[]> tableData;
    private List<ValueItem> valueItems;
    private Wallet wallet;
    private Date startOfThePeriod;
    private Date endOfThePeriod;
    private Type type;
    private MainViewModel viewModel;


    public receiptsFragment() {
        // Required empty public constructor
    }


    public static receiptsFragment newInstance(String param1, String param2) {
        receiptsFragment fragment = new receiptsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipts, container, false);

        PieChart pieChart = (PieChart) view.findViewById(R.id.pieChart);

        if (pieChart != null){
            createPieChart(pieChart);

            final TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.tableView);
//                final TableView tableView = (TableView) holder.itemView.findViewById(R.id.tableView);

            createTable(tableView,view.getContext(),view);

        }


//        reportsGenerator.createBarChart();

        return view;
    }

    public void createTable(TableView tableView, Context context, View view){
        if (tableData != null){
            reportsGenerator.setTableData(tableData);
            reportsGenerator.createTable(tableView,context,view);
        }
    }


        private void createPieChart(PieChart pieChart){
        if (wallet != null){
            reportsGenerator.setEndOfThePeriod(endOfThePeriod);
            reportsGenerator.setStartOfThePeriod(startOfThePeriod);
            reportsGenerator.setWallet(wallet);
            reportsGenerator.setValueItems(valueItems);
            reportsGenerator.setType(type);
            reportsGenerator.setViewModel(viewModel);
            reportsGenerator.setPieData(pieData);
            reportsGenerator.createPieChart(pieChart);
        }


    }


    public void setTableData(List<String[]> tableData) {
        this.tableData = tableData;
    }

    public void setValueItems(List<ValueItem> valueItems) {
        this.valueItems = valueItems;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setStartOfThePeriod(Date startOfThePeriod) {
        this.startOfThePeriod = startOfThePeriod;
    }

    public void setEndOfThePeriod(Date endOfThePeriod) {
        this.endOfThePeriod = endOfThePeriod;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
    }

    public void setReportsGenerator(ReportsGenerator reportsGenerator) {
        this.reportsGenerator = reportsGenerator;
    }
}