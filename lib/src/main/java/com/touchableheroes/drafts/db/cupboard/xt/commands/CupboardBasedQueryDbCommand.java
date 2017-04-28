package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.NoDataCursor;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContractUtil;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;

import java.lang.annotation.Annotation;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 26.04.2017.
 */
public class CupboardBasedQueryDbCommand
        extends DbCommand {


    public CupboardBasedQueryDbCommand(final SQLiteOpenHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public Cursor exec(final Enum contract,
                       final String[] selectionArgs ) {
        final SQLiteDatabase db = readble();

        final UriMatcherContract uriContract = UriMatcherContractUtil.load( contract );
        final QueryContract query = uriContract.operations().query();
        final Class<? extends Object> clz = query.entity();

        if( clz == Void.class ) { //
            if( Tracer.isDevMode() ) {
                throw new IllegalStateException( "Couldn'T execute query, missing entity (is Void.class) in [enum = " + contract.name() + "]" );
            } else {
                return new NoDataCursor();
            }
        }

        return cupboard().withDatabase(db).query(clz).
                withProjection( query.projection() ).
                withSelection( query.selection(), selectionArgs).
                orderBy(query.orderBy()).
                getCursor();
    }
}
