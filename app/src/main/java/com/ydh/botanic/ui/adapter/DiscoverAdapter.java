package com.ydh.botanic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.botanic.models.Plant;
import com.ydh.botanic.R;
//import com.ydh.botanic.models.Plant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import static com.example.watchid.Utils.ApiUtils.IMAGE_URL;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder> {

    private final Context context;
    private  List<Plant> items;
    private  PostItemListener itemListener;

    public DiscoverAdapter(Context context, List<Plant> items , PostItemListener itemListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_item, parent, false);
        return new ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addPlant(List<Plant> item) {
        items = item;
        notifyDataSetChanged();
    }


    private Plant getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String slug, String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         PostItemListener itemListener;
//         @BindView(R.id.discoverImage)
//         ImageView discoverImage;
//         @BindView(R.id.discoverRating)
//         RatingBar discoverRating;
         @BindView(R.id.discoverTitle)
        TextView discoverTitle;

        public ViewHolder(@NonNull View itemView, PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Plant plant) {

//            double score = movie.getRating() / 2;

//            discoverRating.setRating((float) score);
            discoverTitle.setText(plant.getName());
//            Glide.with(itemView.getContext())
//                    .load(IMAGE_URL + movie.getBackdropPath())
//                    .transform(new RoundedCorners(30))
//                    .into(discoverImage);
        }




        @Override
        public void onClick(View view) {
            Plant item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getSlug(), item.getName());
            notifyDataSetChanged();
        }
    }
}
