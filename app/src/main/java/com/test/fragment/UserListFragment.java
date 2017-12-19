package com.test.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.test.R;
import com.test.adapter.ItemsAdapter;
import com.test.database.DatabaseHelper;
import com.test.database.UserKey;
import com.test.model.UserModel;
import com.test.service.OfflineUserSyncOnServer;
import com.test.util.DisplayDialog;
import com.test.util.NetworkUtils;
import com.test.util.Preference;
import com.test.util.Utils;
import com.test.webservice.okhttp.WSConstants;
import com.test.webservice.okhttp.WSUserList;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Sample class displaying the items.
 */

public class UserListFragment extends BaseFragment implements ItemsAdapter.OnItemClickListener {


    private ArrayList<UserModel> userModelArrayList;
    private ItemsAdapter itemsAdapter;
    private RecyclerView rvItemList;
    private FloatingActionButton fabAddUser;
    private TextView tvEmptyMessage;
    private DatabaseHelper databaseHelper;

    private AsyncUserList asyncUserList;


    @Override
    protected void initializeComponent(View view) {
        userModelArrayList = new ArrayList<>(10);

        rvItemList = view.findViewById(R.id.fragment_userlist_rvItemList);
        fabAddUser = view.findViewById(R.id.fragment_userlist_fabAddUser);
        tvEmptyMessage = view.findViewById(R.id.fragment_userlist_tvEmptyMessage);
        rvItemList.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabAddUser.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(getActivity());
        if (databaseHelper.getOfflineUserList().size() > 0) {
            Intent mWISOfflineContactSyncOnServer = new Intent(getActivity(), OfflineUserSyncOnServer.class);
            getActivity().startService(mWISOfflineContactSyncOnServer);
        }
        callUserList();
    }

    @Override
    protected int defineLayoutResource() {
        return R.layout.fragment_userlist;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == fabAddUser) {
            addFragment(R.id.activity_home_flContainer, UserListFragment.this, new AddUserFragment(), true, false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            callUserList();
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Item Clicked : " + position, Toast.LENGTH_SHORT).show();
    }

    //This method is for call block user list
    private void callUserList() {
        userModelArrayList.clear();
        if (NetworkUtils.isOnline(getActivity(), true, true, false)) {
            if (asyncUserList != null && asyncUserList.getStatus() == AsyncTask.Status.PENDING) {
                asyncUserList.execute();
            } else if (asyncUserList == null || asyncUserList.getStatus() == AsyncTask.Status.FINISHED) {
                asyncUserList = new AsyncUserList();
                asyncUserList.execute();
            }
        } else {
            getDataFromLocalDatabase();
        }
    }

    private void getDataFromLocalDatabase() {
        userModelArrayList = databaseHelper.getUserList();
        if (userModelArrayList != null && userModelArrayList.size() > 0) {
            setAdapter(userModelArrayList);
        } else {
            setEmptyView();
        }
    }


    /**
     * Async Task for get Block user List
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncUserList extends AsyncTask<Void, Void, ArrayList<UserModel>> {
        private WSUserList wsUserList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            userModelArrayList.clear();
            Utils.hideSoftKeyBoard(getActivity(), getView());
            DisplayDialog.getInstance().showProgressDialog(getActivity(), getString(R.string.loading), false);

        }

        @Override
        protected ArrayList<UserModel> doInBackground(Void... params) {
            try {
                wsUserList = new WSUserList(getActivity());
                return wsUserList.executeService();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<UserModel> result) {
            super.onPostExecute(result);
            DisplayDialog.getInstance().dismissProgressDialog();
            if (!isCancelled()) {
                if (wsUserList.getCode() == WSConstants.STATUS_SUCCESS) {
                    if (result != null && result.size() > 0) {
                        userModelArrayList = result;
                        ArrayList<Hashtable<String, String>> arrHasMapList = new ArrayList<>();
                        for (UserModel userModel : userModelArrayList) {
                            Hashtable<String, String> tmp_mapFolder = new Hashtable<String, String>();
                            tmp_mapFolder.put(UserKey.ID, "" + userModel.getId());
                            tmp_mapFolder.put(UserKey.NAME, "" + userModel.getName());
                            tmp_mapFolder.put(UserKey.DESCRIPTION, "" + userModel.getDesc());
                            tmp_mapFolder.put(UserKey.CREATEDAT, "" + userModel.getCreatedAt());
                            arrHasMapList.add(tmp_mapFolder);
                        }
                        databaseHelper.insertRecordTableBunch(arrHasMapList, DatabaseHelper.TABLE_NAME_USERS);
                        getDataFromLocalDatabase();
                    } else {
                        setEmptyView();
                    }
                } else if (wsUserList.getMessage() != null && !wsUserList.getMessage().equalsIgnoreCase(String.valueOf(WSConstants.STATUS_SUCCESS))) {
                    setEmptyView();
                    Utils.showSnackBar(getActivity(), getView(), wsUserList.getMessage(), getString(R.string.app_name), "", null);

                }
            } else if (wsUserList.getMessage() != null && !wsUserList.getMessage().equalsIgnoreCase(String.valueOf(WSConstants.STATUS_SUCCESS))) {
                Utils.showSnackBar(getActivity(), getView(), wsUserList.getMessage(), getString(R.string.app_name), "", null);
            }
        }
    }

    private void setAdapter(ArrayList<UserModel> userModelArrayList) {
        Preference.getInstance().setData("lasttimestamp", "" + (System.currentTimeMillis() / 1000));
        itemsAdapter = new ItemsAdapter(getActivity(), userModelArrayList, UserListFragment.this);
        rvItemList.setAdapter(itemsAdapter);
        rvItemList.setVisibility(View.VISIBLE);
        tvEmptyMessage.setVisibility(View.GONE);
    }

    private void setEmptyView() {
        rvItemList.setVisibility(View.GONE);
        tvEmptyMessage.setVisibility(View.VISIBLE);
    }
}
