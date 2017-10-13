package com.oklib.util.sugar.inflater.field;

import android.database.Cursor;
import android.util.Log;

import com.oklib.util.sugar.SugarRecord;
import com.oklib.util.sugar.helper.NamingHelper;

import java.lang.reflect.Field;

/**
 * Created by Łukasz Wesołowski on 03.08.2016.
 */
public class EntityFieldInflater extends FieldInflater {
    private static final String LOG_TAG = "EntityFieldInflater";

    public EntityFieldInflater(Field field, Cursor cursor, Object object, Class<?> fieldType) {
        super(field, cursor, object, fieldType);
    }

    @Override
    public void inflate() {
        try {
            long id = cursor.getLong(cursor.getColumnIndex(NamingHelper.toColumnName(field)));
            field.set(object, (id > 0) ? SugarRecord.findById(fieldType, id) : null);
        } catch (IllegalAccessException e) {
            Log.e(LOG_TAG, String.format("Error while inflating entity field %s", field), e);
        }
    }
}
