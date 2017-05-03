package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public class DoubleSQLite
        extends SQLiteTypeMapping<Double> {

    @Override
    public Double to(final Cursor value,
                      final int index ) {
        return value.getDouble( index );
    }

}
