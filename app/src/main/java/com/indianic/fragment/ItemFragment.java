package com.indianic.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.indianic.R;
import com.indianic.adapter.ItemsAdapter;
import com.indianic.model.BannerModel;
import com.indianic.model.HomeDataModel;

import java.util.ArrayList;

/**
 * Sample class displaying the items.
 */

public class ItemFragment extends BaseFragment implements ItemsAdapter.OnItemClickListener {

    @Override
    public void reloadData() {

    }

    @Override
    protected void initializeComponent(View view) {
        final RecyclerView rvItemList = view.findViewById(R.id.fragment_item_rvItemList);
        rvItemList.setLayoutManager(new LinearLayoutManager(getActivity()));
        final ItemsAdapter itemsAdapter = new ItemsAdapter(getActivity(), setDummyData(), this);
        rvItemList.setAdapter(itemsAdapter);
    }

    @Override
    protected int defineLayoutResource() {
        return R.layout.fragment_item;
    }

    private ArrayList<BannerModel> setDummyData() {

        final ArrayList<BannerModel> bannerArrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bannerArrayList.add(new BannerModel("Item " + (i + 1)));
        }
        return bannerArrayList;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Item Clicked : " + position, Toast.LENGTH_SHORT).show();
    }
}
