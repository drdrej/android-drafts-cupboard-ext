package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by asiebert on 26.04.2017.
 */
public abstract class DbCommand {

    private final SQLiteOpenHelper dbHelper;

    public DbCommand(final SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase writable() {
        return dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase readble() {
        return dbHelper.getReadableDatabase();
    }

    public abstract Cursor exec(final Enum contract,
                       final Uri uri,
                       final String[] projection,
                       final String selection,
                       final String[] selectionArgs,
                       final String sortOrder
    );

}
