package com.app.jane;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

    final private static Integer VERSION =1;

    public DatabaseHelper(Context context)
    {
        super(context,"",null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlLiteDatabase)
    {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqlLiteDatabase,int i,int j)
    {

    }
}
