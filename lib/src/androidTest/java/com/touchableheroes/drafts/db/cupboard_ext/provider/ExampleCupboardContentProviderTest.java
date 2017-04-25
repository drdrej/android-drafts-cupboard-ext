package com.touchableheroes.drafts.db.cupboard_ext.provider;

import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContext;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.ContractUriUtil;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by asiebert on 25.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleCupboardContentProviderTest
     extends ProviderTestCase2<ExampleCupboardContentProvider> {

    public ExampleCupboardContentProviderTest() {
        super(ExampleCupboardContentProvider.class, ExampleCupboardContentProvider.class.getName());
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        Context ctx = InstrumentationRegistry.getTargetContext();
        setContext(ctx);

        ExampleCupboardContentProvider provider = getProvider();
        provider.onCreate();
    }

    @Override
    public Context getContext() {
        // return super.getContext();
        return InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testQuery(){
        ContentProvider provider = getProvider();

        final UriTemplate uriTemplate =  ContractUriUtil.uriByState( ExampleUriContracts.ALL_ENTITIES );
        final Uri uriCall = uriTemplate.create();

        final String[] projection = null; // projection(def); // --> Map auf die Entity

        // TODO: Parameter-Handling ueberlegen
        // TODO: Loader-Aufruf an der richtigen Stelle einbauen! (nicht hier :) im Fragment)
        // ! mache hier bewusst einen kaputten code rein, damit ich die stelle wieder finde.

        String selection = null; // --> where-Params
        String[] selectionArgs = null; // --> where-Names.
        String sortOrder = null;

        // Cursor -> d.h. muss eine Query-Uri sein!


        final Cursor result = provider.query(uriCall, projection, selection, selectionArgs, sortOrder);

        assertNotNull( result );
    }

}
