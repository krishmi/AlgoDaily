package com.example.algodaily;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AlgoDatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "AlgoListDB.db";
    private String dbPath = "";
    private static final String tableName = "Algorithms";
    private static final String pk_id = "Id";
    private static final String col_title = "Title";
    private static final String col_level = "Level";
    private static final String col_easyProblem = "easyProblem";
    private static final String col_mediumProblem = "mediumProblem";
    private static final String col_hardProblem = "hardProblem";
    private static final String col_status = "Status";
    private static int dbVersion = 1;
    private final Context myContext;
    private Cursor cursor = null;

    public AlgoDatabaseHelper(Context context)
    {
        super(context, dbName, null, dbVersion);
        myContext = context;
        dbPath = myContext.getDatabasePath(dbName).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // abstract method needs to be implemented
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        dbVersion = newVersion;
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = dbPath;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                                                  SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e)
        {
            Log.e("message", "" + e);
        }

        if (checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDataBase() throws IOException
    {
        InputStream myInput = myContext.getAssets().open(dbName);
        String outFileName = dbPath;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if(!dbExist)
        {
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                CharSequence text = "Error getting database";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(null, text, duration);
                toast.show();
            }
        }
    }

    public int getNumberOfAlgosAtLevel(String level)
    {
        String query = "SELECT * FROM " + tableName + " WHERE " + col_level + " = '" + level + "'";
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            int numOfAlgos = cursor.getCount();
            cursor.close();
            return numOfAlgos;
        }catch (Exception e)
        {
            CharSequence text = "Failed to get data from database, please restart the app";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(null, text, duration);
            toast.show();
        }

        return 0;
    }

    public void setAlgoNameAndLevel(TextView algoLevel, TextView algoName, TextView algoStatus, int position)
    {
        try
        {
            if(position == 0)
            {
                SQLiteDatabase db = this.getWritableDatabase();
                String query = "SELECT " + col_title + ", " + col_level + ", " + col_status + " FROM " + tableName;
                cursor = db.rawQuery(query, null);
                if(cursor.moveToFirst()) {
                    algoName.setText(cursor.getString(0));
                    algoLevel.setText(cursor.getString(1));
                    algoStatus.setText(cursor.getString(2));
                }
            }else {
                if(cursor.moveToNext()){
                    algoName.setText(cursor.getString(0));
                    algoLevel.setText(cursor.getString(1));
                    algoStatus.setText(cursor.getString(2));
                }
            }
        }
        catch (Exception e)
        {
            CharSequence text = "Failed to get data from database, please restart the app";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(null, text, duration);
            toast.show();
        }
    }

    public Algorithm getNextAlgo()
    {
        String query = "SELECT * FROM " + tableName + " WHERE STATUS != 'Done' ORDER BY " + pk_id + " LIMIT 1";
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst())
            {
                Algorithm algorithm = new Algorithm();
                algorithm.setCol_title(cursor.getString(0));
                algorithm.setCol_level(cursor.getString(1));
                algorithm.setCol_easyProblem(cursor.getString(2));
                algorithm.setCol_mediumProblem(cursor.getString(3));
                algorithm.setCol_hardProblem(cursor.getString(4));
                algorithm.setCol_article(cursor.getString(7));
                cursor.close();
                return algorithm;
            }
        }
        catch (SQLiteException e)
        {
            CharSequence text = "Failed to get data from database, please restart the app";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(null, text, duration);
            toast.show();
        }

        return null;
    }
}
