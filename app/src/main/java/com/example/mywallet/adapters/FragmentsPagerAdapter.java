package com.example.mywallet.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mywallet.MainActivity;
import com.example.mywallet.MainViewModel;
import com.example.mywallet.R;
import com.example.mywallet.ReportsGenerator;
import com.example.mywallet.Transaction;
import com.example.mywallet.Type;
import com.example.mywallet.ValueItem;
import com.example.mywallet.Wallet;
import com.example.mywallet.converters.DateConverter;
import com.example.mywallet.converters.TypeConverter;
import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;
import com.example.mywallet.fragments.BarChartFragment;
import com.example.mywallet.fragments.receiptsFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class FragmentsPagerAdapter extends FragmentStateAdapter {
     private int numOfTabs;
     private PieData pieData;
    private BarData barData;
    private List<String[]> tableData;
    private List<ValueItem> valueItems;
    private Wallet wallet;

    private Date startOfThePeriod;
    private Date endOfThePeriod;
    private Type type;
    private MainViewModel viewModel;
    private BottomSheetDialog bottomSheetDialog;
    private ReportsGenerator reportsGenerator;
    private int currentPos;



    public FragmentsPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numOfTabs) {
        super(fragmentActivity);
        this.numOfTabs = numOfTabs;
        pieData = new PieData();
        barData = new BarData();
        reportsGenerator = new ReportsGenerator();



    }



    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        if (position == 0 || position == 1){
            PieChart pieChart = (PieChart) holder.itemView.findViewById(R.id.pieChart);

            if (pieChart != null){
                createPieChart(pieChart);

                final TableView<String[]> tableView = (TableView<String[]>) holder.itemView.findViewById(R.id.tableView);
//                final TableView tableView = (TableView) holder.itemView.findViewById(R.id.tableView);

                createTable(tableView,holder.itemView.getContext(),holder);

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

            receiptsFragment fragment = new receiptsFragment();
            fragment.setReportsGenerator(reportsGenerator);
            fragment.setEndOfThePeriod(endOfThePeriod);
            fragment.setStartOfThePeriod(startOfThePeriod);
            fragment.setWallet(wallet);
            fragment.setValueItems(valueItems);
            fragment.setType(type);
            fragment.setViewModel(viewModel);
            fragment.setTableData(tableData);
            fragment.setPieData(pieData);

            return fragment;
        } else if (position == 2){
            BarChartFragment fragmentBC = new BarChartFragment();
            fragmentBC.setWallet(wallet);
            fragmentBC.setReportsGenerator(reportsGenerator);
            fragmentBC.setBarData(barData);

            return fragmentBC;
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

    public void setStartOfThePeriod(Date startOfThePeriod) {
        this.startOfThePeriod = startOfThePeriod;
    }

    public void setEndOfThePeriod(Date endOfThePeriod) {
        this.endOfThePeriod = endOfThePeriod;
    }

    public void setTableData(List<String[]> tableData) {
        this.tableData = tableData;
    }

    public void setValueItems(List<ValueItem> valueItems) {
        this.valueItems = valueItems;
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    private void createPieChart(PieChart pieChart){
        reportsGenerator.setEndOfThePeriod(endOfThePeriod);
        reportsGenerator.setStartOfThePeriod(startOfThePeriod);
        reportsGenerator.setWallet(wallet);
        reportsGenerator.setValueItems(valueItems);
        reportsGenerator.setType(type);
        reportsGenerator.setViewModel(viewModel);
        reportsGenerator.setPieData(pieData);
        reportsGenerator.createPieChart(pieChart);

    }


    private void createBarChart(BarChart barChart){
        reportsGenerator.setEndOfThePeriod(endOfThePeriod);
        reportsGenerator.setStartOfThePeriod(startOfThePeriod);
        reportsGenerator.setWallet(wallet);
        reportsGenerator.setValueItems(valueItems);
        reportsGenerator.setType(type);
        reportsGenerator.setViewModel(viewModel);
        reportsGenerator.setBarData(barData);
        reportsGenerator.createBarChart(barChart);
    }


    public void createTable(TableView tableView, Context context,@NonNull FragmentViewHolder holder){

        reportsGenerator.setTableData(tableData);
        reportsGenerator.createTable(tableView,context,holder.itemView);

//        bottomSheetDialog = new BottomSheetDialog(
//                tableView.getContext(),R.style.BottomSheetDialogTheme);
//
//        tableView.setColumnCount(2);
//
//        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
//
//        String[] Columns={context.getString(R.string.value_item_label),context.getString(R.string.sum_label)};
//
//        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(context,Columns));
//        TableDataAdapter tableDataAdapter = new SimpleTableDataAdapter(context, tableData);
//        tableView.setDataAdapter(tableDataAdapter);
//
//
//
//
//
//        tableView.addDataClickListener(new TableDataClickListener() {
//            @Override
//            public void onDataClicked(int rowIndex, Object clickedData) {
//
//
//                ValueItem currValueItem = valueItems.get(rowIndex);
//
//
//
//                if (!bottomSheetDialog.isShowing()){
//                    View bottomSheetView = LayoutInflater.from(tableView.getContext())
//                            .inflate(R.layout.transactions_list,
//                                    (CardView)holder.itemView.findViewById(R.id.bottomSheetContainerTransactions)
//                            );
//
//                    RecyclerView recyclerViewTransactions = bottomSheetView.findViewById(R.id.recyclerViewTransactions);
//
//
//                    TransactionsAdapter adapter = new TransactionsAdapter();
//
//
//
//                    List<Transaction> transactions = viewModel.getAllTransactionsByWalletAndValueItemAndTypeForThePeriod(WalletConverter.WalletToString(wallet),ValueItemConverter.ValueItemToString(currValueItem), TypeConverter.TypeToString(type), DateConverter.dateToTimestamp(startOfThePeriod),DateConverter.dateToTimestamp(endOfThePeriod));
//
//                    adapter.setTransactions(transactions);
//
//                    LinearLayoutManager manager = new LinearLayoutManager(tableView.getContext());
//                    manager.setOrientation(LinearLayoutManager.VERTICAL);
//                    recyclerViewTransactions.setLayoutManager(manager);
//                    recyclerViewTransactions.setAdapter(adapter);
//
//
//                    bottomSheetDialog.setContentView(bottomSheetView);
//
//
//
//                    bottomSheetDialog.show();
//                }
//
//
//
//
//
//
//
//
//            }
//        });



//        tableView.setDataAdapter(adapter);
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }
}
