package com.ydh.botanic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Plant;
import com.ydh.botanic.ui.FavoriteClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.ViewHolder>  {

    private final Context context;
    private List<Plant> items;
    private PlantListAdapter.PostItemListener itemListener;

    private FavoriteClickListener favoriteClickListener;

    public PlantListAdapter(Context context, List<Plant> items , PlantListAdapter.PostItemListener itemListener, FavoriteClickListener favoriteClickListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_list_item, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantListAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addPlants(List<Plant> item) {
        items.addAll(item);
        notifyDataSetChanged();
    }


    private Plant getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String slug, String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_plant_list_title)
        TextView plantName;

        @BindView(R.id.item_plant_list_image)
        ImageView plantImage;

        @BindView(R.id.item_plant_list_local)
        TextView plantLocal;

        @BindView(R.id.item_plant_fav)
        CheckBox plantFav;

        int checked;

        PostItemListener itemListener;

        public ViewHolder(@NonNull View itemView, PlantListAdapter.PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Plant plant) {
            plantName.setText(plant.getName());
            plantLocal.setText(plant.getLocal());
            Glide.with(itemView.getContext())
                    .load(plant.getImageUrl())
                    .transform(new RoundedCorners(30))
                    .into(plantImage);
            plantFav.setChecked(plant.getIsFavourite());
            plantFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checked = 0;
                    if (plantFav.isChecked()){
                        checked = 1;
                    }

                    favoriteClickListener.onFavoriteClick(items.get(getAdapterPosition()).getSlug(), checked);

                }
            });
        }

        @Override
        public void onClick(View view) {
            Plant item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getSlug(), item.getName());
            notifyDataSetChanged();
        }
    }
}
