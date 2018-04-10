package com.codefrog.dioritbajrami.einkaufsliste.StartShoppingListView;

import android.content.Context;
import android.widget.ListView;

import com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by dioritbajrami on 03.03.18.
 */

public class FireBaseShoppingClient {
    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    static ArrayList<Model> models= new ArrayList<>();
    static CustomAdapter customAdapter;


    public  FireBaseShoppingClient(Context c/* String DB_URL*/, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;


        Firebase.setAndroidContext(c);
        firebase=new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }



    public  void refreshdata()
    {
        firebase.child("Shoplist").orderByChild("name").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getupdates(dataSnapshot);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getupdates(DataSnapshot dataSnapshot){

        models.clear();

        for(DataSnapshot ds :dataSnapshot.getChildren()){
            Model m= new Model();
            m.setName(ds.getValue(Model.class).getName());
            m.setAmmount(ds.getValue(Model.class).getAmmount());
            m.setVerwalter(ds.getValue(Model.class).getVerwalter());
            m.setArticel(ds.getValue(Model.class).getArticel());
            m.setFirebaseid(ds.getValue(Model.class).getFirebaseid());
            models.add(m);
        }

    }

}
