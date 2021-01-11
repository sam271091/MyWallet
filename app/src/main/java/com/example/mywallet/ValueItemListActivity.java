package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mywallet.adapters.ValueItemsAdapter;

import java.util.List;

public class ValueItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewValueItems;
    ValueItemsAdapter adapter;
    MainViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_item_list);

        recyclerViewValueItems = findViewById(R.id.recyclerViewValueItems);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        adapter = new ValueItemsAdapter();

        viewModel.getValueItems().observe(this, new Observer<List<ValueItem>>() {
            @Override
            public void onChanged(List<ValueItem> valueItems) {
                adapter.setValueItems(valueItems);
            }
        });


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewValueItems.setLayoutManager(manager);
        recyclerViewValueItems.setAdapter(adapter);


        adapter.setOnValueItemClickListener(new ValueItemsAdapter.OnValueItemClickListener() {
            @Override
            public void OnValueItemClick(int position) {
                List<ValueItem> valueItems = adapter.getValueItems();
                Intent intent = new Intent();
                intent.putExtra("valueItem",  valueItems.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void onClickAddValueItem(View view) {
        Intent intent = new Intent(this,activity_value_item.class);
        startActivity(intent);

    }
}