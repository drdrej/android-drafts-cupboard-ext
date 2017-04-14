package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import android.net.Uri;

import com.touchableheroes.drafts.core.tools.EnumTool;
import com.touchableheroes.drafts.db.cupboard.xt.loader.UriTemplate;

/**
 * Created by asiebert on 04.03.16.
 */
public class ContractUriUtil {

    public static Uri uri(final CupboardContract contract, final CupboardLoaderContract paragraph) {
        final String preDefUri = uriStr(contract, paragraph);

        System.out.print( "--> uri encode: " + preDefUri );

        return Uri.parse(preDefUri);
    }

    public static String  uriStr(final CupboardContract contract, final CupboardLoaderContract paragraph) {
        return "content://" + contract.authority() + paragraph.path();
    }

    public static UriTemplate uriByState(final Enum loaderId) {
        final CupboardContract contract = EnumTool.withEnum(loaderId).onType().annotation(CupboardContract.class);
        final CupboardLoaderContract paragraph = EnumTool.withEnum(loaderId).annotation(CupboardLoaderContract.class);

        return new UriTemplate( uriStr(contract, paragraph) );
    }

}
