package com.mahesaiqbal.learnrealm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mahesaiqbal.learnrealm.R;
import com.mahesaiqbal.learnrealm.helper.RealmHelper;
import com.mahesaiqbal.learnrealm.model.Mahasiswa;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSimpan, btnTampil;
    EditText nim, nama;
    String namaMahasiswa;
    Integer nimMahasiswa;
    Realm realm;
    RealmHelper realmHelper;
    Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSimpan = findViewById(R.id.btnSimpan);
        btnTampil = findViewById(R.id.btnTampil);
        nim = findViewById(R.id.nim);
        nama = findViewById(R.id.nama);

        //Set up Realm
        Realm.init(MainActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        btnSimpan.setOnClickListener(this);
        btnTampil.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan) {
            nimMahasiswa = Integer.parseInt(nim.getText().toString());
            namaMahasiswa = nama.getText().toString();

            if (!nimMahasiswa.equals("") && !namaMahasiswa.isEmpty()) {
                mahasiswa = new Mahasiswa();
                mahasiswa.setNim(nimMahasiswa);
                mahasiswa.setNama(namaMahasiswa);

                realmHelper = new RealmHelper(realm);
                realmHelper.save(mahasiswa);

                Toast.makeText(MainActivity.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();

                nim.setText("");
                nama.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Terdapat inputan yang kosong", Toast.LENGTH_SHORT).show();
            }
        } else if (view == btnTampil) {
            startActivity(new Intent(MainActivity.this, MahasiswaActivity.class));
        }
    }
}
