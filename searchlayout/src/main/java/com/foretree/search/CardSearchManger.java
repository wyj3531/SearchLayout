package com.foretree.search;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silen on 11/08/2017.
 */

public class CardSearchManger {
    private Context context;
    private static CardSearchManger mManger;
    private SearchHelper mDbHelper;

    private CardSearchManger(Context context) {
    }

    public static CardSearchManger getInstance(Context context) {
        if (mManger == null)
            mManger = new CardSearchManger(context);
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
        do {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            names.add(name);
        } while (cursor.moveToNext());
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
