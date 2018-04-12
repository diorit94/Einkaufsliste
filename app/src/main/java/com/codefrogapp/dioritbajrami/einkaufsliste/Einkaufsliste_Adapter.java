package com.codefrogapp.dioritbajrami.einkaufsliste;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClient;
import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFiltered;
import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFilteredPersonal;
import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.TabbedActivity;
import com.codefrogapp.dioritbajrami.einkaufsliste.StartShoppingListView.StartShoppingListViewActivity;
import com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.Administrator_activity;
import com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.EmpfelungenModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.codefrogapp.dioritbajrami.einkaufsliste.MainActivity.databaseReference;
import static com.codefrogapp.dioritbajrami.einkaufsliste.MainActivity.fire;
import static com.codefrogapp.dioritbajrami.einkaufsliste.MainActivity.firebaseAuth;

/**
 * Created by dioritbajrami on 23.02.18.
 */

public class Einkaufsliste_Adapter extends RecyclerView.Adapter<Einkaufsliste_ViewHolder.EinkaufslisteViewViewHolder> {

    public static List<Einkaufsliste_Model> einkaufsListe;
    public Context context;
    FirebaseClientFiltered fireBaseClientFiltered;
    FirebaseClientFilteredPersonal fireBaseClientFilteredPersonal;
    FirebaseClient fireBaseClient;

    static RecyclerView rvEmpfelung;

    public Einkaufsliste_Adapter(List<Einkaufsliste_Model> einkaufsListe, Context c) {
        this.einkaufsListe = einkaufsListe;
        this.context = c;
    }

    @Override
    public Einkaufsliste_ViewHolder.EinkaufslisteViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.einkauf_list_row, parent, false);

        return new Einkaufsliste_ViewHolder.EinkaufslisteViewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Einkaufsliste_ViewHolder.EinkaufslisteViewViewHolder holder, final int position) {
        final Einkaufsliste_Model einkaufsliste = einkaufsListe.get(position);

        holder.Einkaufsliste_Titel.setText(einkaufsliste.getName());
        holder.circleButton.setImageResource(einkaufsliste.getImageResource());

        if (position == 2) {
            if (firebaseAuth.getCurrentUser() == null) {
                holder.circleButton.setColor(Color.GRAY);
            }
        } else if(position == 3){
            if (firebaseAuth.getCurrentUser() == null) {
                holder.circleButton.setColor(Color.GRAY);
            }
        }

        holder.circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Intent i = new Intent(context, TabbedActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } else if (position == 1) {
                    showDialogggg(view);
                } else if (position == 2) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        Intent i = new Intent(context, StartShoppingListViewActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context, "Du musst Administrator sein um einen Einkauf zu starten!", Toast.LENGTH_SHORT).show();
                    }
                } else if(position == 3){
                    if (firebaseAuth.getCurrentUser() != null) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setMessage("Sind Sie sicher das Sie den Einkauf abschließen wollen?");
                        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                databaseReference.child("Shoplist").removeValue();
                                Toast.makeText(context, "Einkauf wurde abgeschloßen!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        builder.show();


                    } else {
                        Toast.makeText(context, "Du musst Administrator sein um einen Einkauf abzuschließen!", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (position == 4) {
                    Intent i = new Intent(context, Administrator_activity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });

        /*holder.setOnItemClick(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }
        });*/
    }


    //private static String [] items = new String[]{"Pizza","Milch","Kaffee","Bifi","Wasser","Hanuta","Snickers"};
    public void showDialogggg(final View view){
        final AutoCompleteTextView nameEditText;
        final EditText ammountEditText, personEditText;
        Button saveBtn;

        final Dialog d = new Dialog(view.getRootView().getContext());
        d.setTitle("Einkaufen");
        d.setContentView(R.layout.custom_dialog2);



        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1);

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


        nameEditText = (AutoCompleteTextView)d.findViewById(R.id.nameEditText);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line,items);
        nameEditText.setAdapter(autoComplete);
        nameEditText.setThreshold(0);

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
                    Toast.makeText(view.getRootView().getContext(), "Bitte alle felder füllen", Toast.LENGTH_SHORT).show();
                } else if (verwaltungRadio.isChecked()) {
                    saveOnline(nameEditText.getText().toString(), Integer.parseInt(ammountEditText.getText().toString()), "Verwaltung",false,"Verwaltung", view);
                    d.dismiss();

                } else if (itRadio.isChecked()) {
                    saveOnline(nameEditText.getText().toString(),Integer.parseInt(ammountEditText.getText().toString()),"IT", false, "IT", view);
                    nameEditText.setText("");
                    ammountEditText.setText("");
                    //verwalterEditText.setText("");
                    d.dismiss();
                } else if (personRadio.isChecked()) {
                    saveOnlinePerson(nameEditText.getText().toString(),Integer.parseInt(ammountEditText.getText().toString()),"(Person)"+personEditText.getText().toString(), false);
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

    public void saveOnline(final String name, final int ammount, final String verwalter, boolean articel, final String type, final View v) {
        final Model m = new Model();
        m.setName(name);
        m.setAmmount(ammount);
        m.setVerwalter(verwalter);
        m.setArticel(articel);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshotsnap) {
                databaseReference.child("Empfehlungen").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists() && !dataSnapshotsnap.exists()){
                            EmpfelungenModel m = new EmpfelungenModel();
                            m.setName(name);
                            String id = databaseReference.push().getKey();
                            databaseReference.child("Empfehlungen").child(id).setValue(m);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String id = databaseReference.push().getKey();
        m.setFirebaseid(id);
        databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (final com.google.firebase.database.DataSnapshot fireDataSnapshot : dataSnapshot.getChildren()) {

                    final String verwaltung = fireDataSnapshot.child("verwalter").getValue(String.class);
                    final int zahl = fireDataSnapshot.child("ammount").getValue(Integer.class);

                    if (fireDataSnapshot.exists() && verwaltung.equals(type)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                        builder.setMessage("Es existiert bereits " + zahl + " " +name + " auf der " + verwalter +" liste, noch " + ammount + " hinzufügen?");
                        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int beforeammount = fireDataSnapshot.child("ammount").getValue(Integer.class);
                                fireDataSnapshot.getRef().child("ammount").setValue(beforeammount + ammount);
                            }
                        });
                        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        builder.show();

                        return;

                    }
                }if (!dataSnapshot.exists() || verwalter.equals(type)) {
                    fire.child("Shoplist").child(id).setValue(m);
                    Toast.makeText(context,name + " Wurde zur " + verwalter + " Liste hinzugefügt", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*String id = databaseReference.push().getKey();
        m.setFirebaseid(id);
        fire.child("Shoplist").child(id).setValue(m);*/
    }

    public void saveOnlinePerson(final String name, final int ammount, final String verwalter, boolean articel) {
        final Model m = new Model();
        m.setName(name);
        m.setAmmount(ammount);
        m.setVerwalter(verwalter);
        m.setArticel(articel);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Shoplist").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshotsnap) {
                databaseReference.child("Empfehlungen").orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists() && !dataSnapshotsnap.exists()){
                            EmpfelungenModel m = new EmpfelungenModel();
                            m.setName(name);
                            String id = databaseReference.push().getKey();
                            databaseReference.child("Empfehlungen").child(id).setValue(m);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String id = databaseReference.push().getKey();
        m.setFirebaseid(id);
        fire.child("Shoplist").child(id).setValue(m);
        Toast.makeText(context,name + " Wurde zur Personen Liste hinzugefügt", Toast.LENGTH_SHORT).show();
    }
    /*public void ndryshimi(String texti){
        nameEditText.setText(texti);
    }

    public void hide(){
        rvEmpfelung.setVisibility(View.GONE);
    }*/


    @Override
    public int getItemCount() {
        return einkaufsListe.size();
    }
}
