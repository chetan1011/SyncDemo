package com.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.test.model.UserModel;
import com.test.util.Constants;
import com.test.util.Preference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

/****************************************************************************
 * @ClassdName:DatabaseHelper
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is use to Create database with Table and insert,update,delete Method with functionlity.
 ***************************************************************************/

public class DatabaseHelper extends SQLiteOpenHelper {
    private static Context mContext;

    // DATABASE NAME
    private static String DATABASE_NAME = "USER_DATABASE";

    // LIST TABLE NAME
    public static String TABLE_NAME_USERS = "users";
    public static String TABLE_NAME_OFFLINE_USERS = "AddOfflineUser";

    private static String TABLE_NAME = "";

    public DatabaseHelper(Context context, String data_name, String tab_name, Hashtable<String, String> column_pairs) {
        super(context, data_name, null, Constants.SQLITE_VERSION);
        mContext = context;
        TABLE_NAME = tab_name;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, Constants.SQLITE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        create_table_user(database);
        create_table_offline_user(database);
    }

    private void create_table_offline_user(SQLiteDatabase database) {
        Hashtable<String, String> tmp_contacts = new Hashtable<String, String>();
        tmp_contacts.put(UserKey.NAME, "TEXT");
        tmp_contacts.put(UserKey.DESCRIPTION, "TEXT");
        createtable(database, TABLE_NAME_OFFLINE_USERS, tmp_contacts);
    }

    private void create_table_user(SQLiteDatabase database) {
        Hashtable<String, String> tmp_contacts = new Hashtable<String, String>();
        tmp_contacts.put(UserKey.ID, "TEXT");
        tmp_contacts.put(UserKey.NAME, "TEXT");
        tmp_contacts.put(UserKey.DESCRIPTION, "TEXT");
        tmp_contacts.put(UserKey.CREATEDAT, "TEXT");

        createtable(database, TABLE_NAME_USERS, tmp_contacts);
    }

    private void createtable(SQLiteDatabase database, String tableName, Hashtable<String, String> tmp_contacts) {
        String CREATE_TABLE = "create table " + tableName + "(";

        for (String key : tmp_contacts.keySet()) {
            CREATE_TABLE = CREATE_TABLE + key + " " + tmp_contacts.get(key) + ",";
        }

        int len = CREATE_TABLE.length();
        CREATE_TABLE = CREATE_TABLE.substring(0, len - 1) + ")";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            try {

                Preference.getInstance().clearAllPreferenceData();


                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        onCreate(db);
    }

    /**
     * Open Databases
     * *
     */
    public void openDataBase() throws SQLException {
        this.getWritableDatabase();

    }

    public void insertRecord(Hashtable<String, String> queryValues, String TableName, UserModel userModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String key : queryValues.keySet()) {
            values.put(key, queryValues.get(key));
        }

        database.insert(TableName, null, values);
        database.close();


    }

    public void insertRecordTableBunch(ArrayList<Hashtable<String, String>> queryValues, String tableName) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //database.beginTransaction();
        database.beginTransactionNonExclusive();

        for (int i = 0; i < queryValues.size(); i++) {
            for (String key : queryValues.get(i).keySet()) {
                values.put(key, queryValues.get(i).get(key));

            }
            database.insert(tableName, null, values);
        }


        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }


    public ArrayList<UserModel> getUserList() {

        final ArrayList<UserModel> arrayList = new ArrayList<UserModel>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_NAME_USERS, new String[]{"*"}, null, null, null, null, UserKey.CREATEDAT);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                UserModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new UserModel();
                    model.setId(cursor.getString(cursor.getColumnIndex(UserKey.ID)));
                    model.setName(cursor.getString(cursor.getColumnIndex(UserKey.NAME)));
                    model.setDesc(cursor.getString(cursor.getColumnIndex(UserKey.DESCRIPTION)));
                    model.setCreatedAt(cursor.getString(cursor.getColumnIndex(UserKey.CREATEDAT)));

                    arrayList.add(model);
                    cursor.moveToNext();
                }
                arrayList.trimToSize();
            }
        } catch (Exception e) {

            Log.e("error in dbhelper", e.toString());
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return arrayList;
    }

    public ArrayList<UserModel> getOfflineUserList() {

        final ArrayList<UserModel> arrayList = new ArrayList<UserModel>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_NAME_OFFLINE_USERS, new String[]{"*"}, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                UserModel model = null;
                for (int i = 0; i < cursor.getCount(); i++) {
                    model = new UserModel();
                    model.setName(cursor.getString(cursor.getColumnIndex(UserKey.NAME)));
                    model.setDesc(cursor.getString(cursor.getColumnIndex(UserKey.DESCRIPTION)));

                    arrayList.add(model);
                    cursor.moveToNext();
                }
                arrayList.trimToSize();
            }
        } catch (Exception e) {

            Log.e("error in dbhelper", e.toString());
        } finally {

            close();
            if (cursor != null) {
                cursor.close();
                SQLiteDatabase.releaseMemory();
            }
        }
        return arrayList;
    }

    public void deleteAll(String TableName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TableName, null, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LastUpdateDateComparator implements Comparator<UserModel> {
        @Override
        public int compare(UserModel lhs, UserModel rhs) {
            return rhs.getCreatedAt().compareToIgnoreCase(lhs.getCreatedAt());
        }
    }
}
