package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;

/**
 * Created by asiebert on 02.05.2017.
 */

public abstract class SQLiteTypeMapping<T> {

    public abstract T to( final Cursor value,
                          final int index );

}
