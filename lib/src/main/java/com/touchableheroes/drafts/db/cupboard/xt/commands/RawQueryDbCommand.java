package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.NoDataCursor;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContractUtil;

/**
 * Created by asiebert on 27.04.2017.
 */
public class RawQueryDbCommand extends DbCommand {

    public RawQueryDbCommand(final SQLiteOpenHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public Cursor exec(final Enum contract,
                       final String[] args) {

        final UriMatcherContract uriMatcherContract = UriMatcherContractUtil.load( contract );

        final QueryContract query = uriMatcherContract.operations().query();
        final String sql = query.sql();

        /*
        TODO: target API anpassen.
        CancellationSignal cancelation = new CancellationSignal() {};
        */

        try {
            final Cursor rval = readble().rawQuery(sql, args /*, cancelation*/);

            return rval;
        } catch ( final SQLiteException x ) { // Caused by: android.database.sqlite.SQLiteException: no such table:
            if( Tracer.isDevMode()) {
                throw new RuntimeException( "Couldn't execute raw-query [sql=" + sql + "] of uri {enum = " + contract.name() + "].", x );
            } else {
                return NoDataCursor.get();
            }
        }
    }


}
