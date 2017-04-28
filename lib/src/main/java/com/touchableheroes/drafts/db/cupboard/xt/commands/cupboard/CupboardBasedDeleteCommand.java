package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import android.database.sqlite.SQLiteDatabase;

import com.touchableheroes.drafts.db.cupboard.xt.commands.DeleteCommand;
import static nl.qbusict.cupboard.CupboardFactory.*;
/**
 * Created by asiebert on 28.04.2017.
 */

public class CupboardBasedDeleteCommand
       extends DeleteCommand {


    public CupboardBasedDeleteCommand(final SQLiteDatabase writable) {
        super(writable);
    }

    @Override
    public int exec(
            final Enum uri,
            final String[] selectionArgs) {
        // cupboard().withDatabase(writable()).
        throw new UnsupportedOperationException();
    }
}
