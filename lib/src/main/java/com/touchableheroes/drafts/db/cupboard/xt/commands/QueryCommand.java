package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by asiebert on 26.04.2017.
 */
public abstract class QueryCommand {

    private final SQLiteOpenHelper dbHelper;

    public QueryCommand(final SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public SQLiteDatabase writable() {
        return dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase readble() {
        return dbHelper.getReadableDatabase();
    }

    /*
    public abstract Cursor exec(final Enum contract,
                       final Uri uri,
                       final StringSQLite[] projection,
                       final StringSQLite selection,
                       final StringSQLite[] selectionArgs,
                       final StringSQLite sortOrder
    );
    */

    public abstract Cursor exec(
            final Enum contract,
            final String[] projection,
            final String selection,
            final String[] selectionArgs,
            final String sortOrder);

}
