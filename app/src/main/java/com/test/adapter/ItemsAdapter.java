package com.test.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.R;
import com.test.fragment.UserListFragment;
import com.test.model.UserModel;
import com.test.util.Constants;

import java.util.ArrayList;

/**
 * Recycler view adapter for items list
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

    private Context context;
    private ArrayList<UserModel> itemsList;
    private OnItemClickListener itemClickListener;

    private long lastClickedTime;

    public ItemsAdapter(final Context context, final ArrayList<UserModel> itemsList, final UserListFragment itemClickListener) {
        this.context = context;
        this.itemsList = itemsList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final UserModel model = itemsList.get(position);
        holder.itemView.setTag(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvDesc, tvCreated;

        ItemHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.row_item_tvName);
            tvDesc = itemView.findViewById(R.id.row_item_tvDesc);
            tvCreated = itemView.findViewById(R.id.row_item_tvCreated);
        }

        void bind(final UserModel model) {
            tvTitle.setText(model.getName());
            tvDesc.setText(model.getDesc());
            tvCreated.setText(model.getCreatedAt());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            /*
            * Logic to Prevent the Launch of the Fragment Twice if User makes
            * the Tap(Click) very Fast.
            */
            if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {

                return;
            }
            lastClickedTime = SystemClock.elapsedRealtime();

            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
