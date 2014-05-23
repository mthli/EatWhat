package io.github.mthli.EatWhat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UDBAction {
    private UDBHelper udbHelper;
    private SQLiteDatabase database;

    public UDBAction(Context context) {
        udbHelper = new UDBHelper(context);
    }

    public void openDatabase(boolean rw) {
        if (rw) {
            database = udbHelper.getWritableDatabase();
        } else {
            database = udbHelper.getReadableDatabase();
        }
    }

    public void closeDatabase() {
        udbHelper.close();
    }

    public void newRepo(Usual usual) {
        ContentValues values = new ContentValues();
        values.put(Usual.RESTAURANT, usual.getRestaurant());
        values.put(Usual.PATH, usual.getPath());
        database.insert(Usual.TABLE, null, values);
    }

    public void deleteRepo(Usual usual) {
        database.delete(
                Usual.TABLE,
                Usual.RESTAURANT + "=?",
                new String[] {usual.getRestaurant()}
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
