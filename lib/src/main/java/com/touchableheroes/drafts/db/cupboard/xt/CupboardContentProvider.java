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
            initDB();
            initUriMatcher(matcherMgr);

            return true;
        }
    }

    private void initDB() {
        dbHelper = new CupboardSQLiteDBHelper(getContext(), config);
    }

    protected void initUriMatcher(
            final UriMatcherManager matcherMgr) {
        matcherMgr.registerUris( getConfig() );
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
            final Class<? extends DbCommand> queryCmdClass = uriMatcher.operations().query();

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
                throw new IllegalStateException( "Couldn't initialize and execute DbCommand.",  x);
            }
                /*
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            */


            //Class byMatchId = findByMatchId(matchId);
/*
            final Cursor cursor = doQuery(uri, projection, selection, selectionArgs, sortOrder);

            int count = cursor.getCount();

            System.out.println( "--> LOAD CURSOR.size = " + count);

            return cursor;
            */
        }
    }

    private Cursor doQuery(
            final Uri uri,
            final String[] projection,
            final String selection, final String[] selectionArgs,
            final String sortOrder) {


        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        final int matchId = matcherMgr.match(uri);
        final Class clz = findByMatchId(matchId);

        return cupboard().withDatabase(db).query(clz).
                withProjection(projection).
                withSelection(selection, selectionArgs).
                orderBy(sortOrder).
                getCursor();
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
        /**
         * The mime type of a directory of items.
         */
        /* public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE +
                        "/vnd.de.openminds.lentitems_items";
                        */
        /**
         * The mime type of a single item.
         */
        /* public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +
                        "/vnd.de.openminds.lentitems_items";
                        */

        // hier den myme/type anhand des uri matchers und der uri bestimmen und zureuckgeben.

        return null;
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
