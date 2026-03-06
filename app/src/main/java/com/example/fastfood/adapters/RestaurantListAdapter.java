package com.example.fastfood.adapters;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood.R;
import com.example.fastfood.model.Restaurant;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    private List<Restaurant> restaurantList;
    private OnRestaurantClickListener clickListener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantListAdapter(List<Restaurant> restaurantList, OnRestaurantClickListener clickListener) {
        this.restaurantList = restaurantList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.address.setText(restaurant.getAddress());
        holder.restaurantHours.setText(restaurant.getHours().getTodaysHours());

        // Chargement image avec Glide
        Glide.with(holder.itemView.getContext())
                .load(restaurant.getImage())
                .into(holder.thumbImage);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        TextView  restaurantHours;
        ImageView thumbImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurantName);
            address = itemView.findViewById(R.id.restaurantAddress);
            restaurantHours = itemView.findViewById(R.id.restaurantHours);
            thumbImage = itemView.findViewById(R.id.thumbImage);
        }
    }
    public interface RestaurantListClickListener {
        public void onItemClick (Restaurant restaurant);    }

}
