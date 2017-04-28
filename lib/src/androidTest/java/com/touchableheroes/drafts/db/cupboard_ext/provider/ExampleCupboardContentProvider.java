package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.provider.CupboardContentProvider;
import com.touchableheroes.drafts.db.cupboard.xt.config.DbConfig;

/**
 * Created by asiebert on 25.04.2017.
 */

public class ExampleCupboardContentProvider extends CupboardContentProvider {

    public ExampleCupboardContentProvider() {
        super(
                new DbConfig( "example-db", 1,
                    new Class[] { ExampleEntity.class },
                    ExampleUris.class
        ));
    }
}
