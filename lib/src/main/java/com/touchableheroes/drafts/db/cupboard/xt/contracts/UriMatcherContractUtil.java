package com.touchableheroes.drafts.db.cupboard.xt.contracts;

import com.touchableheroes.drafts.core.tools.EnumTool;

import java.lang.annotation.Annotation;

/**
 * Created by asiebert on 28.04.2017.
 */

public class UriMatcherContractUtil {

    public static final UriOperation DEFAULT_OPERATIONS = new UriOperation() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return UriOperation.class;
        }

        @Override
        public InsertContract insert() {
            return null;
        }

        @Override
        public QueryContract query() {
            return null;
        }

        @Override
        public Class<?> update() {
            return null;
        }

        @Override
        public Class<?> delete() {
            return null;
        }
    };

    public static final UriMatcherContract DEFAULT_URI_CONTRACT =
            new UriMatcherContract() {

                @Override
                public Class<? extends Annotation> annotationType() {
                    return UriMatcherContract.class;
                }

                @Override
                public UriOperation operations() {
                    return DEFAULT_OPERATIONS;
                }

                @Override
                public String mimeType() {
                    return "";
                }
            };

    public static UriMatcherContract load( final Enum uriEnum ) {
        final UriMatcherContract contract = EnumTool.withEnum(uriEnum).annotation(UriMatcherContract.class);

        if( contract == null )
            return DEFAULT_URI_CONTRACT;

        return contract;
    }
}
