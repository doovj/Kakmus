package com.doovj.kakmus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.doovj.kakmus.DatabaseContract.Kamus.ARTI;
import static com.doovj.kakmus.DatabaseContract.Kamus.KATA;
import static com.doovj.kakmus.DatabaseContract.TABLE_ENG_INDO;
import static com.doovj.kakmus.DatabaseContract.TABLE_INDO_ENG;

public class KamusHelper {
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<KamusModel> getDataByKata(String kata, boolean isEnglish) {
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        Cursor cursor = database.query(TABLE, null, KATA+" LIKE ?", new String[]{kata}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusModel> getAllData(boolean isEnglish) {
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        Cursor cursor = database.query(TABLE, null, null, null, null, null, _ID+ " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount()>0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                kamusModel.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(kamusModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(KamusModel kamusModel, boolean isEnglish) {
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA, kamusModel.getKata());
        initialValues.put(ARTI, kamusModel.getArti());
        return database.insert(TABLE, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(ArrayList<KamusModel> kamusModel, boolean isEnglish) {
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        String sql = "INSERT INTO "+TABLE+" ("+KATA+", "+ARTI+") VALUES (?, ?)";
        beginTransaction();
        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < kamusModel.size(); i++) {
            stmt.bindString(1, kamusModel.get(i).getKata());
            stmt.bindString(2, kamusModel.get(i).getArti());
            stmt.execute();
            stmt.clearBindings();
        }
        setTransactionSuccess();
        endTransaction();
    }

    public void update(KamusModel kamusModel, boolean isEnglish) {
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        ContentValues args = new ContentValues();
        args.put(KATA, kamusModel.getKata());
        args.put(ARTI, kamusModel.getArti());
        database.update(TABLE, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    public int delete(int id, boolean isEnglish){
        String TABLE = isEnglish ? TABLE_ENG_INDO : TABLE_INDO_ENG;
        return database.delete(TABLE, _ID + " = '"+id+"'", null);
    }
}
