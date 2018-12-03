package com.doovj.kakmus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.doovj.kakmus.DatabaseContract.Kamus.KATA;
import static com.doovj.kakmus.DatabaseContract.Kamus.ARTI;
import static com.doovj.kakmus.DatabaseContract.TABLE_ENG_INDO;
import static com.doovj.kakmus.DatabaseContract.TABLE_INDO_ENG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_INDO_ENG = "create table "+TABLE_INDO_ENG+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            ARTI+" text not null);";

    public static String CREATE_TABLE_ENG_INDO = "create table "+TABLE_ENG_INDO+
            " ("+_ID+" integer primary key autoincrement, " +
            KATA+" text not null, " +
            ARTI+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO_ENG);
        db.execSQL(CREATE_TABLE_ENG_INDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_INDO_ENG);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENG_INDO);
        onCreate(db);
    }
}
