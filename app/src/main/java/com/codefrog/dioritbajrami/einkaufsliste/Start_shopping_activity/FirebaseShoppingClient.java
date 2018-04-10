package com.codefrog.dioritbajrami.einkaufsliste.Start_shopping_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 27.02.18.
 */

public class FirebaseShoppingClient {
    Context c;
    //String DB_URL;
    RecyclerView rv;

    Firebase fire;
    static ArrayList<Model> models = new ArrayList<>();
    static ShoppingAdapter adapter;

    public FirebaseShoppingClient(Context c/*, String DB_URL*/, RecyclerView rv) {
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
        fire.child("Shoplist").orderByChild("name").addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUpdates(DataSnapshot dataSnapshot) {
        models.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Model m = new Model();
            m.setName(ds.getValue(Model.class).getName());
            m.setAmmount(ds.getValue(Model.class).getAmmount());
            m.setVerwalter(ds.getValue(Model.class).getVerwalter());
            m.setArticel(ds.getValue(Model.class).getArticel());
            models.add(m);
        }

        if (models.size() > 0) {
            adapter = new ShoppingAdapter(c, models);
            rv.setAdapter(adapter);
        } else {
            //Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
