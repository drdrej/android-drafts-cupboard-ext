package com.touchableheroes.drafts.db.cupboard.xt.commands.cupboard;

import com.touchableheroes.drafts.core.tools.ArrayTool;
import com.touchableheroes.drafts.core.tools.StringTool;

/**
 * Created by asiebert on 09.05.2017.
 */

public class AbstractSelectionParams {

    protected static String createSelection (
            final String config,
            final String arg) {
        final boolean a1 = StringTool.isEmpty( config );
        final boolean a2 = StringTool.isEmpty( arg );

        if( a1 && a2 ) {
            return null;
        }

        if( a1 && !a2 ) {
            return arg;
        }

        if( !a1 && a2 ) {
            return config;
        }

        return concat( "(", arg, ") and (", config, ")" );
    }

    private static String concat(final String... values) {
        final StringBuilder b = new StringBuilder( 10 );

        int co = 0;
        for ( final String val : values ) {
            if( co > 0 ) {
                b.append( " " );
            }

            if( val != null ) {
                b.append( val );
            }

            b.append( " " );

            co++;
        }

        return b.toString();
    }

    public String[] createArgs(final String[] config,
                               final String[] args) {

        if( !ArrayTool.isEmpty(args) )
            return args;

        if( !ArrayTool.isEmpty(config))
            return config;

        return null;
    }

}
