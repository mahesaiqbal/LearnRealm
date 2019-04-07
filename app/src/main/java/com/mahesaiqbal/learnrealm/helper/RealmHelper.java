package com.mahesaiqbal.learnrealm.helper;

import android.util.Log;

import com.mahesaiqbal.learnrealm.model.Mahasiswa;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    // Create
    public void save(final Mahasiswa mahasiswa) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null) {
                    Log.d("Created", "Database was created");
                    Number currentIdNum = realm.where(Mahasiswa.class).max("id");

                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }

                    mahasiswa.setId(nextId);
                    Mahasiswa model = realm.copyToRealm(mahasiswa);
                } else {
                    Log.d("Not created", "Database doesn't exist");
                }
            }
        });
    }

    // Select
    public List<Mahasiswa> getAllMahasiswa() {
        RealmResults<Mahasiswa> results = realm.where(Mahasiswa.class).findAll();
        return results;
    }

    // Update
    public void update(final Integer id, final Integer nim, final String nama) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Mahasiswa mahasiswa = realm.where(Mahasiswa.class)
                        .equalTo("id", id)
                        .findFirst();
                mahasiswa.setNim(nim);
                mahasiswa.setNama(nama);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Success", "Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    // Delete
    public void delete(Integer id) {
        final RealmResults<Mahasiswa> mahasiswa = realm.where(Mahasiswa.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mahasiswa.deleteFromRealm(0);
            }
        });
    }
}
