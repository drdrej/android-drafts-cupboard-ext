package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import android.content.ContentValues;
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


    public static Uri createInsert( final Enum uri ) {
        final EnumTool.EnumWrapper enumWrapper = EnumTool.withEnum( uri );
        final UriMatcherContract def = enumWrapper.annotation(UriMatcherContract.class);

        final Uri rval = ContractUriUtil.uriByState(uri).create();

        return rval;

/*        final Uri resultUri = cupboard()
                .withContext( getContext() )
                .put(insertUri, entity);
*/
/*
        ContentValues contentValues = new ContentValues();

        contentValues.put( "_id", 1 );
        contentValues.put( "name", "Name xa,xa" );
*/
    }

}
