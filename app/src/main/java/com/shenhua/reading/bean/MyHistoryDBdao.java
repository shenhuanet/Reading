package com.shenhua.reading.bean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shenhua.reading.utils.MyStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shenhua on 4/15/2016.
 */
public class MyHistoryDBdao {

    private Context context;
    private List<HistoryData> datas;
    private MyHistoryDBHelper dbHelper;
    private SQLiteDatabase sqldb = null;

    public MyHistoryDBdao(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new MyHistoryDBHelper(context);
        sqldb = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int getDBcount() {
        int count = 0;
        Cursor cursor = sqldb.rawQuery("Select count(*) from " + MyStringUtils.TABLE_NAME + ";", null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public List<HistoryData> getAllDatas() {
        Cursor cursor = sqldb.rawQuery("select * from " + MyStringUtils.TABLE_NAME + ";", null);
        datas = new ArrayList<HistoryData>();
        datas.clear();
        while (cursor.moveToNext()) {
            HistoryData data = new HistoryData();
            data.setTitle(cursor.getString(1));
            data.setTime(cursor.getString(2));
            data.setUrl(cursor.getString(3));
            data.setUrl(cursor.getString(4));
            data.setType(cursor.getInt(5));
            datas.add(data);
        }
        cursor.close();
        return datas;
    }

    public long insertTodb(HistoryData value) {
        ContentValues content = new ContentValues();
        content.put("_title", value.getTitle());
        content.put("_time", value.getTime());
        content.put("_url", value.getUrl());
        content.put("_describe", value.getDescribe());
        content.put("_type", value.getType());
        return sqldb.insert(MyStringUtils.TABLE_NAME, null, content);
    }

    public void updataTodb() {

    }

    public void deleteFromdb() {

    }
}
