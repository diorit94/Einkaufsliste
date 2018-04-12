package com.codefrogapp.dioritbajrami.einkaufsliste.StartShoppingListView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

import static com.codefrogapp.dioritbajrami.einkaufsliste.StartShoppingListView.FireBaseShoppingClient.customAdapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.StartShoppingListView.FireBaseShoppingClient.models;

public class StartShoppingListViewActivity extends AppCompatActivity {

    ListView listView;
    FireBaseShoppingClient firebaseClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Einkauf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_shopping_list_view);

        listView = (ListView) findViewById(R.id.listview);
        firebaseClient = new FireBaseShoppingClient(this, listView);
        firebaseClient.refreshdata();

        customAdapter = new CustomAdapter(this, models);

        listView.setAdapter(customAdapter);
    }
}
