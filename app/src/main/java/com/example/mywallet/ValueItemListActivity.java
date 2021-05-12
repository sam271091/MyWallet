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

import com.example.mywallet.adapters.ValueItemsAdapter;

import java.util.List;

public class ValueItemListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewValueItems;
    ValueItemsAdapter adapter;
    MainViewModel viewModel;
    boolean notForResult;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_item_list);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        setTitle(getString(R.string.label_value_items));

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


        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra("notForResult")){
                notForResult = true;
            }
        }


        adapter.setOnValueItemClickListener(new ValueItemsAdapter.OnValueItemClickListener() {
            @Override
            public void OnValueItemClick(int position) {
                List<ValueItem> valueItems = adapter.getValueItems();
                if (notForResult == false) {
                Intent intent = new Intent();
                intent.putExtra("valueItem",  valueItems.get(position));
                setResult(RESULT_OK, intent);
                finish();
                } else {
                    OpenValueItem(valueItems.get(position));
                }
            }

            @Override
            public void OnLongClick(int position) {
                List<ValueItem> valueItems = adapter.getValueItems();
                ValueItem valueItem = valueItems.get(position);
                OpenValueItem(valueItem);
            }
        });

    }

    void OpenValueItem(ValueItem valueItem){
        Intent intent = new Intent(this,activity_value_item.class);
        intent.putExtra("curr_valueItem",valueItem);
        startActivity(intent);
    }


    public void onClickAddValueItem(View view) {
        Intent intent = new Intent(this,activity_value_item.class);
        startActivity(intent);

    }
}