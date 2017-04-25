package com.touchableheroes.drafts.db.cupboard.xt;

import android.content.UriMatcher;
import android.net.Uri;
import android.util.Log;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.CupboardContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;

import java.util.Iterator;

/**
 * Created by asiebert on 25.04.2017.
 */

public class UriMatcherManager {

    private final UriMatcher matcher;

    public UriMatcherManager() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
    }

    @Deprecated
    public void registerUri(
            final CupboardContract contract,
            final Enum state) {
        System.out.println( "--> registerUri: " + contract + " / " + state);

        final UriMatcherContract dbResource = EnumTool.withEnum(state)
                .annotation(UriMatcherContract.class);

        System.out.println("--> registerUri: " + ("content://" + contract.authority()) + " path = " + dbResource.path());

        final String host = "content://" + contract.authority();

        matcher.addURI( host, dbResource.path(), state.ordinal());
    }

    public void registerUri(
            final String authority,
            final Enum state) {
        final UriMatcherContract dbResource = EnumTool.withEnum(state)
                .annotation(UriMatcherContract.class);

        if( dbResource == null ) {
            Log.e( "DB", "Couldn't register [URI = " + state.name() + "] in [type = " + state.getClass() + "]" );
            throw new IllegalStateException( "Couldn't configure ContentProvider with URIs" );
        }

        Log.d( "DB", "--> registerUri: " + ("content://" + authority) + " path = " + dbResource.path());
        matcher.addURI( authority, dbResource.path(), state.ordinal());
    }

    public int match(final Uri uri) {
        return matcher.match(uri);
    }


    public void registerUris( final DbConfig config) {
        for(final Iterator<Enum> uris = config.uris();
            uris.hasNext(); ) {

            final Enum uri = uris.next();
            final String authority = uri.getClass().getName();
            registerUri(authority, uri);
        }
    }
}
