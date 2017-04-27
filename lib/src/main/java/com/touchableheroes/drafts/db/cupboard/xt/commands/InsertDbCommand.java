package com.touchableheroes.drafts.db.cupboard.xt.commands;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by asiebert on 27.04.2017.
 */
public abstract class InsertDbCommand {

    private final SQLiteDatabase db;

    public InsertDbCommand(final SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase writable() {
        return db;
    }

    /**
     * @param contract
     * @param params
     * @param id if < 0 than ignore id-value.
     * @return [] empty array if nothing created, [1] one entry if one element created, [1,2,3...] if many entries created, for every entry own id
     */
    public abstract long[] exec(final Enum contract,
                                final long id,
                                final ContentValues params);
}


// TODO: where is insertcommand aufloesung des construktors fixen bitte. da sonst etwas an config falsch?