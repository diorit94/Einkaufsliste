package com.codefrogapp.dioritbajrami.einkaufsliste.Start_shopping_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

import static com.codefrogapp.dioritbajrami.einkaufsliste.Start_shopping_activity.FirebaseShoppingClient.adapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Start_shopping_activity.FirebaseShoppingClient.models;


public class StartShopping extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv;
    FirebaseShoppingClient firebaseShoppingClient;
    DividerItemDecoration mDividerItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_shopping);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Einkauf");

        rv = (RecyclerView) findViewById(R.id.startShoppingRecyclerViewID);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firebaseShoppingClient=new FirebaseShoppingClient(this,rv);
        firebaseShoppingClient.refreshData();

        adapter = new ShoppingAdapter(this, models);
        rv.setAdapter(adapter);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
