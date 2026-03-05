package com.example.fastfood;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Restaurant> restaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Restaurant List");

        loadRestaurants();
    }

    private List<Restaurant> loadRestaurants() {
        try {
            InputStream is = getResources().openRawResource(R.raw.restaurant);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Restaurant r = new Restaurant();
                r.setName(obj.getString("name"));
                r.setAddress(obj.getString("address"));
                r.setDelivery_charge((float) obj.getDouble("delivery_charge"));
                r.setImage(obj.getString("image"));

                // Hours
                JSONObject hoursObj = obj.getJSONObject("hours");
                Hours h = new Hours();
                h.setSunday(hoursObj.getString("Sunday"));
                h.setMonday(hoursObj.getString("Monday"));
                h.setTuesday(hoursObj.getString("Tuesday"));
                h.setWednesday(hoursObj.getString("Wednesday"));
                h.setThursday(hoursObj.getString("Thursday"));
                h.setFriday(hoursObj.getString("Friday"));
                h.setSaturday(hoursObj.getString("Saturday"));
                r.setHours(h);

                // Menus
                r.setMenus(new ArrayList<>());
                JSONArray menus = obj.getJSONArray("menus");
                for (int j = 0; j < menus.length(); j++) {
                    JSONObject m = menus.getJSONObject(j);
                    Menu item = new Menu();
                    item.setName(m.getString("name"));
                    item.setPrice((float) m.getDouble("price"));
                    item.setUrl(m.getString("url"));
                    r.getMenus().add(item);
                }
                restaurants.add(r);
                android.util.Log.d("Restaurant", r.getName() + " - " + r.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}