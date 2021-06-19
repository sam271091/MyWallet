package com.example.mywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mywallet.adapters.PicturesAdapter;

import java.util.ArrayList;

public class activity_picture_chooser extends AppCompatActivity {

    private PicturesAdapter adapter;
    private RecyclerView recyclerViewPictures;
    private ArrayList picturesArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_chooser);

        recyclerViewPictures = findViewById(R.id.recyclerViewPictures);



        picturesArray = new ArrayList();
        picturesArray.add(getResources().getResourceName(R.drawable.add));
        picturesArray.add(getResources().getResourceName(R.drawable.analytics));
        picturesArray.add(getResources().getResourceName(R.drawable.download_24));

        adapter = new PicturesAdapter(picturesArray);

        LinearLayoutManager manager = new GridLayoutManager(this,3);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewPictures.setLayoutManager(manager);
        recyclerViewPictures.setAdapter(adapter);


        adapter.setOnPictureClickListener(new PicturesAdapter.OnPictureClickListener() {
            @Override
            public void OnPicturelick(int position) {
                String picture = (String) picturesArray.get(position);

                Intent intent = new Intent();
                intent.putExtra("pictureID",  picture);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}