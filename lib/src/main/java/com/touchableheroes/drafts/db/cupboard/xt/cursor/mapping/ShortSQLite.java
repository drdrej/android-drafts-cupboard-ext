package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public class ShortSQLite
        extends SQLiteTypeMapping<Short> {

    @Override
    public Short to(final Cursor value,
                      final int index ) {
        return value.getShort( index );
    }

}
