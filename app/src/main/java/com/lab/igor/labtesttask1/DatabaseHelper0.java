package com.lab.igor.labtesttask1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Igor on 05-Apr-18.
 */

public class DatabaseHelper0 extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "drug_scheme.db";
    private Context myContext = null;
    private SQLiteDatabase myDataBase;



    /**
     *
     * @param context
     */
    public DatabaseHelper0(Context context) {
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        else
            DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";

        myContext = context;
    }

    /*
     * Creates an empty database in the system and rewrites it with your own
     *
     */

    public void createDataBase() {
        try {
            boolean dbExist = checkDataBase();

            if (dbExist) {
                // do nothing - database already exists
            } else {
                this.getReadableDatabase();
                copyDataBase();
            }
        } catch (Exception e) {

        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH+DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does not exist yet
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() {
        try {
            // Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH+DB_NAME;

            // Open the empty database as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myInput.close();
            myOutput.flush();
            myOutput.close();
        } catch (Exception e) {
            // catch exceptions
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {

        // Open database
        String myPath = DB_PATH+DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase;

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }

        super.close();
    }

    // Select All Data from one table
   /* public List<String> getAllInfo() {
        List<String> temp = new ArrayList<String>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = database.rawQuery("SELECT name FROM drug_interactions", null);
            if (cursor == null) return null;

            cursor.moveToFirst();
        }
    }*/
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
