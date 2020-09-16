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

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private final Context context;
    private List<Plant> items;
    private PlantAdapter.PostItemListener itemListener;

    private FavoriteClickListener favoriteClickListener;


    public PlantAdapter(Context context, List<Plant> items, PlantAdapter.PostItemListener itemListener, FavoriteClickListener favoriteClickListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_item, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));

    }

    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addPlant(List<Plant> item) {
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

        PostItemListener itemListener;

        @BindView(R.id.item_plant_image)
        ImageView plantImage;

        @BindView(R.id.item_plant_title)
        TextView plantTitle;

        @BindView(R.id.item_plant_fav)
        CheckBox plantFav;

        int checked;


        public ViewHolder(@NonNull View itemView, PlantAdapter.PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Plant plant) {
            plantTitle.setText(plant.getName());
            Glide.with(itemView.getContext())
                    .load(plant.getImageUrl())
//                    .fitCenter()
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
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
        public void onClick(View v) {
            Plant item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getSlug(), item.getName());
            notifyDataSetChanged();
        }
    }
}
