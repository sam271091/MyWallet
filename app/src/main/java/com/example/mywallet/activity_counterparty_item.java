package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_counterparty_item extends AppCompatActivity {
    private MainViewModel viewModel;
    private EditText editTextCounterpartyName;
    private EditText editTextCounterpartyComment;
    private Counterparty currCounterparty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counterparty_item);

        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        editTextCounterpartyName = findViewById(R.id.editTextCounterpartyName);
        editTextCounterpartyComment = findViewById(R.id.editTextCounterpartyComment);

    }

    public void onClickValueItemSave(View view) {

        String name = editTextCounterpartyName.getText().toString().trim();
        String comment = editTextCounterpartyComment.getText().toString().trim();

        if (!name.equals("")) {
            if (currCounterparty == null){
                Counterparty newCounterparty = new Counterparty(name,comment);

                viewModel.insertCounterparty(newCounterparty);
            } else if (! currCounterparty.getName().equals(name)){
                currCounterparty.setName(name);
                viewModel.updateCounterparty(currCounterparty);
            }
            finish();
        } else {
        Toast.makeText(this, R.string.empty_name_warning, Toast.LENGTH_SHORT).show();
    }
    }
}