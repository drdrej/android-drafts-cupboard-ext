package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.NoDataCursor;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 26.04.2017.
 */
public class CupboardBasedQueryDbCommand extends DbCommand {

    public CupboardBasedQueryDbCommand(final SQLiteOpenHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public Cursor exec(final Enum contract,
                       final Uri uri,
                       final String[] projection,
                       final String selection,
                       final String[] selectionArgs,
                       final String sortOrder
    ) {
        final SQLiteDatabase db = readble();
        final Class<? extends Object> clz = EnumTool.withEnum(contract)
                .annotation(UriMatcherContract.class)
                .operations().query().entity();

        if( clz == Void.class ) { //
            if( Tracer.isDevMode() ) {
                throw new IllegalStateException( "Couldn'T execute query, missing entity (is Void.class) in [enum = " + contract.name() + "]" );
            } else {
                return new NoDataCursor();
            }
        }

        return cupboard().withDatabase(db).query(clz).
                withProjection(projection).
                withSelection(selection, selectionArgs).
                orderBy(sortOrder).
                getCursor();
    }
}
