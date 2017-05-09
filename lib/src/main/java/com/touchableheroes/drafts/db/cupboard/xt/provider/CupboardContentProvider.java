package com.touchableheroes.drafts.db.cupboard.xt.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.obj.Structure;
import com.touchableheroes.drafts.core.tools.ArrayTool;
import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.core.tools.StringTool;
import com.touchableheroes.drafts.db.cupboard.xt.commands.DeleteCommand;
import com.touchableheroes.drafts.db.cupboard.xt.config.DbConfig;
import com.touchableheroes.drafts.db.cupboard.xt.defaults.NoDataCursor;
import com.touchableheroes.drafts.db.cupboard.xt.commands.QueryCommand;
import com.touchableheroes.drafts.db.cupboard.xt.commands.InsertCommand;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.util.UriMatcherContractUtil;

import java.lang.reflect.Constructor;

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

        final Enum contract = identify(uri);

        return query(contract, selectionArgs);
    }

    public Cursor query(
            final Enum contract,
            final String[] selectionArgs) {
        final UriMatcherContract uriMatcher = EnumTool.withEnum(contract).annotation(UriMatcherContract.class);
        final Class<? extends QueryCommand> queryCmdClass = uriMatcher.operations().query().command();

        try {
            final Constructor<? extends QueryCommand> constructor = queryCmdClass.getConstructor(SQLiteOpenHelper.class);
            final QueryCommand queryCommand = constructor.newInstance(dbHelper);

            return queryCommand.exec(contract, selectionArgs);
        } catch (final Throwable x) {
            throw new IllegalStateException("Couldn't initialize and execute QueryCommand.", x);
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
            final Enum contract = identify(uri);
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
        final Enum uriEnum = identify(uri);
        final UriMatcherContract uriMatcher = UriMatcherContractUtil.load(uriEnum);
        final Class<? extends InsertCommand> cmdClass = uriMatcher.operations().insert().command();

        if( cmdClass.isAssignableFrom(Void.class) ) {
            if(Tracer.isDevMode()) {
                throw new UnsupportedOperationException("No ");
            } else {
                return uri;
            }
        }

        try {
            final Constructor<? extends InsertCommand> constructor = cmdClass.getConstructor(SQLiteDatabase.class);
            final InsertCommand dbCommand = constructor.newInstance(dbHelper.getWritableDatabase());

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
            throw new IllegalStateException("Couldn't initialize and execute QueryCommand.", x);
        }
    }

    private Enum identify(Uri uri) {
        final int matchId = matcherMgr.match(uri);
        return findEnum(matchId);
    }


    @Override
    public int delete(final Uri uri,
                      final String selection,
                      final String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final Enum uriEnum = identify(uri);

        final UriMatcherContract uriMatcher = UriMatcherContractUtil.load(uriEnum);
        final Class<? extends DeleteCommand> cmdClass = uriMatcher.operations().delete().command();

        if( cmdClass.isAssignableFrom(Void.class) ) {
            if(Tracer.isDevMode()) {
                throw new UnsupportedOperationException("No ");
            } else {
                return 0;
            }
        }

        try {
            final Constructor<? extends DeleteCommand> constructor = cmdClass.getConstructor(SQLiteDatabase.class);
            final DeleteCommand dbCommand = constructor.newInstance(dbHelper.getWritableDatabase());

            return dbCommand.exec(uriEnum, selectionArgs);
        } catch (final Throwable x) {
            throw new IllegalStateException("Couldn't initialize and execute QueryCommand.", x);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public /* abstract */ Cursor queryBy(final Enum key,
                                   final Structure param) {
        throw new UnsupportedOperationException();
    }

}
