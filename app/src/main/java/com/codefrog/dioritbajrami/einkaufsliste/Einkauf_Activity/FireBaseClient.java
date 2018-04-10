package com.codefrog.dioritbajrami.einkaufsliste.Einkauf_Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 24.02.18.
 */

public class FireBaseClient {

    Context c;
    //String DB_URL;
    RecyclerView rv;

    Firebase fire;
    static ArrayList<Model> datamodels = new ArrayList<>();
    DataSnapshot dataSnapshot;
    static EinkaufAdapter adapter;

    public FireBaseClient(Context c/*, String DB_URL*/, RecyclerView rv) {
        this.c = c;
        //this.DB_URL = DB_URL;
        this.rv = rv;

        //INITIALIZE
        Firebase.setAndroidContext(c);

        //INSTANTIATE
        fire = new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }

    //SAVE
    public void saveOnline(String name, int ammount, String verwalter,boolean articel) {
        Model m = new Model();
        m.setName(name);
        m.setAmmount(ammount);
        m.setVerwalter(verwalter);
        m.setArticel(articel);

        //CHECK IS SOMETHING IS THERE IN THE DATABSE EQUALS WITH WHAT YOU WANT TO ADD
        /*FirebaseDatabase.getInstance().getReference().child("Shoplist")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.getValue(Model.class).getName().equals("Wasser")){
                                Toast.makeText(c,"Existiert", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/

        fire.child("Shoplist").push().setValue(m);
    }

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
        datamodels.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            Model m = new Model();
            m.setName(ds.getValue(Model.class).getName());
            m.setAmmount(ds.getValue(Model.class).getAmmount());
            m.setVerwalter(ds.getValue(Model.class).getVerwalter());
            datamodels.add(m);
        }
    }

}
