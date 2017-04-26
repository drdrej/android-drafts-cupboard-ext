package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 26.04.2017.
 */

public class CupboardBasedQueryDbCommand extends DbCommand {


    public CupboardBasedQueryDbCommand(final SQLiteOpenHelper dbHelper) {
        super(dbHelper);
    }

    public Cursor exec(final Enum contract,
                       final Uri uri,
                       final String projection,
                       final String selection,
                       final String selectionArgs,
                       final String sortOrder
    ) {
        final SQLiteDatabase db = readble();
        final Class<? extends Object> clz = EnumTool.withEnum(contract).annotation(UriMatcherContract.class).type();

        return cupboard().withDatabase(db).query(clz).
                withProjection(projection).
                withSelection(selection, selectionArgs).
                orderBy(sortOrder).
                getCursor();
    }
}
