package com.touchableheroes.drafts.db.cupboard.xt.contracts.defaults;

import com.touchableheroes.drafts.db.cupboard.xt.contracts.DeleteContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.InsertContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.QueryContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.SQliteTypeContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriMatcherContract;
import com.touchableheroes.drafts.db.cupboard.xt.contracts.UriOperation;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping.SQLiteTypeMapping;
import com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping.StringSQLiteTypeMapping;

import java.lang.annotation.Annotation;

/**
 * Created by asiebert on 03.05.2017.
 */

public class Defaults {

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
        public DeleteContract delete() {
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

    public static final SQliteTypeContract DEFAULT_SQLITE_TYPE_MAPPING = new SQliteTypeContract() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return SQliteTypeContract.class;
        }

        @Override
        public Class<? extends SQLiteTypeMapping> type() {
            return StringSQLiteTypeMapping.class;
        }
    };

}
