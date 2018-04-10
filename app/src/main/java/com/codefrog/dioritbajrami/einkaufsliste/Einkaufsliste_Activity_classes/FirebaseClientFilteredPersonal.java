package com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codefrog.dioritbajrami.einkaufsliste.administrator_activity.EmpfelungenModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 28.02.18.
 */

public class FirebaseClientFilteredPersonal {

    Context c;
    //String DB_URL;
    RecyclerView rv;
    DatabaseReference databaseReference;
    Firebase fire;
    static ArrayList<Model> modelsPersonal = new ArrayList<>();

    static AdapterClass adapter;

    public FirebaseClientFilteredPersonal(Context c/*, String DB_URL*/, RecyclerView rv) {
        this.c = c;
        //this.DB_URL = DB_URL;
        this.rv = rv;

        //INITIALIZE
        Firebase.setAndroidContext(c);

        //INSTANTIATE
        fire = new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }

    //SAVE
    public void saveOnline(final String name, int ammount, String verwalter, boolean articel) {
        Model m = new Model();
        m.setName(name);
        m.setAmmount(ammount);
        m.setVerwalter(verwalter);
        m.setArticel(articel);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshotsnap) {
                databaseReference.child("Empfehlungen").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists() && !dataSnapshotsnap.exists()) {
                            EmpfelungenModel m = new EmpfelungenModel();
                            m.setName(name);
                            String id = databaseReference.push().getKey();
                            databaseReference.child("Empfehlungen").child(id).setValue(m);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final String id = databaseReference.push().getKey();
        m.setFirebaseid(id);
        fire.child("Shoplist").child(id).setValue(m);
        Toast.makeText(c,name + " Wurde zur Personen Liste hinzugefügt", Toast.LENGTH_SHORT).show();
    }

    //RETRIEVE
    public void refreshData() {
        fire.child("Shoplist").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
                adapter.notifyDataSetChanged(); //notify
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUpdates(DataSnapshot dataSnapshot) {
        modelsPersonal.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Model m = new Model();
            if (!ds.getValue(Model.class).getVerwalter().equals("Verwaltung") && !ds.getValue(Model.class).getVerwalter().equals("IT")) {
                m.setName(ds.getValue(Model.class).getName());
                m.setAmmount(ds.getValue(Model.class).getAmmount());
                m.setVerwalter(ds.getValue(Model.class).getVerwalter());
                m.setFirebaseid(ds.getValue(Model.class).getFirebaseid());

                modelsPersonal.add(m);
            }
        }

        if (modelsPersonal.size() > 0) {
        } else {
            //Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }

}
