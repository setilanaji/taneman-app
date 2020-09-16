package com.ydh.botanic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ydh.botanic.R;
import com.ydh.botanic.models.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private final Context context;
    private List<Category> items;
    private CategoryListAdapter.PostItemListener itemListener;

    public CategoryListAdapter(Context context, List<Category> items , CategoryListAdapter.PostItemListener itemListener) {
        this.context = context;
        this.items = items;
        this.itemListener = itemListener;
    }


    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list_item, parent, false);
        return new CategoryListAdapter.ViewHolder(view, this.itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addCategory(List<Category> item) {
        items.addAll(item);
        notifyDataSetChanged();
    }


    private Category getItem(int adapterPosition) {
        return items.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(String slug, String name, int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CategoryListAdapter.PostItemListener itemListener;

        @BindView(R.id.textView_category_name_list_item)
        TextView categoryName;

        @BindView(R.id.imageView_category_list_item)
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView, CategoryListAdapter.PostItemListener itemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
        }

        void bind(Category category) {

            categoryName.setText(category.getName());
            Glide.with(itemView.getContext())
                    .load(category.getImage())
                    .transform(new RoundedCorners(30))
                    .into(categoryImage);
        }

        @Override
        public void onClick(View view) {
            Category item = getItem(getAdapterPosition());
            this.itemListener.onPostClick(item.getSlug(), item.getName(), item.getCategoryId());
            notifyDataSetChanged();
        }
    }
}
