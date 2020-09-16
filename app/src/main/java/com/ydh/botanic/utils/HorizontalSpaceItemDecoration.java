package com.ydh.botanic.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration{
    private final float horizontalSpaceHeight;

    public HorizontalSpaceItemDecoration(float horizontalSpaceHeight) {
        this.horizontalSpaceHeight = horizontalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() -  parent.getAdapter().getItemCount()) {
            outRect.left = Math.round(horizontalSpaceHeight);
        }
        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
            outRect.right = Math.round(horizontalSpaceHeight);;
        }

    }
}
