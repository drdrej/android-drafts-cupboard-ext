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
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContentValuesUtil;
import com.touchableheroes.drafts.db.cupboard.xt.util.ContractUriUtil;
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
public class CupboardContentProvider_query_Test
     extends AbstractContentProviderTest {

    public CupboardContentProvider_query_Test() {
        super(ExampleCupboardContentProvider.class);
    }

    @Before
    public void setu()
            throws Exception {
        super.setUp();
    }

    protected ExampleEntity createEntity(final long id) {
        ExampleEntity entity = new ExampleEntity();

        entity._id = id;
        entity.name = "test entry-"+id;

        return entity;
    }

    @Test
    public void testInsertQuery(){
        final ExampleCupboardContentProvider provider = getProvider();

        { // prepare data set / insert entities:
            final ExampleEntity e1 = createEntity(1);
            final Uri insertUri = ContractUriUtil.createInsert(ExampleUris.ENTITY_cupboardbased);

            provider.insert(insertUri,
                    ContentValuesUtil.entityToContentValues(e1));

            final ExampleEntity e2 = createEntity(2);
            provider.insert(insertUri,
                    ContentValuesUtil.entityToContentValues(e2));
        }

        { // request where id = 1 as selection parameter
            final UriTemplate uriTemplate = ContractUriUtil.uri(ExampleUris.ENTITY_cupboardbased);
            final Uri uriCall = uriTemplate.create();

            final Cursor result = provider.query(
                    uriCall,
                    null, " _id = ? ",
                    new String[]{ "1" },
                    null);
            /*
            final Cursor result
                    = provider.queryBy(ExampleUris.ENTITIES,
                    new Structure(ByIdSelection.class) );
*/
            assertNotNull(result);

            final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
            for (int i = 0; result != null && i < results.size(); i++) {
                ExampleEntity exampleEntity = results.get(i);
                System.out.println("[" + i + "] " + exampleEntity);
            }

            assertEquals("Size of Entries in List:", results.size(), 1);

            final ExampleEntity e1 = createEntity(1);
            assertEquals("Name of entry: ", results.get(0).name, e1.name);
        }

        { // delete where id = 1 as selection parameter
            final Uri uri = ContractUriUtil.createDelete(ExampleUris.ENTITY_cupboardbased);
            final int result = provider.delete(uri,
                    " _id = ? ",
                    new String[]{ "1" });

            assertEquals(result, 1);
        }

        { // request after delete, where id = 1 as selection parameter
            final UriTemplate uriTemplate = ContractUriUtil.uri(ExampleUris.ENTITY_cupboardbased);
            final Uri uriCall = uriTemplate.create();

            final Cursor result = provider.query(
                    uriCall,
                    null, " _id = ? ",
                    new String[]{ "1" },
                    null);

            assertNotNull(result);

            final List<ExampleEntity> results = cupboard().withCursor(result).list(ExampleEntity.class);
            assertEquals("Size of Entries in List:", results.size(), 0);
        }
    }

}
