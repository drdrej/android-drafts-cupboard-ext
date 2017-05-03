package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public class LongSQLite
        extends SQLiteTypeMapping<Long> {

    @Override
    public Long to(final Cursor value,
                     final int index ) {
        return value.getLong( index );
    }

}
