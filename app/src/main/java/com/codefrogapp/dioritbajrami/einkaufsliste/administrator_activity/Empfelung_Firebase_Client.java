package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 06.04.18.
 */

public class Empfelung_Firebase_Client {

    Context c;
    RecyclerView rv;
    static Firebase fire;

    public static ArrayList<EmpfelungenModel> empfehlungen = new ArrayList<>();
    public static EmpfelungAdapter adapter;

    public Empfelung_Firebase_Client(Context c/*, String DB_URL*/, RecyclerView rv) {
        this.c = c;
        //this.DB_URL = DB_URL;
        this.rv = rv;

        //INITIALIZE
        Firebase.setAndroidContext(c);


        //INSTANTIATE
        fire = new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }

    //RETRIEVE
    public void refreshData() {
        fire.child("Empfehlungen").orderByChild("name").addValueEventListener(new com.firebase.client.ValueEventListener() {
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
        empfehlungen.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            EmpfelungenModel em = new EmpfelungenModel();
            em.setName(ds.getValue(EmpfelungenModel.class).getName());
            empfehlungen.add(em);

        }

        if (empfehlungen.size() > 0) {
        } else {
            //Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }


}
