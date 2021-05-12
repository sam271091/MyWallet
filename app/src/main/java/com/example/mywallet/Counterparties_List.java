package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    boolean notForResult;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparties__list);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.label_counterparties));

        recyclerViewCounterparties = findViewById(R.id.RecyclerViewCounterparties);
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
        adapter = new CounterpartiesAdapter();

        viewModel.getCounterparties().observe(this, new Observer<List<Counterparty>>() {
            @Override
            public void onChanged(List<Counterparty> counterparties) {
                adapter.setCounterparties(counterparties);
            }
        });


        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("notForResult")){
                notForResult = true;
            }
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCounterparties.setLayoutManager(manager);
        recyclerViewCounterparties.setAdapter(adapter);

        adapter.setOnCounterpartyClickListener(new CounterpartiesAdapter.OnCounterpartyClickListener() {
            @Override
            public void onCounterpartyClick(int position) {
                List<Counterparty> counterparties = adapter.getCounterparties();
                if (notForResult == false) {
                    Intent intent = new Intent();
                    intent.putExtra("counterparty",counterparties.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    OpenCounterparty(counterparties.get(position));
                }

            }

            @Override
            public void OnLongClick(int position) {
                List<Counterparty> counterparties = adapter.getCounterparties();
                Counterparty counterparty =  counterparties.get(position);
                OpenCounterparty(counterparty);

            }
        });
    }

    void OpenCounterparty(Counterparty counterparty){
        Intent intent = new Intent(this,activity_counterparty_item.class);
        intent.putExtra("curr_counterparty",counterparty);
        startActivity(intent);
    }

    public void onClickCounterparty(View view) {
        Intent intent = new Intent(this,activity_counterparty_item.class);
        startActivity(intent);
    }
}