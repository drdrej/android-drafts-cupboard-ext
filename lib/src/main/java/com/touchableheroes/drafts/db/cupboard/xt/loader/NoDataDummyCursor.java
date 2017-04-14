package com.touchableheroes.drafts.db.cupboard.xt.loader;

import android.database.AbstractCursor;

import java.util.NoSuchElementException;

/**
 * Created by asiebert on 16.04.16.
 */
public class NoDataDummyCursor extends AbstractCursor {


    private final String[] names;

    public NoDataDummyCursor(final String... columnNames) {
        this.names = columnNames;
    }

    public NoDataDummyCursor() {
        this(new String[] { "_id" });
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public String[] getColumnNames() {
        return this.names;
    }

    @Override
    public String getString(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public short getShort(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public int getInt(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public long getLong(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public float getFloat(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public double getDouble(int column) {
        throw new NoSuchElementException();
    }

    @Override
    public boolean isNull(int column) {
        return true;
    }
}
