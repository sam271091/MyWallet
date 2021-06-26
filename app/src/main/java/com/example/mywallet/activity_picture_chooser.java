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
//        picturesArray.add(getResources().getResourceName(R.drawable.add));
//        picturesArray.add(getResources().getResourceName(R.drawable.analytics));
//        picturesArray.add(getResources().getResourceName(R.drawable.download_24));
        picturesArray.add(getResources().getResourceName(R.drawable.products_48));
        picturesArray.add(getResources().getResourceName(R.drawable.internetprovider_48));
        picturesArray.add(getResources().getResourceName(R.drawable.tv_48));
        picturesArray.add(getResources().getResourceName(R.drawable.water_48));
        picturesArray.add(getResources().getResourceName(R.drawable.gas_48));
        picturesArray.add(getResources().getResourceName(R.drawable.central_heating_48));
        picturesArray.add(getResources().getResourceName(R.drawable.mobile_phone_48));
        picturesArray.add(getResources().getResourceName(R.drawable.work_48));
        picturesArray.add(getResources().getResourceName(R.drawable.system_info_48));
        picturesArray.add(getResources().getResourceName(R.drawable.sports_48));
        picturesArray.add(getResources().getResourceName(R.drawable.netflix_48));
        picturesArray.add(getResources().getResourceName(R.drawable.music_48));
        picturesArray.add(getResources().getResourceName(R.drawable.car_48));
        picturesArray.add(getResources().getResourceName(R.drawable.taxi_48));
        picturesArray.add(getResources().getResourceName(R.drawable.birthday_48));
        picturesArray.add(getResources().getResourceName(R.drawable.christmas_tree_48));
        picturesArray.add(getResources().getResourceName(R.drawable.friends_48));
        picturesArray.add(getResources().getResourceName(R.drawable.colleagues_48));
        picturesArray.add(getResources().getResourceName(R.drawable.income_48));
        picturesArray.add(getResources().getResourceName(R.drawable.tax_48));

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