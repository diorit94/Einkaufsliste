package com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity.FireBaseClient.adapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity.FireBaseClient.datamodels;


public class Einkauf_activity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab;
    RecyclerView rv;

    FireBaseClient fireBaseClient;
    static DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Einkaufsliste");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einkauf_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView) findViewById(R.id.EinkaufRecyclerViewID);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new EinkaufAdapter(this, datamodels);
        rv.setAdapter(adapter);

        fireBaseClient = new FireBaseClient(getApplicationContext(), rv);
        fireBaseClient.refreshData();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        databaseReference = FirebaseDatabase.getInstance().getReference();

        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == fab) {
            displayDialog();
        }
    }

    private void displayDialog() {
        final EditText nameEditText, ammountEditText, personEditText;
        Button saveBtn;

        final Dialog d = new Dialog(this);
        d.setTitle("Einkaufen");
        d.setContentView(R.layout.custom_dialog2);

        nameEditText = (EditText) d.findViewById(R.id.nameEditText);
        ammountEditText = (EditText) d.findViewById(R.id.ammountEditText);
        personEditText = (EditText) d.findViewById(R.id.personEditText);

        final RadioButton verwaltungRadio = (RadioButton) d.findViewById(R.id.radio_verwaltung);
        final RadioButton itRadio = (RadioButton) d.findViewById(R.id.radio_it);
        final RadioButton personRadio = (RadioButton) d.findViewById(R.id.radio_person);

        saveBtn = (Button) d.findViewById(R.id.saveBtn);
        personEditText.setVisibility(View.GONE);

        personRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(personRadio.isChecked()){
                    personEditText.setVisibility(View.VISIBLE);
                } else if(!personRadio.isChecked()){
                    personEditText.setVisibility(View.GONE);
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verwaltungRadio.isChecked() && !itRadio.isChecked() && !personRadio.isChecked() || nameEditText.getText().toString().isEmpty() || ammountEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Bitte alle felder füllen", Toast.LENGTH_SHORT).show();
                } else if (verwaltungRadio.isChecked()) {
                    //fireBaseClient.saveOnline(nameEditText.getText().toString(), Integer.parseInt(ammountEditText.getText().toString()), "Verwaltung", false);
                    nameEditText.setText("");
                    ammountEditText.setText("");
                    //verwalterEditText.setText("");
                    d.dismiss();
                } else if (itRadio.isChecked()) {
                    //fireBaseClient.saveOnline(nameEditText.getText().toString(), Integer.parseInt(ammountEditText.getText().toString()), "IT", false);
                    nameEditText.setText("");
                    ammountEditText.setText("");
                    //verwalterEditText.setText("");
                    d.dismiss();
                } else if (personRadio.isChecked()) {
                    //fireBaseClient.saveOnline(nameEditText.getText().toString(), Integer.parseInt(ammountEditText.getText().toString()), "(Personal)"+personEditText.getText().toString(), false);
                    nameEditText.setText("");
                    ammountEditText.setText("");
                    //verwalterEditText.setText("");
                    d.dismiss();
                }

            }
        });

        //SHOW
        d.show();
    }
}
