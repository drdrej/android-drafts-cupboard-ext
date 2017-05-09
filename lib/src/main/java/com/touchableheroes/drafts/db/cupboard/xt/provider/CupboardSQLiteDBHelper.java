package com.touchableheroes.drafts.db.cupboard.xt.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.config.DbConfig;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.DbContract;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 23.02.16.
 */
public class CupboardSQLiteDBHelper extends SQLiteOpenHelper {

    private DbConfig config;

    CupboardSQLiteDBHelper(final Context ctx,
                                  final DbConfig config) {
        super(ctx, config.name(), null, config.version());
        this.config = config;
        bindEntities();
    }

    public CupboardSQLiteDBHelper(final Context ctx,
                                  final Class<? extends Enum> type) {
        this( ctx, createDbConfig( type ) );
    }

    private static DbConfig createDbConfig(final Class<? extends Enum> type) {
        final DbContract dbContract = EnumTool.withEnum(type).annotation(DbContract.class);

        return new DbConfig(
                type.getName(),
                dbContract.version(),
                dbContract.entities(),
                type
        );
    }


    private void bindEntities() {
        for( final Class<?> entity : config.entities() ) {
            cupboard().register( entity );
        }
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (Tracer.isDevMode()) {
            cupboard().withDatabase(db).dropAllTables();
            cupboard().withDatabase(db).createTables();
        } else {
            cupboard().withDatabase(db).upgradeTables();
        }
    }

}