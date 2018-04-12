package com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

/**
 * Created by dioritbajrami on 25.02.18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.EmpfelungenModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 24.02.18.
 */

public class FirebaseClientFiltered {

    Context c;
    //String DB_URL;
    RecyclerView rv;

    Firebase fire;
    ArrayList<Model> models = new ArrayList<>();
    static ArrayList<Model> modelsVerwaltung = new ArrayList<>();
    DatabaseReference databaseReference;

    static AdapterClass adapter;

    public FirebaseClientFiltered(Context c/*, String DB_URL*/, RecyclerView rv) {
        this.c = c;
        //this.DB_URL = DB_URL;
        this.rv = rv;

        //INITIALIZE
        Firebase.setAndroidContext(c);

        //INSTANTIATE
        fire = new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }

    public void saveOnline(final String name, final int ammount, final String verwalter, boolean articel) {
        final Model m = new Model();
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
        databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (final com.google.firebase.database.DataSnapshot fireDataSnapshot : dataSnapshot.getChildren()) {

                    final String verwaltung = fireDataSnapshot.child("verwalter").getValue(String.class);
                    final int zahl = fireDataSnapshot.child("ammount").getValue(Integer.class);

                    if (fireDataSnapshot.exists() && verwaltung.equals("Verwaltung")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setMessage("Es existiert bereits " + zahl + " " +name + " auf der Verwaltungs liste, noch " + ammount + " hinzufügen?");
                        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int beforeammount = fireDataSnapshot.child("ammount").getValue(Integer.class);
                                fireDataSnapshot.getRef().child("ammount").setValue(beforeammount + ammount);
                            }
                        });
                        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        builder.show();

                        return;

                    }
                }if (!dataSnapshot.exists() || verwalter.equals("Verwaltung")) {
                    fire.child("Shoplist").child(id).setValue(m);
                    Toast.makeText(c,name + " Wurde zur Verwaltungs Liste hinzugefügt", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot fireDataSnapshot : dataSnapshot.getChildren()) {
                    final String verwaltung = fireDataSnapshot.child("verwalter").getValue(String.class);

                    if (dataSnapshot.exists() && verwaltung.equals("Verwaltung")) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(c);
                        }
                        builder.setTitle("Existiert bereits")
                                .setMessage(name + " Existierts bereits in der VERWALTUNG LISTE, trotzdem hinzufügen?")
                                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (com.google.firebase.database.DataSnapshot fireSnapshot : dataSnapshot.getChildren()) {
                                            if (verwaltung.equals("Verwaltung")) {
                                                int beforeammount = fireSnapshot.child("ammount").getValue(Integer.class);
                                                fireSnapshot.getRef().child("ammount").setValue(beforeammount + ammount);
                                            }
                                        }
                                        //fire.child("Shoplist").child(id).setValue(m);
                                    }
                                })
                                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {
                        fire.child("Shoplist").child(id).setValue(m);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        /*final String id = databaseReference.push().getKey();
        m.setFirebaseid(id);
        fire.child("Shoplist").child(id).setValue(m);*/
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
        modelsVerwaltung.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Model m = new Model();
            if (ds.getValue(Model.class).getVerwalter().equals("Verwaltung")) {
                m.setName(ds.getValue(Model.class).getName());
                m.setAmmount(ds.getValue(Model.class).getAmmount());
                m.setVerwalter(ds.getValue(Model.class).getVerwalter());
                m.setFirebaseid(ds.getValue(Model.class).getFirebaseid());

                modelsVerwaltung.add(m);
            }

        }

        if (modelsVerwaltung.size() > 0) {
        } else {
            //Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }

}
