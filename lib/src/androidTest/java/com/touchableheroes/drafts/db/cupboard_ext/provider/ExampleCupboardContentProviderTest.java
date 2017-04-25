package com.touchableheroes.drafts.db.cupboard_ext.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContext;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.ContractUriUtil;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

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

    @Test
    public void testInsertQuery(){
        final ContentProvider provider = getProvider();


        final UriTemplate uriTemplate =  ContractUriUtil.uriByState( ExampleUriContracts.ALL_ENTITIES );
        final Uri uriCall = uriTemplate.create();

        final EnumTool.EnumWrapper enumWrapper = EnumTool.withEnum( ExampleUriContracts.ENTITY_BY_ID );
        final UriMatcherContract def = enumWrapper.annotation(UriMatcherContract.class);

        final Uri insertUri = ContractUriUtil.uriByState(ExampleUriContracts.ENTITY_BY_ID).create();

/*        final Uri resultUri = cupboard()
                .withContext( getContext() )
                .put(insertUri, entity);
*/

        ContentValues contentValues = new ContentValues();

        contentValues.put( "_id", 1 );
        contentValues.put( "name", "Name xa,xa" );

        Uri resultUri = provider.insert(insertUri, contentValues);

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

        List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
        for (int i = 0; result != null && i < results.size(); i++ ) {
            ExampleEntity exampleEntity = results.get(i);
            System.out.println( "[" + i + "] " + exampleEntity);
        }

    }

}
