package com.example.fastfood;

import com.example.fastfood.model.Restaurant;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fastfood.adapters.RestaurantListAdapter;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListAdapter.OnRestaurantClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");
        // get restaurant list from json
        List<Restaurant> restaurantList = getRestaurantData();
        initRecyclerView(restaurantList);
    }

    private void initRecyclerView(List<Restaurant> restaurantList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(restaurantList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Restaurant> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.restaurant);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonStr = writer.toString();
        Gson gson = new Gson();
        Restaurant[] restaurants = gson.fromJson(jsonStr, Restaurant[].class);
        return Arrays.asList(restaurants);
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        // Navigation vers le détail - à implémenter plus tard
        Intent intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }
}