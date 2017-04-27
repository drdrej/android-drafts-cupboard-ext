package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.commands.DbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertDbCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by asiebert on 28.02.16.
 */
public abstract class CupboardContentProvider extends ContentProvider {

    private final DbConfig config;

    private CupboardSQLiteDBHelper dbHelper;

    private UriMatcherManager matcherMgr = new UriMatcherManager();

    private static final Object LOCK = new Object();

    public CupboardContentProvider( final DbConfig config ) {
       this.config = config;
    }

    @Override
    public boolean onCreate() {
        synchronized( LOCK ) {
            dbHelper = new CupboardSQLiteDBHelper(getContext(), config);
            matcherMgr.registerUris( getConfig() );

            return true;
        }
    }

    public DbConfig getConfig() {
        return config;
    }

    @Override
    public Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder) {
        synchronized (LOCK) {
            final int matchId = this.matcherMgr.match(uri);
            final Enum contract = findEnum(matchId);

            final UriMatcherContract uriMatcher = EnumTool.withEnum(contract).annotation(UriMatcherContract.class);
            final Class<? extends DbCommand> queryCmdClass = uriMatcher.operations().query().command();

            try {
                final Constructor<? extends DbCommand> constructor = queryCmdClass.getConstructor(SQLiteOpenHelper.class);
                final DbCommand dbCommand = constructor.newInstance(dbHelper);

                return dbCommand.exec(contract,
                        uri,
                        projection,
                        selection,
                        selectionArgs,
                        sortOrder);
            } catch (final Throwable x) {
                throw new IllegalStateException("Couldn't initialize and execute DbCommand.", x);
            }
        }
    }

    public Enum findEnum(final int matchId) {
        return getConfig().uriById(matchId);
    }

    @Override
    public String getType(final Uri uri) {
        try {
            final int matchId = matcherMgr.match(uri);
            final Enum contract = findEnum(matchId);

            final String rval = EnumTool.withEnum(contract).annotation(UriMatcherContract.class).mimeType();

            if( rval.length() < 1 )
                return null;

            return rval;
        } catch (final Throwable x) {
            return null;
        }
    }

    @Override
    public Uri insert(final Uri uri,
                      final ContentValues values) {

        synchronized (LOCK) {
            return doInsert(uri, values);
        }
    }


    private Uri doInsert(final Uri uri,
                         final ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final int matchId = matcherMgr.match(uri);
        final Enum uriEnum = findEnum(matchId);

        final UriMatcherContract uriMatcher = EnumTool.withEnum(uriEnum).annotation(UriMatcherContract.class);
        final Class<? extends InsertDbCommand> cmdClass = uriMatcher.operations().insert().command();
/*
        if( cmdClass == Void.class ) {
            throw new UnsupportedOperationException( "No " );
        }
*/
        try {
            final Constructor<? extends InsertDbCommand> constructor = cmdClass.getConstructor(SQLiteDatabase.class);
            final InsertDbCommand dbCommand = constructor.newInstance(dbHelper.getWritableDatabase());

            final long[] newIds = dbCommand.exec(uriEnum, -1, values );

            switch( newIds.length ) {
                case 0:
                    return uri; // same uri because nothing created
                case 1:
                    return ContentUris.withAppendedId(uri, newIds[0]);
                default:
                    throw new UnsupportedOperationException("Missing implementation for uri for many generated objects in one insertdbcommand. Please fix, now its time!");
            }
        } catch (final Throwable x) {
            throw new IllegalStateException("Couldn't initialize and execute DbCommand.", x);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (LOCK) {
            // return cupboard().withDatabase(db).delete(Channel.class, selection, selectionArgs);
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (LOCK) {
                    // return cupboard().withDatabase(db).update(clz, values, selection, selectionArgs);
        }
        return 0;
    }
}
