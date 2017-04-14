package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 23.02.16.
 */
public class CupboardSQLiteDBHelper extends SQLiteOpenHelper {

    // TODO: diese Stellen müssen verallgemeinert werden.
    private static final String DATABASE_NAME = "mr.example.db";
    private static final int DATABASE_VERSION = 3;

    static {
        // example: register entities?
        /* cupboard().register(TrackEntity.class); */
        /* cupboard().register(EndpointEntity.class); */
    }

    public CupboardSQLiteDBHelper(Context ctx) {
        // todo: remove hardcoded part.
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).dropAllTables();
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dev mode:
        cupboard().withDatabase(db).dropAllTables();
        cupboard().withDatabase(db).createTables();

        // cupboard().withDatabase(db).upgradeTables();
    }

}