package com.ydh.botanic.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Plant;

public class PlantViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

    TextView name;
    ImageView image;
    OnPlantListener onPlantListener;
    RequestManager requestManager;
    ViewPreloadSizeProvider viewPreloadSizeProvider;


    public PlantViewHolder(@NonNull View itemView,
                           OnPlantListener onPlantListener,
                           RequestManager requestManager,
                           ViewPreloadSizeProvider preloadSizeProvider) {
        super(itemView);
        this.onPlantListener = onPlantListener;
        this.requestManager = requestManager;
        this.viewPreloadSizeProvider = preloadSizeProvider;
        name = itemView.findViewById(R.id.garden_title);
        image = itemView.findViewById(R.id.image_garden);
        itemView.setOnClickListener(this);

    }

    public void onBind(Plant plant){
        requestManager
                .load(plant.getImageUrl())
                .into(image);
        name.setText(plant.getName());
        viewPreloadSizeProvider.setView(image);
    }

    @Override
    public void onClick(View v) {
        onPlantListener.onPlantClick(getAdapterPosition());
    }
}
