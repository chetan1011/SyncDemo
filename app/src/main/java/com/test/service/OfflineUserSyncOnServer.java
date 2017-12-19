package com.test.service;


import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import com.test.database.DatabaseHelper;
import com.test.model.UserModel;
import com.test.util.DisplayDialog;
import com.test.webservice.okhttp.WSAddUser;
import com.test.webservice.okhttp.WSConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OfflineUserSyncOnServer extends IntentService {

    private ArrayList<UserModel> conatacsmodel;
    private DatabaseHelper dbHelper;

    public OfflineUserSyncOnServer() {
        super("OfflineUserSyncOnServer");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        dbHelper = new DatabaseHelper(getApplicationContext());
        conatacsmodel = new ArrayList<UserModel>();

        try {
            conatacsmodel = dbHelper.getOfflineUserList();

            if (conatacsmodel.size() > 0) {
                new AsyncAddUser(generateJsonReq(getSelectContactsArr(conatacsmodel))).execute();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /****************************************************************************
     * @MethoddName:getSelectContactsArr
     * @CreatedDate:
     * @ModifiedBy: not yet
     * @ModifiedDate: not yet
     * @purpose:This Method use to create seprate Arrary from conatcs model.Return on all arrary in JsonArray
     ***************************************************************************/


    private JSONArray getSelectContactsArr(ArrayList<UserModel> conatacsmodel) {
        JSONArray selcetArr = new JSONArray();


        for (int i = 0; i < conatacsmodel.size(); i++) {

            JSONObject jsonObjectReq = new JSONObject();

            try {

                jsonObjectReq.put(WSConstants.API_PARAM_NAME, conatacsmodel.get(i).getName());
                jsonObjectReq.put(WSConstants.API_PARAM_DESCRIPTION, "" + conatacsmodel.get(i).getDesc());


                selcetArr.put(jsonObjectReq);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return selcetArr;
    }

    /****************************************************************************
     * @MethoddName:generateJsonReq
     * @CreatedDate:
     * @ModifiedBy: not yet
     * @ModifiedDate: not yet
     * @purpose:This Method use to generate Json request.
     ***************************************************************************/
    public String generateJsonReq(final JSONArray arr) {

        JSONObject jsonObjectReq = new JSONObject();
        JSONObject jsonMainObjectReq = new JSONObject();


        try {

            jsonObjectReq.put("brand", arr);

            jsonMainObjectReq.put("data", jsonObjectReq.toString());


            //Log.e("generateJsonReq", "jsonObjectReq" + jsonObjectReq.toString());
            System.out.println("generateJsonReq" + jsonMainObjectReq.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonMainObjectReq.toString();
    }


    /**
     * Async Task for get Block user List
     */
    private class AsyncAddUser extends AsyncTask<Void, Void, ArrayList<UserModel>> {
        private WSAddUser wsAddUser;
        private String userModel;

        public AsyncAddUser(String userModel) {
            this.userModel = userModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected ArrayList<UserModel> doInBackground(Void... params) {
            try {
                wsAddUser = new WSAddUser(getApplicationContext());
                wsAddUser.executeService(userModel);
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
                if (wsAddUser.getCode() == WSConstants.STATUS_SUCCESS) {
                    dbHelper.deleteAll(DatabaseHelper.TABLE_NAME_OFFLINE_USERS);
                }
            }
        }
    }
}
