package com.sachi.lifetweak.db;

import android.content.Context;
import android.util.Log;

import com.sachi.lifetweak.model.LifeTweak;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by jombay on 28/3/16.
 */
public class LifeTweaksStore {
    private Realm realm;

    public LifeTweaksStore(Context context) {
        realm = ProtoRealm.getInstance(context).getRealm();
    }

    public static LifeTweaksStore getInstance(Context context) {
        return new LifeTweaksStore(context);
    }

    public void writeMessage(final LifeTweak lifeTweak) {
        Log.d("writing to db", lifeTweak.getLifeHacks());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm1) {
                realm1.copyToRealm(lifeTweak);
            }
        });
    }

    public RealmResults<LifeTweak> getAllLifeTweaks() {
        return realm.where(LifeTweak.class).findAll();

    }

    public void isFavorite(final int id, final int value) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                LifeTweak lifeTweak = realm.where(LifeTweak.class).equalTo("id", id).findFirst();
                lifeTweak.setIsFavorite(value);
                realm.copyToRealm(lifeTweak);
            }
        });
    }

    public void refresh() {
        if (realm != null) {
            realm.refresh();
        }
    }

    public int getIsFavorite(int id) {
        return realm.where(LifeTweak.class).equalTo("id", id).findFirst().getIsFavorite();
    }

    public RealmResults<LifeTweak> getAllFavorites() {
        return realm.where(LifeTweak.class).equalTo("isFavorite", 1).findAll();
    }

    public boolean isLifeHackExist(String id) {
        RealmQuery<LifeTweak> query = realm.where(LifeTweak.class)
                .equalTo("id", id);
        return query.count() > 0;
    }

    public boolean isLifeTweaksExist() {
        RealmQuery<LifeTweak> query = realm.where(LifeTweak.class);
        return query.count() > 0;

    }

    public void close() {
        realm.close();
    }
}
