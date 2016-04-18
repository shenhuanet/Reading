package com.shenhua.reading.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shenhua.reading.utils.MyStringUtils;

/**
 * Created by Shenhua on 4/15/2016.
 */
public class MyHistoryDBHelper extends SQLiteOpenHelper {

    public MyHistoryDBHelper(Context context) {
        super(context, MyStringUtils.DB_NAME, null, MyStringUtils.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_create = "create table "
                + MyStringUtils.TABLE_NAME
                + "("
                + "_id INTEGER primary key autoincrement,"
                + "_title TEXT,"
                + "_time TEXT,"
                + "_url TEXT,"
                + "_describe TEXT,"
                + "_type INTEGER"
                + ");";
        db.execSQL(sql_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyStringUtils.TABLE_NAME);
        onCreate(db);
    }
}
