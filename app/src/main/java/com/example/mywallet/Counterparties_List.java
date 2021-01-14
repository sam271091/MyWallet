package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mywallet.adapters.CounterpartiesAdapter;

import java.util.List;

public class Counterparties_List extends AppCompatActivity {

    private RecyclerView recyclerViewCounterparties;
    CounterpartiesAdapter adapter;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparties__list);

        recyclerViewCounterparties = findViewById(R.id.RecyclerViewCounterparties);
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
        adapter = new CounterpartiesAdapter();

        viewModel.getCounterparties().observe(this, new Observer<List<Counterparty>>() {
            @Override
            public void onChanged(List<Counterparty> counterparties) {
                adapter.setCounterparties(counterparties);
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCounterparties.setLayoutManager(manager);
        recyclerViewCounterparties.setAdapter(adapter);

        adapter.setOnCounterpartyClickListener(new CounterpartiesAdapter.OnCounterpartyClickListener() {
            @Override
            public void onCounterpartyClick(int position) {
                List<Counterparty> counterparties = adapter.getCounterparties();
                Intent intent = new Intent();
                intent.putExtra("counterparty",counterparties.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onClickCounterparty(View view) {
        Intent intent = new Intent(this,activity_counterparty_item.class);
        startActivity(intent);
    }
}