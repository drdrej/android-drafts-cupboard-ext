package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asiebert on 28.04.2017.
 */

public abstract class DeleteCommand {

    private final SQLiteDatabase writable;

    public DeleteCommand(final SQLiteDatabase writable) {
        this.writable = writable;
    }

    public SQLiteDatabase writable() {
        return writable;
    }

    public abstract int exec(final Enum uri,
                     final String[] selectionArgs );
}
