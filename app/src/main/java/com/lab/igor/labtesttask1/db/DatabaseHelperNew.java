package com.lab.igor.labtesttask1.db;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DatabaseHelperNew extends SQLiteAssetHelper {

    private static final String DB_NAME = "drugDB.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelperNew databaseHelper;

    private DatabaseHelperNew(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseHelperNew getInstance(Context context) {
        Log.i("DB", "now here");
        if (Objects.isNull(databaseHelper)) {
            databaseHelper = new DatabaseHelperNew(context.getApplicationContext());
        }

        return databaseHelper;
    }



}
