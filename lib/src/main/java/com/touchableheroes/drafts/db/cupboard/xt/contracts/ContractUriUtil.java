package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import android.net.Uri;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;

/**
 * Created by asiebert on 04.03.16.
 */
public class ContractUriUtil {

    /*
    @Deprecated
    public static Uri uri(final CupboardContract contract, final UriMatcherContract paragraph) {
        final String preDefUri = uriStr(contract, paragraph);

        System.out.print( "--> uri encode: " + preDefUri );

        return Uri.parse(preDefUri);
    }

    @Deprecated
    public static String  uriStr(final CupboardContract contract, final UriMatcherContract paragraph) {
        return "content://" + contract.authority() + paragraph.path();
    }
    */

    public static String  uriStr(final Enum uri, final UriMatcherContract paragraph) {
        return "content://" + uri.getClass().getName() + paragraph.path();
    }

    public static UriTemplate uriByState(final Enum uri) {
        final UriMatcherContract paragraph = EnumTool.withEnum( uri ).annotation(UriMatcherContract.class);
        return new UriTemplate(uriStr(uri, paragraph));
    }

}
