package com.touchableheroes.drafts.db.cupboard.xt.loader;

import android.net.Uri;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * Contract:
 *
 * schold not end with / ???
 *
 * Created by asiebert on 03.03.16.
 */
public class UriTemplate {

    private final String[] splitted;

    private final String tmpl;

    public UriTemplate(final String uriTmpl) {
        this.tmpl = uriTmpl;
        this.splitted = this.tmpl.split("/");

        System.out.println("--> TrackUri: " + this.tmpl);

    }

    public String createStr(final Object... args) {
        final StringBuilder buffer = new StringBuilder( this.tmpl.length() );
        int varIdx = 0;

        for (int splitIdx = 0; splitIdx < splitted.length; splitIdx++) {
            if( !isVar( splitted[splitIdx] ) ) {
                buffer.append( valueOf(splitted[splitIdx]) );
            } else {

                if( isValidVarIdx(args, varIdx) )
                    buffer.append(args[varIdx]);
                else
                    buffer.append( valueOf(splitted[splitIdx]) );

                varIdx++;
            }

            if( !isLastSplit(splitIdx) ) {
                buffer.append( "/" );
            }
        }

        return buffer.toString();
    }

    private String valueOf(final Object obj) {
        if( obj == null )
            return "";

        return String.valueOf(obj);
    }


    private boolean isValidVarIdx(final Object[] args, int varIdx) {
        if( args.length < 1 )
            return false;

        return (varIdx > -1 && varIdx < args.length );
    }

    public boolean isVar(final String part) {
        return ( part != null
                  && ("#".equals(part)
                      || "*".equals(part)));
    }

    private boolean isLastSplit(final int splitIdx) {
        return  (splitIdx > -1
                   && splitIdx == (splitted.length - 1));

    }


    public Uri create(final Object... args) {
        Uri rval = Uri.parse(createStr(args));

        System.out.println( "--> uriTmpl.create() : " + rval);

        return rval;
    }
}
