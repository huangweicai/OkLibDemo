package com.oklib.util.sugar;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.oklib.util.sugar.helper.ManifestHelper;
import com.oklib.util.sugar.util.SugarCursorFactory;

import static com.oklib.util.sugar.SugarContext.getDbConfiguration;
import static com.oklib.util.sugar.helper.ManifestHelper.getDatabaseVersion;
import static com.oklib.util.sugar.helper.ManifestHelper.getDbName;
import static com.oklib.util.sugar.util.ContextUtil.getContext;


public class SugarDb extends SQLiteOpenHelper {
    private static final String LOG_TAG = "Sugar";

    private final SchemaGenerator schemaGenerator;
    private SQLiteDatabase sqLiteDatabase;
    private int openedConnections = 0;

    //Prevent instantiation
    private SugarDb() {
        super(getContext(), getDbName(), new SugarCursorFactory(ManifestHelper.isDebugEnabled()), getDatabaseVersion());
        schemaGenerator = SchemaGenerator.getInstance();
    }

    public static SugarDb getInstance() {
        return new SugarDb();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        schemaGenerator.createDatabase(sqLiteDatabase);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        final SugarDbConfiguration configuration = getDbConfiguration();

        if (null != configuration) {
            db.setLocale(configuration.getDatabaseLocale());
            db.setMaximumSize(configuration.getMaxSize());
            db.setPageSize(configuration.getPageSize());
        }

        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        schemaGenerator.doUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public synchronized SQLiteDatabase getDB() {
        if (this.sqLiteDatabase == null) {
            this.sqLiteDatabase = getWritableDatabase();
        }

        return this.sqLiteDatabase;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if(ManifestHelper.isDebugEnabled()) {
            Log.d(LOG_TAG, "getReadableDatabase");
        }
        openedConnections++;
        return super.getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        if(ManifestHelper.isDebugEnabled()) {
            Log.d(LOG_TAG, "getReadableDatabase");
        }
        openedConnections--;
        if(openedConnections == 0) {
            if(ManifestHelper.isDebugEnabled()) {
                Log.d(LOG_TAG, "closing");
            }
            super.close();
        }
    }
}
