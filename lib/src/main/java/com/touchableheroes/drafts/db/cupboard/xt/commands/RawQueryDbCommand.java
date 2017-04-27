package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

/**
 * Created by asiebert on 27.04.2017.
 */
public class RawQueryDbCommand extends DbCommand {

    public RawQueryDbCommand(final SQLiteOpenHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public Cursor exec(final Enum contract,
                       final Uri uri,
                       final String[] projection,
                       final String selection,
                       final String[] selectionArgs,
                       final String sortOrder) {
        final String[] args = null; // new String[0];

        final QueryContract query = EnumTool.withEnum(contract).annotation(UriMatcherContract.class).operations().query();
        final String sql = query.sql();

        /*
        TODO: target API anpassen.
        CancellationSignal cancelation = new CancellationSignal() {};
        */

        final Cursor rval = readble().rawQuery(sql, args /*, cancelation*/);

        return rval;
    }


}
