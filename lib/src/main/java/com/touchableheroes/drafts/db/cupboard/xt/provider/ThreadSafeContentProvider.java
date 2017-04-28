package com.touchableheroes.drafts.db.cupboard.xt.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.touchableheroes.drafts.db.cupboard.xt.config.DbConfig;

/**
 * Created by asiebert on 28.04.2017.
 */
public abstract class ThreadSafeContentProvider
       extends CupboardContentProvider {

    private static final Object LOCK = new Object();

    public ThreadSafeContentProvider(final DbConfig config) {
        super(config);
    }

    @Override
    public boolean onCreate() {
        synchronized( LOCK ) {
            return super.onCreate();
        }
    }

    @Override
    public Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder) {
        synchronized (LOCK) {
            return super.query(uri, projection, selection, selectionArgs, sortOrder);
        }
    }

    @Override
    public Uri insert(final Uri uri,
                      final ContentValues values) {
        synchronized (LOCK) {
            return super.insert(uri, values);
        }
    }
}
