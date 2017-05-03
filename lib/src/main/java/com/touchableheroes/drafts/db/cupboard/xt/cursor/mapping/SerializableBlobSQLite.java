package com.touchableheroes.drafts.db.cupboard.xt.cursor.mapping;

import android.database.Cursor;

import com.touchableheroes.drafts.core.logger.Tracer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Created by asiebert on 02.05.2017.
 */
public class SerializableBlobSQLite<T extends Serializable>
        extends AbstractBlobSQLite<T> {

    @Override
    protected T convert(
            final byte[] blob) {
        final ByteArrayInputStream bin = new ByteArrayInputStream(blob);
        ObjectInputStream oin = null;

        try {
            oin = new ObjectInputStream(bin);
            return (T) oin.readObject();
        } catch (final Throwable x) {
            if (Tracer.isDevMode()) {
                throw new IllegalStateException("Couldn't read searializable. Check parent Exception!", x);
            } else {
                x.printStackTrace();
                return null;
            }
        } finally {
            if( oin != null ) {
                try {
                    oin.close();
                } catch (Throwable tx){}
            }
        }

    }

}
