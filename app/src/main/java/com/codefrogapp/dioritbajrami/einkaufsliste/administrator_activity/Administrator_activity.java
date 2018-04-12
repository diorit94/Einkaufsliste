package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.MainActivity;
import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Administrator_activity extends AppCompatActivity {

    static FirebaseAuth firebaseAuth;
    static DatabaseReference databaseReference;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Empfehlungen");
        //Initialization();

        Fragment fragment1 = new administrator_list_fragment();
        Fragment fragment2 = new administrator_login_fragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (firebaseAuth.getCurrentUser() != null) {

            fragmentTransaction.add(R.id.content_frame, fragment1);
            fragmentTransaction.commit();

            linearLayout = (LinearLayout)findViewById(R.id.linearlayoutID);

            Snackbar snackbar = Snackbar
                    .make(linearLayout, "Du bist Angemeldet", Snackbar.LENGTH_LONG)
                    .setAction("Abmelden", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            firebaseAuth.signOut();
                            toastmessage("Abgemeldet");
                            startActivity(new Intent(Administrator_activity.this, MainActivity.class));
                        }
                    });
            //snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            setTitle("Empfehlungen");
        } else {
            fragmentTransaction.add(R.id.content_frame, fragment2);
            fragmentTransaction.commit();
            setTitle("Einloggen");
        }

    }



    public void toastmessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        // return true so that the mesnu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                firebaseAuth.signOut();
                toastmessage("Abgemeldet");
                startActivity(new Intent(Administrator_activity.this, MainActivity.class));
                return true;
            case R.id.action_empfelungen:
                showEmpfelungDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showEmpfelungDialog() {
        final EditText EditText;
        //SHOW INPUT DIALOG
        final Dialog d = new Dialog(this);
        d.setTitle("Empfehlung Hinzufügen");
        d.setContentView(R.layout.dialog_empfelung);

        EditText = (EditText) d.findViewById(R.id.empfelende_artikel_hinzufügen_id);
        Button saveBtn = (Button) d.findViewById(R.id.saveEmpfelungBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(EditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Bitte Empfehlung hinzufügen.", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.orderByChild("name").equalTo(EditText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), EditText.getText().toString() + " ist schon bereits bei den Empfehlungen", Toast.LENGTH_SHORT).show();
                        } else if (!dataSnapshot.exists()) {
                            saveOnline(EditText.getText().toString());
                            Toast.makeText(getApplicationContext(), EditText.getText().toString() + " Wurde hinzugefügt.", Toast.LENGTH_SHORT).show();
                            d.dismiss();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        //SHOW
        d.show();
    }

    public void saveOnline(String text) {
        EmpfelungenModel m = new EmpfelungenModel();
        m.setName(text);
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(m);
    }

}