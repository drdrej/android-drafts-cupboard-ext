package com.touchableheroes.drafts.db.cupboard_ext;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ProviderTestCase2;

import com.touchableheroes.drafts.core.logger.Tracer;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleCupboardContentProvider;

import org.junit.After;
import org.junit.Before;

/**
 * Created by asiebert on 09.05.2017.
 */

public class AbstractContentProviderTest
        extends ProviderTestCase2<ExampleCupboardContentProvider> {

    public AbstractContentProviderTest(
            final Class<ExampleCupboardContentProvider> contentProviderContract) {
        super( contentProviderContract /* l1ExampleCupboardContentProvider.class*/,
                contentProviderContract.getName());
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

    @After
    public void tearDown() {
        Tracer.setDevMode( false );
    }


}
