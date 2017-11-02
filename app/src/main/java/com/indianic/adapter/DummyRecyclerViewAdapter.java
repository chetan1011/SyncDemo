package com.indianic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.indianic.R;
import com.indianic.customview.OnItemClickListenerWrapper;
import com.indianic.customview.OnRecyclerItemClickListenerWrapper;
import com.indianic.model.BannerModel;

import java.util.List;

/**
 * Created by H.T. on 02/11/17.
 */

public class DummyRecyclerViewAdapter extends RecyclerView.Adapter<DummyRecyclerViewAdapter.DummyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListenerWrapper itemClickListener;

    private List<BannerModel> list;

    DummyRecyclerViewAdapter(Context context, List<BannerModel> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public DummyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DummyViewHolder(inflater.inflate(R.layout.row_dummy_list_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(DummyViewHolder holder, int position) {
        final BannerModel model = list.get(position);
        holder.tvTitle.setText(model.getTitle());

        final int clickPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(clickPosition);
                }
            }
        });
    }

    class DummyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        DummyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.row_dummy_tv_title);
        }
    }

    public void setItemClickListener(OnRecyclerItemClickListenerWrapper itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
