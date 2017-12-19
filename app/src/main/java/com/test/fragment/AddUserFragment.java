package com.test.fragment;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.test.R;
import com.test.database.DatabaseHelper;
import com.test.database.UserKey;
import com.test.model.UserModel;
import com.test.util.NetworkUtils;
import com.test.util.Utils;

import java.util.Hashtable;

/**
 * Sample class displaying the items.
 */

public class AddUserFragment extends BaseFragment {


    private AppCompatEditText etName;
    private AppCompatEditText etDesc;
    private AppCompatButton btnAddUser;
    private TextView tvTitle;
    private UserModel userModel;
    private DatabaseHelper dbHelper;

    @Override
    protected void initializeComponent(View view) {
        etName = view.findViewById(R.id.fragment_add_user_etName);
        etDesc = view.findViewById(R.id.fragment_add_user_etDescription);
        btnAddUser = view.findViewById(R.id.fragment_add_user_btnAddUser);
        tvTitle = view.findViewById(R.id.header_tvTitle);

        btnAddUser.setOnClickListener(this);
        setTitle();
        dbHelper = new DatabaseHelper(getActivity());
    }

    private void setTitle() {
        tvTitle.setText(getString(R.string.title_add_user));
    }

    @Override
    protected int defineLayoutResource() {
        return R.layout.fragment_add_user;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btnAddUser) {
            validateAddUser();
        }
    }

    private void validateAddUser() {
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            Utils.showSnackBar(getActivity(), getView(), "Enter user name", getString(R.string.app_name), "", null);
        } else if (TextUtils.isEmpty(etDesc.getText().toString().trim())) {
            Utils.showSnackBar(getActivity(), getView(), "Enter user description", getString(R.string.app_name), "", null);
        } else {
            userModel = new UserModel(etName.getText().toString().trim(), etDesc.getText().toString().trim());
            Hashtable<String, String> tmp_mapFolder = new Hashtable<String, String>();
            tmp_mapFolder.clear();
            tmp_mapFolder.put(UserKey.NAME, "" + userModel.getName());
            tmp_mapFolder.put(UserKey.DESCRIPTION, "" + userModel.getDesc());
            dbHelper.insertRecord(tmp_mapFolder, DatabaseHelper.TABLE_NAME_USERS, userModel);
            if (!NetworkUtils.isOnline(getActivity(), true, true, false)) {
                dbHelper.insertRecord(tmp_mapFolder, DatabaseHelper.TABLE_NAME_OFFLINE_USERS, userModel);
            }
            getFragmentManager().popBackStack();
        }
    }


}
