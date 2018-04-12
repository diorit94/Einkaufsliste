package com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.app.Dialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TabbedActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "TabbedActivity";

    private SectionsPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;
    FloatingActionButton fab;
    Toolbar toolbar;
    Button saveBtn;
    static FirebaseClient fireBaseClient;
    static FirebaseClientFiltered fireBaseClientFiltered;
    static FirebaseClientFilteredPersonal fireBaseClientFilteredPersonal;
    static DatabaseReference databaseReference;
    static String key;
    static CoordinatorLayout coordinatorLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        //INITIALIZE ALL VARIABLES
        Initialization();

        toolbar.setTitle("Einkaufsliste");

        mSectionPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //fireBaseClient.refreshData();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        if(view == fab){
            displayDialog();
        }
    }

    public void displayDialog() {
        final AutoCompleteTextView nameEditTextField;

        final EditText ammountEditText, personEditText;
        final Button saveBtn;
        final Dialog d = new Dialog(this);
        d.setTitle("Einkaufen");
        d.setContentView(R.layout.custom_dialog2);

        ammountEditText = (EditText) d.findViewById(R.id.ammountEditText);
        personEditText = (EditText) d.findViewById(R.id.personEditText);

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        databaseReference.child("Empfehlungen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Basically, this says "For each DataSnapshot *Data* in dataSnapshot, do what's inside the method.
                for (DataSnapshot suggestionSnapshot : dataSnapshot.getChildren()){
                    //Get the suggestion by childing the key of the string you want to get.
                    String suggestion = suggestionSnapshot.child("name").getValue(String.class);
                    //Add the retrieved string to the list
                    autoComplete.add(suggestion);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        nameEditTextField = (AutoCompleteTextView)d.findViewById(R.id.nameEditText);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line,items);
        nameEditTextField.setAdapter(autoComplete);
        nameEditTextField.setThreshold(1);


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
                String vlera = nameEditTextField.getText().toString();
                if (!verwaltungRadio.isChecked() && !itRadio.isChecked() && !personRadio.isChecked() || nameEditTextField.getText().toString().isEmpty() || ammountEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Bitte alle Felder füllen", Toast.LENGTH_SHORT).show();
                } else if (verwaltungRadio.isChecked()) {
                    fireBaseClientFiltered.saveOnline(vlera,Integer.parseInt(ammountEditText.getText().toString()),"Verwaltung", false);
                    nameEditTextField.setText("");
                    ammountEditText.setText("");

                    //verwalterEditText.setText("");
                    d.dismiss();
                } else if (itRadio.isChecked()) {
                    fireBaseClient.saveOnline(vlera,Integer.parseInt(ammountEditText.getText().toString()),"IT", false);
                    nameEditTextField.setText("");
                    ammountEditText.setText("");

                    //verwalterEditText.setText("");
                    d.dismiss();
                } else if (personRadio.isChecked()) {
                    fireBaseClientFilteredPersonal.saveOnline(vlera,Integer.parseInt(ammountEditText.getText().toString()),"(Person)"+personEditText.getText().toString(), false);
                    nameEditTextField.setText("");
                    ammountEditText.setText("");

                    //verwalterEditText.setText("");
                    d.dismiss();
                }

            }
        });

        //SHOW
        d.show();
    }


    public void Initialization(){
        fab = (FloatingActionButton) findViewById(R.id.addItemFab);
        fab.setOnClickListener(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mViewPager = (ViewPager)findViewById(R.id.container);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_content);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "IT");
        adapter.addFragment(new Tab2Fragment(), "Verwaltung");
        adapter.addFragment(new Tab3Fragment(), "Person");
        viewPager.setAdapter(adapter);
    }

}
