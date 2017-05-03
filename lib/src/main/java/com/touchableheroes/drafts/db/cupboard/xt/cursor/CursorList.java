package com.touchableheroes.drafts.db.cupboard.xt.cursor;

import android.database.Cursor;

import com.touchableheroes.drafts.db.cupboard.xt.defaults.NoDataCursor;

import java.util.AbstractList;

public abstract class CursorList<E> extends AbstractList<E> {

    private final Cursor cursor;

    public CursorList(final Cursor src){
       this.cursor = src == null ? new NoDataCursor() : src;
    }

    @Override
    public E get(final int index) {
        if( index < 0 ) {
            throw new IllegalArgumentException( "Couldn't access cursor [position=" + index +"] because wrong number. only positive index allowed." );
        }

        if( cursor.isClosed() ) {
            throw new IllegalStateException( "Couldn't access [index= "+index+"], cursor is closed." );
        }

        if( cursor.getCount() < 1) {
            throw new IllegalStateException( "Couldn't access cursor [position=" + index +"] because cursor [cursor = " + cursor.getClass().getSimpleName() +"] is empty.");
        }

        if( !cursor.moveToPosition(index) ) {
            throw new IllegalStateException( "Couldn't access cursor [position=" + index +"] because moveToPosition() don't work." );
        }

        return parse(cursor);
    }

    public abstract E parse(final Cursor cursor);

    @Override
    public int size() {
        return cursor.getCount();
    }

}
