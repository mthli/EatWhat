package io.github.mthli.EatWhat.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RDBAction {
    private RDBHelper rdbHelper;
    private SQLiteDatabase database;

    public void RDBAction(Context context) {
        rdbHelper = new RDBHelper(context);
    }

    public void openDatabase() throws SQLException {
            database = rdbHelper.getReadableDatabase();
    }

    public void closeDatabase() {
        rdbHelper.close();
    }

    public List<Restaurant> restaurantList() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Cursor cursor = database.query(
                Restaurant.TABLE,
                new String[] {
                        Restaurant.RESTAURANT,
                        Restaurant.PATH
                },
                null,
                null,
                null,
                null,
                Restaurant.RESTAURANT
        );
        if (cursor == null) {
            return restaurants;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Restaurant restaurant = readRestaurant(cursor);
            restaurants.add(restaurant);
            cursor.moveToNext();
        }
        cursor.close();
        return restaurants;
    }

    private Restaurant readRestaurant(Cursor cursor) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant(cursor.getString(0));
        restaurant.setPath(cursor.getString(1));
        return restaurant;
    }
}
