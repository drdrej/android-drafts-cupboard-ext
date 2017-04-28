package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.tools.ArrayTool;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.core.tools.StringTool;
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

    public CupboardContentProvider( final DbConfig config ) {
       this.config = config;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new CupboardSQLiteDBHelper(getContext(), config);
        matcherMgr.registerUris( getConfig() );

        return true;
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
        if( hasArguments(projection, selection, sortOrder) ) {
            return queryWithArguments( uri, projection, selection, selectionArgs, sortOrder);
        }

        return queryWithUri(uri, selectionArgs);
    }

    private Cursor queryWithUri(final Uri uri,
                                final String[] selectionArgs) {

        final int matchId = this.matcherMgr.match(uri);
        final Enum contract = findEnum(matchId);

        return query(contract, selectionArgs);
    }

    public Cursor query(
            final Enum contract,
            final String[] selectionArgs) {
        final UriMatcherContract uriMatcher = EnumTool.withEnum(contract).annotation(UriMatcherContract.class);
        final Class<? extends DbCommand> queryCmdClass = uriMatcher.operations().query().command();

        try {
            final Constructor<? extends DbCommand> constructor = queryCmdClass.getConstructor(SQLiteOpenHelper.class);
            final DbCommand dbCommand = constructor.newInstance(dbHelper);

            return dbCommand.exec(contract, selectionArgs);
        } catch (final Throwable x) {
            throw new IllegalStateException("Couldn't initialize and execute DbCommand.", x);
        }
    }

    private Cursor queryWithArguments(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if( Tracer.isDevMode() ) {
            throw new UnsupportedOperationException( "this method is not implemented." );
        }

        return new NoDataCursor();
    }

    protected boolean hasArguments(final String[] projection,
                                   final String selection,
                                   final String sortOrder) {
        if(ArrayTool.isEmpty(projection)
                && StringTool.isEmpty(selection)
                && StringTool.isEmpty(sortOrder) )
            return false;

        return true;
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

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final int matchId = matcherMgr.match(uri);
        final Enum uriEnum = findEnum(matchId);

        final UriMatcherContract uriMatcher = EnumTool.withEnum(uriEnum).annotation(UriMatcherContract.class);
        final Class<? extends InsertDbCommand> cmdClass = uriMatcher.operations().insert().command();

        if( cmdClass.isAssignableFrom(Void.class) ) {
            if(Tracer.isDevMode()) {
                throw new UnsupportedOperationException("No ");
            } else {
                return uri;
            }
        }

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
       throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

}
