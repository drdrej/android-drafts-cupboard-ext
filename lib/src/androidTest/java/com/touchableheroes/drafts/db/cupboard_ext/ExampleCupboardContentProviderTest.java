package com.touchableheroes.drafts.db.cupboard_ext;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.core.obj.Structure;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.ConverterCursorList;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.CursorList;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContentValuesUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContractUriUtil;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleCupboardContentProvider;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleEntity;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleEntity2;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleUris;

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

        Tracer.setDevMode( true );

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

        final UriTemplate uriTemplate =  ContractUriUtil.uri( ExampleUris.ENTITIES );
        final Uri uriCall = uriTemplate.create();

        final String[] projection = null; // projection(def); // --> Map auf die Entity

        // TODO: Parameter-Handling ueberlegen
        // TODO: ContractLoader-Aufruf an der richtigen Stelle einbauen! (nicht hier :) im Fragment)
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
        final ExampleCupboardContentProvider provider = getProvider();

        final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY);

/*        final Uri resultUri = cupboard()
                .withContext( getContext() )
                .put(insertUri, entity);
*/
        ExampleEntity entity = new ExampleEntity();


        entity._id = 12L;
        entity.name = "test entry";

        final ContentValues values = ContentValuesUtil.entityToContentValues(entity);

//        contentValues.put( "_id", 1 );
 //       contentValues.put( "name", "Name xa,xa" );

        Uri resultUri = provider.insert(insertUri, values);

// request db content:
        final UriTemplate uriTemplate =  ContractUriUtil.uri( ExampleUris.ENTITIES );
        final Uri uriCall = uriTemplate.create();

        final String[] projection = null; // projection(def); // --> Map auf die Entity

        // TODO: Parameter-Handling ueberlegen
        // TODO: ContractLoader-Aufruf an der richtigen Stelle einbauen! (nicht hier :) im Fragment)
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

        assertEquals( "Size of Entries in List:", results.size(), 1 );
        assertEquals( "Name of entry: ", results.get(0).name, entity.name);
    }





    @Test
    public void testInsertQueryById(){
        final ExampleCupboardContentProvider provider = getProvider();

        final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY);

        final ExampleEntity entity = new ExampleEntity();
        entity._id = System.currentTimeMillis();
        entity.name = "testInsertQueryById";

        final ContentValues values = ContentValuesUtil.entityToContentValues(entity);

        final Uri resultUri = provider.insert(insertUri, values);

        // request db content:
        final UriTemplate uriTemplate =  ContractUriUtil.uri( ExampleUris.ENTITY );
        final Uri uriCall = uriTemplate.create();

        final Cursor result = provider.query(uriCall, null, null, null, null);
        assertNotNull( result );

        final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
        for (int i = 0; result != null && i < results.size(); i++ ) {
            ExampleEntity exampleEntity = results.get(i);
            System.out.println( "[" + i + "] " + exampleEntity);
        }

        assertEquals( "Size of Entries in List:", results.size(), 1 );
        assertEquals( "Name of entry: ", results.get(0).name, entity.name);
    }


    @Test
    public void testQueryBrokenSql(){
        // Test Error-Handling for production
        Tracer.setDevMode( false );

        final ExampleCupboardContentProvider provider = getProvider();

        // request db content:
        final UriTemplate uriTemplate =  ContractUriUtil.uri( ExampleUris.BROKEN_RAW_QUERY );
        final Uri uriCall = uriTemplate.create();

        final Cursor result = provider.query(uriCall, null, null, null, null);
        assertNotNull( result );
        assertEquals( "No Elements found.", 0, result.getCount() );
    }


    @Test
    public void testQueryCupboardBasedByEntity(){
        final ExampleCupboardContentProvider provider = getProvider();

        final ExampleEntity entity = new ExampleEntity();
        entity._id = System.currentTimeMillis();
        entity.name = "testInsertQueryById";

        {
            final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY_cupboardbased);

            final ContentValues values = ContentValuesUtil.entityToContentValues(entity);

            final Uri resultUri = provider.insert(insertUri, values);
        }

        // request db content:
        final Uri uriCall =  ContractUriUtil.uri( ExampleUris.ENTITY_cupboardbased ).create();
        final Cursor result = provider.query(uriCall, null, null, null, null);
        assertNotNull( result );

        final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
        for (int i = 0; result != null && i < results.size(); i++ ) {
            ExampleEntity exampleEntity = results.get(i);
            System.out.println( "[" + i + "] " + exampleEntity);
        }

        assertEquals( "Size of Entries in List:", results.size(), 1 );
        assertEquals( "Name of entry: ", results.get(0).name, entity.name);
    }


    @Test
    public void testCursorList(){
        final ExampleCupboardContentProvider provider = getProvider();

        final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY);

        final ExampleEntity entity = new ExampleEntity();
        entity._id = System.currentTimeMillis();
        entity.name = "testInsertQueryById";

        final ContentValues values = ContentValuesUtil.entityToContentValues(entity);

        provider.insert(insertUri, values);

        final UriTemplate uriTemplate =  ContractUriUtil.uri( ExampleUris.ENTITIES );
        final Uri uriCall = uriTemplate.create();

        final Cursor result = provider.query(uriCall, null, null, null, null);
        assertNotNull( result );

        final ConverterCursorList<ExampleEntity2> resultsList = new ConverterCursorList<>(result, ExampleEntity2.class);
        assertEquals( resultsList.size(), 1 );

        Structure<ExampleEntity2> firstEntity = resultsList.get(0);
        assertNotNull( firstEntity );
        assertEquals( firstEntity.get( ExampleEntity2.name ), entity.name );
        assertEquals( firstEntity.get( ExampleEntity2._id ), entity._id );
    }


    @Test
    public void testDeleteAllCupboardBasedByEntity() {
        final ExampleCupboardContentProvider provider = getProvider();

        final ExampleEntity entity = new ExampleEntity();
        entity._id = System.currentTimeMillis();
        entity.name = "testInsertQueryById";

        {
            final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY_cupboardbased);
            final ContentValues values = ContentValuesUtil.entityToContentValues(entity);
            final Uri resultUri = provider.insert(insertUri, values);
        }


        { // check before delete :
            final Uri uriCall = ContractUriUtil.uri(ExampleUris.ENTITY_cupboardbased).create();
            final Cursor result = provider.query(uriCall, null, null, null, null);
            assertNotNull(result);
            final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
            assertEquals("Size of Entries in List:", results.size(), 1);
        }

        { // delete
            final Uri uri = ContractUriUtil.createDelete(ExampleUris.ENTITY_cupboardbased);
            provider.delete(uri, null, null); // same as: delete all..
        }

        { // check after delete :
            final Uri uriCall = ContractUriUtil.uri(ExampleUris.ENTITY_cupboardbased).create();
            final Cursor result = provider.query(uriCall, null, null, null, null);
            assertNotNull(result);
            final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
            assertEquals("Size of Entries in List:", results.size(), 0);
        }
    }
}
