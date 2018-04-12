package com.codefrogapp.dioritbajrami.einkaufsliste.StartShoppingListView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by dioritbajrami on 03.03.18.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Model> models;
    LayoutInflater inflater;


    public CustomAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertview, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertview == null) {
            convertview = inflater.inflate(R.layout.startshoppingrow, viewGroup, false);

        }

        final MyHolder holder = new MyHolder(convertview);

        holder.nameKaufTxt.setText(models.get(i).getName());
        holder.ammountKaufTxt.setText(String.valueOf(models.get(i).getAmmount()) + "x ");

        if (models.get(i).getVerwalter().equals("Verwaltung")) {
            holder.verwaltungKaufTxt.setText("Verwaltung");
        } else if (models.get(i).getVerwalter().equals("IT")) {
            holder.verwaltungKaufTxt.setText("IT");
        } else {
            holder.verwaltungKaufTxt.setText(models.get(i).getVerwalter());
        }


        if (models.get(i).getArticel()) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));
            holder.checkBox.setChecked(true);
        } else if (!models.get(i).getArticel()) {
            holder.linearLayout.setBackgroundColor(0x00000000);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));
                    boughtArticle(models.get(i).getFirebaseid(), true);

                } else {
                    holder.linearLayout.setBackgroundColor(0x00000000);
                    boughtArticle(models.get(i).getFirebaseid(), false);

                }
            }
        });




        return convertview;
    }


    /*public void boughtArticle(String name, final boolean b) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Shoplist").orderByChild("name").equalTo(name);


        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().child("articel").setValue(b);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }*/


    public void boughtArticle(String key, final boolean b) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query fireQuery = ref.child("Shoplist").orderByChild("firebaseid").equalTo(key);


        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fireSnapshot : dataSnapshot.getChildren()) {
                    fireSnapshot.getRef().child("articel").setValue(b);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

}