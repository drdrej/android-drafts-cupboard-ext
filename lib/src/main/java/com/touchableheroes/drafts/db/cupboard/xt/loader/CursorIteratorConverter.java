package com.touchableheroes.drafts.db.cupboard.xt.loader;

import android.database.Cursor;

/**
 * Created by asiebert on 16.04.16.
 */
public class CursorIteratorConverter<R> {

    private final Cursor src;

    public  CursorIteratorConverter(final Cursor src) {
        this.src = src;
    }

    public Cursor getCursor() {
        return src;
    }
}
