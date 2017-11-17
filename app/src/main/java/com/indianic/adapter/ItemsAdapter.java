package com.indianic.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.indianic.R;
import com.indianic.model.BannerModel;
import com.indianic.util.Constants;
import com.indianic.util.imageloader.ImageLoader;
import com.indianic.util.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Recycler view adapter for items list
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<BannerModel> itemsList;
    private OnItemClickListener itemClickListener;

    private long lastClickedTime;

    public ItemsAdapter(final Context context, final ArrayList<BannerModel> itemsList, final OnItemClickListener itemClickListener) {
        this.context = context;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemsList = itemsList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(layoutInflater.inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final BannerModel model = itemsList.get(position);
        holder.itemView.setTag(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivOffer;
        TextView tvTitle;

        ItemHolder(View itemView) {
            super(itemView);
            ivOffer = itemView.findViewById(R.id.row_item_ivOffer);
            tvTitle = itemView.findViewById(R.id.row_item_tvTitle);
        }

        void bind(BannerModel model) {
            tvTitle.setText(model.getTitle());
            ImageLoader.loadImage(context, ivOffer, R.drawable.dummy_banner_image, R.drawable.placeholder_banner);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (!isDoubleTap()) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(position);
            }
        }
    }

    private boolean isDoubleTap() {
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {
            return true;
        }
        lastClickedTime = SystemClock.elapsedRealtime();
        return false;
    }
}
