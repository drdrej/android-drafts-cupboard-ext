package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public class StringSQLite
        extends SQLiteTypeMapping<java.lang.String> {

    @Override
    public java.lang.String to(final Cursor value,
                               final int index ) {
        return get(value, index);
    }

    private java.lang.String get(Cursor value, int index) {
        if( value != null )
            return value.getString( index );
        else
            return null;
    }

}
