package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.Iterator;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 23.02.16.
 */
public class CupboardSQLiteDBHelper extends SQLiteOpenHelper {

    private final DbConfig config;

    public CupboardSQLiteDBHelper(final Context ctx,
                                  final DbConfig config) {
        super(ctx, config.name(), null, config.version());

        this.config = config;
    }

    @Override
    public void onConfigure(final SQLiteDatabase db) {
        bindEntities( );
        super.onConfigure(db);
    }

    private void bindEntities() {
        for( final Class<?> entity : config.entities() ) {
            cupboard().register( entity );
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for ( final Class<?> entity : config.entities()) {
            cupboard().register( entity );
        }

//        cupboard().withDatabase(db).dropAllTables();
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // dev mode:
        cupboard().withDatabase(db).dropAllTables();
        cupboard().withDatabase(db).createTables();

        // prod mode:
        // cupboard().withDatabase(db).upgradeTables();
    }

}