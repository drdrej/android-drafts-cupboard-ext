package com.touchableheroes.drafts.db.cupboard_ext.helper;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.touchableheroes.drafts.db.cupboard.xt.CupboardSQLiteDBHelper;
import com.touchableheroes.drafts.db.cupboard.xt.DbConfig;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleEntity;
import com.touchableheroes.drafts.db.cupboard_ext.provider.ExampleUris;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by asiebert on 25.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleCupboardDatabaseHelper
       extends AndroidTestCase {

    private CupboardSQLiteDBHelper db;
    private RenamingDelegatingContext ctx;

    @Before
    public void setUp(){
        ctx
                = new RenamingDelegatingContext(getContext(), "test_");
        db = new CupboardSQLiteDBHelper(ctx, ExampleUris.class);
    }


    @Test
    public void testCREATE() {
     // db.onCreate( ctx.openOrCreateDatabase());
    }

    @After
    public void tearDown() throws Exception{
        db.close();
        super.tearDown();
    }

}
