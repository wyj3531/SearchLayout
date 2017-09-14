package com.foretree.search;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silen on 11/08/2017.
 */

public class SearchManger {
    private Context context;
    private static SearchManger mManger;
    private SearchHelper mDbHelper;

    private SearchManger(Context context) {
    }

    public static SearchManger getInstance(Context context) {
        if (mManger == null)
            mManger = new SearchManger(context);
        mManger.context = context;
        if (mManger.mDbHelper == null)
            mManger.mDbHelper = new SearchHelper(context);
        return mManger;
    }

    public void closeDB() {
        mDbHelper.close();
    }

    public List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        if (!mDbHelper.isDbOpen()) mDbHelper.open();
        Cursor cursor = mDbHelper.queryAllNames();
        int count = cursor.getCount();
        if (count > 0) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                names.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
    public List<SearchModel> getAllSearchModels() {
        List<SearchModel> names = new ArrayList<>();
        if (!mDbHelper.isDbOpen()) mDbHelper.open();
        Cursor cursor = mDbHelper.queryAllNames();
        int count = cursor.getCount();
        if (count > 0) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                SearchModel searchModel = new SearchModel();
                searchModel.setName(name);
                names.add(searchModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    public void putName(String name) {
        if (!mDbHelper.isDbOpen()) mDbHelper.open();
        mDbHelper.createName(name);
    }

    public boolean clearAllNames() {
        if (!mDbHelper.isDbOpen()) mDbHelper.open();
        return mDbHelper.deleteAllNames();
    }
}
