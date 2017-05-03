package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public class IntSQLite
        extends SQLiteTypeMapping<Integer> {

    @Override
    public Integer to(final Cursor value,
                      final int index ) {
        return value.getInt( index );
    }

}
