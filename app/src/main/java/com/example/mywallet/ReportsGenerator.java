package com.example.mywallet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mywallet.adapters.TransactionsAdapter;
import com.example.mywallet.converters.DateConverter;
import com.example.mywallet.converters.TypeConverter;
import com.example.mywallet.converters.ValueItemConverter;
import com.example.mywallet.converters.WalletConverter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public  class ReportsGenerator {

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


    public void createPieChart(PieChart pieChart){

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();
    }


    public void createBarChart(BarChart barChart){
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }

    public void createTable(TableView tableView, Context context,  View view){


        bottomSheetDialog = new BottomSheetDialog(
                tableView.getContext(),R.style.BottomSheetDialogTheme);

        tableView.setColumnCount(2);

        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));

        String[] Columns={context.getString(R.string.value_item_label),context.getString(R.string.sum_label)};

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(context,Columns));
        TableDataAdapter tableDataAdapter = new SimpleTableDataAdapter(context, tableData);
        tableView.setDataAdapter(tableDataAdapter);





        tableView.addDataClickListener(new TableDataClickListener() {
            @Override
            public void onDataClicked(int rowIndex, Object clickedData) {


                ValueItem currValueItem = valueItems.get(rowIndex);



                if (!bottomSheetDialog.isShowing()){
                    View bottomSheetView = LayoutInflater.from(tableView.getContext())
                            .inflate(R.layout.transactions_list,
                                    (CardView)view.findViewById(R.id.bottomSheetContainerTransactions)
                            );

                    RecyclerView recyclerViewTransactions = bottomSheetView.findViewById(R.id.recyclerViewTransactions);


                    TransactionsAdapter adapter = new TransactionsAdapter();



                    List<Transaction> transactions = viewModel.getAllTransactionsByWalletAndValueItemAndTypeForThePeriod(WalletConverter.WalletToString(wallet), ValueItemConverter.ValueItemToString(currValueItem), TypeConverter.TypeToString(type), DateConverter.dateToTimestamp(startOfThePeriod),DateConverter.dateToTimestamp(endOfThePeriod));

                    adapter.setTransactions(transactions);

                    LinearLayoutManager manager = new LinearLayoutManager(tableView.getContext());
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerViewTransactions.setLayoutManager(manager);
                    recyclerViewTransactions.setAdapter(adapter);


                    bottomSheetDialog.setContentView(bottomSheetView);



                    bottomSheetDialog.show();
                }








            }
        });



//        tableView.setDataAdapter(adapter);
    }


    public void setTableData(List<String[]> tableData) {
        this.tableData = tableData;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
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
}
