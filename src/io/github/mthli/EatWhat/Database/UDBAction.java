package io.github.mthli.EatWhat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UDBAction {
    private UDBHelper udbHelper;
    private SQLiteDatabase database;

    public UDBAction(Context context) {
        udbHelper = new UDBHelper(context);
    }

    public void openDatabase(boolean rw) throws SQLException {
        if (rw) {
            database = udbHelper.getWritableDatabase();
        } else {
            database = udbHelper.getReadableDatabase();
        }
    }

    public void closeDatabase() {
        udbHelper.close();
    }

    public void newUsual(Usual usual) {
        ContentValues values = new ContentValues();
        values.put(Usual.RESTAURANT, usual.getRestaurant());
        values.put(Usual.PATH, usual.getPath());
        database.insert(Usual.TABLE, null, values);
    }

    public boolean checkUsual(Usual usual) {
        Cursor cursor = database.query(
                Usual.TABLE,
                new String[] {Usual.RESTAURANT, Usual.PATH},
                Usual.RESTAURANT + " =?" + " AND " + Usual.PATH + " =?",
                new String[] {usual.getRestaurant(), usual.getPath()},
                null,
                null,
                null
        );
        if (cursor != null) {
            boolean result = false;
            if (cursor.moveToFirst()) {
                result = true;
            }
            cursor.close();
            return result;
        }
        return false;
    }

    public void deleteUsual(Usual usual) {
        database.delete(
                Usual.TABLE,
                Usual.RESTAURANT + " =?" + " AND " + Usual.PATH + " =?",
                new String[] {usual.getRestaurant(), usual.getPath()}
        );
    }

    public List<Usual> usualList() {
        List<Usual> usuals = new ArrayList<Usual>();
        Cursor cursor = database.query(
                Usual.TABLE,
                new String[] {
                        Usual.RESTAURANT,
                        Usual.PATH
                },
                null,
                null,
                null,
                null,
                Usual.RESTAURANT
        );
        if (cursor == null) {
            return usuals;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Usual usual = readUsual(cursor);
            usuals.add(usual);
            cursor.moveToNext();
        }
        cursor.close();
        return usuals;
    }

    private Usual readUsual(Cursor cursor) {
        Usual usual = new Usual();
        usual.setRestaurant(cursor.getString(0));
        usual.setPath(cursor.getString(1));
        return usual;
    }
}
