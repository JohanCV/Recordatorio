package com.jcv.recordatorio;

import android.app.Application;

import com.jcv.recordatorio.modelo.Recordatorio;
import com.jcv.recordatorio.modelo.Tarea;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Inicio extends Application {

    public static AtomicInteger recordatorioId = new AtomicInteger();
    public static AtomicInteger tareasId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(getApplicationContext());
        setUpRealConfig();
        Realm realm = Realm.getDefaultInstance();
        recordatorioId = getByTabla(realm, Recordatorio.class);
        tareasId = getByTabla(realm, Tarea.class);

        realm.close();
    }
    private void setUpRealConfig() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
    private <T extends RealmObject> AtomicInteger getByTabla(Realm realm, Class<T> anyClass) {
        RealmResults<T> results = realm.where(anyClass).findAll();

        return (results.size()>0)? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}
