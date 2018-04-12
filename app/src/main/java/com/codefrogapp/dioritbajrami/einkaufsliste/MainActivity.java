package com.codefrogapp.dioritbajrami.einkaufsliste;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    static DatabaseReference databaseReference;
    static FirebaseAuth firebaseAuth;

    public static boolean TAGONCLICK;
    static Firebase fire;
    //RecyclerViewStuff
    private List<Einkaufsliste_Model> einkaufListe = new ArrayList<>();
    private RecyclerView recyclerView;
    private Einkaufsliste_Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewInitialization();
        addItems();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(getApplicationContext());
        fire = new Firebase("https://einkaufsliste-dc837.firebaseio.com/");
    }

    public void recyclerViewInitialization() {
        recyclerView = (RecyclerView) findViewById(R.id.einkauf_list_id);
        mAdapter = new Einkaufsliste_Adapter(einkaufListe, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void addItems() {
        Einkaufsliste_Model einkauf = new Einkaufsliste_Model("Einkaufliste", R.mipmap.ic_add_shopping_cart_white_24dp);
        einkaufListe.add(einkauf);

        einkauf = new Einkaufsliste_Model("Artikel hinzu", R.mipmap.ic_playlist_add_white_24dp);
        einkaufListe.add(einkauf);

        einkauf = new Einkaufsliste_Model("Einkauf starten", R.mipmap.ic_shopping_cart_white_24dp);
        einkaufListe.add(einkauf);

        einkauf = new Einkaufsliste_Model("Einkauf abschließen", R.drawable.flag);
        einkaufListe.add(einkauf);

        einkauf = new Einkaufsliste_Model("Administrator", R.mipmap.ic_account_box_white_24dp);
        einkaufListe.add(einkauf);

        mAdapter.notifyDataSetChanged();
    }

    public void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_einkauf:
                if (firebaseAuth.getCurrentUser() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Sind Sie sicher das Sie den Einkauf abschließen wollen?");
                    builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseReference.child("Shoplist").removeValue();
                            toastMessage("Neuer Einkauf wurde gestartet");
                        }
                    });
                    builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    builder.show();
                } else {
                    toastMessage("Nur der Administrator kann neue Einkäufe starten");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Drücke nochmal zurück um die App zu verlassen", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
