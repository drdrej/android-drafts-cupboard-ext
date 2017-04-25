package com.touchableheroes.drafts.db.cupboard_ext.provider;

import com.touchableheroes.drafts.db.cupboard.xt.CupboardContentProvider;
import com.touchableheroes.drafts.db.cupboard.xt.DbConfig;

/**
 * Created by asiebert on 25.04.2017.
 */

public class ExampleCupboardContentProvider extends CupboardContentProvider {

    public ExampleCupboardContentProvider() {
        super(
                new DbConfig( "example-db", 1,
                    new Class[] { ExampleEntity.class },
                    ExampleUriContracts.class
        ));
    }
}
