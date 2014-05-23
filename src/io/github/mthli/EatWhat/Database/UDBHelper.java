package io.github.mthli.EatWhat.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "usual.db";
    private static final int DATABASE_VERSION = 1;

    public UDBHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Usual.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        /* Do Nothing */
    }
}
