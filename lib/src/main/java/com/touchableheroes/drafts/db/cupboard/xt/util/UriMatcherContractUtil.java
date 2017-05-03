package com.touchableheroes.drafts.db.cupboard.xt.util;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.DeleteContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.defaults.Defaults;

import java.lang.annotation.Annotation;

/**
 * Created by asiebert on 28.04.2017.
 */

public class UriMatcherContractUtil {

    public static UriMatcherContract load( final Enum uriEnum ) {
        final UriMatcherContract contract = EnumTool.withEnum(uriEnum).annotation(UriMatcherContract.class);

        if( contract == null )
            return Defaults.DEFAULT_URI_CONTRACT;

        return contract;
    }
}
