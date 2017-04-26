package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.commands.DbCommand;
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

    private Class findByMatchId(final int matchId) {
        final Enum uri = findEnum(matchId);
        final UriMatcherContract contract = EnumTool.withEnum(uri).annotation(UriMatcherContract.class);

        return contract.type();
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




    private Uri doInsert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        final int matchId = matcherMgr.match(uri);
        final Class type = findByMatchId(matchId);

        // TODO: hier prüfen, wie allgemein das ist:::
        final long id = Long.getLong(uri.getLastPathSegment(), 0);

        final long resultId;

        final String resultUri;
        if (id == 0) {
            resultId = cupboard().withDatabase(db).put(type, values);
            resultUri = uri + "/" + resultId;

            return Uri.parse(resultUri);
        } else {
            resultId = cupboard().withDatabase(db).update(type, values);
            return uri;
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
