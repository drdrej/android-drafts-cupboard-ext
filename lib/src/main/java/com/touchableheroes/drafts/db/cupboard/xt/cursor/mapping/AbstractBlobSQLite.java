package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

/**
 * Created by asiebert on 02.05.2017.
 */
public abstract class AbstractBlobSQLite<T>
        extends SQLiteTypeMapping<T> {

    @Override
    public T to(final Cursor value,
                      final int index ) {
        return convert( value.getBlob( index ) );
    }

    protected abstract T convert(final byte[] blob);

}
