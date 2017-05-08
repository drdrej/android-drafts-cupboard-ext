package com.touchableheroes.drafts.db.cupboard.xt.util;

import android.net.Uri;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;

/**
 * Created by asiebert on 04.03.16.
 */
public class ContractUriUtil {

    public static String  uriStr(final Enum uri,
                                 final UriMatcherContract paragraph) {
        final String path = "/" + uri.name();
        return "content://" + uri.getClass().getName() + path; /* paragraph.path(); */
    }

    public static UriTemplate uri(final Enum uri) {
        final UriMatcherContract paragraph = EnumTool.withEnum( uri ).annotation(UriMatcherContract.class);
        return new UriTemplate(uriStr(uri, paragraph));
    }


    public static Uri createInsert( final Enum uri ) {
        final EnumTool.EnumWrapper enumWrapper = EnumTool.withEnum( uri );
        final UriMatcherContract def = enumWrapper.annotation(UriMatcherContract.class);

        final Uri rval = ContractUriUtil.uri(uri).create();

        return rval;
    }


    public static Uri createQuery(final Enum uri) {
        final Uri rval = ContractUriUtil.uri(uri).create();
        return rval;
    }

    // TODO: reimpl. maybe wrong idea..
    public static Uri createDelete(final Enum key) {
        final Uri rval = ContractUriUtil.uri(key).create();
        return rval;
    }
}
