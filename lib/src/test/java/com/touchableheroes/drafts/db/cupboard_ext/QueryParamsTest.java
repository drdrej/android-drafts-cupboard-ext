package com.touchableheroes.drafts.db.cupboard_ext;

import com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard.QueryParams;
import com.touchableheroes.drafts.db.cupboard.xt.defaults.QueryContract;


import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by asiebert on 09.05.2017.
 */
public class QueryParamsTest {


    @Test
    public void empty_all() {
        final QueryParams p = new QueryParams(
                QueryContract.DEFAULT, null, null, null);

        assertNull(p.projection());
        assertNull(p.selection());
        assertNull(p.orderBy());
        assertNull(p.args(null));
    }

    @Test
    public void with_query_projection() {
        final QueryParams p = new QueryParams(
                QueryContract.create(
                        new String[] {
                                "field1" },
                        null,
                        null),
                null, null, null);

        assertNotNull( p.projection());
        assertEquals( p.projection().length, 1);
        assertEquals( p.projection()[0], "field1" );

        assertNull(p.selection());
        assertNull(p.orderBy());
        assertNull(p.args(null));
    }
}
