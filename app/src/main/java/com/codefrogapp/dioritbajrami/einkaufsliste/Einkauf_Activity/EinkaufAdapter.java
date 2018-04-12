package com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.codefrogapp.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by dioritbajrami on 28.02.18.
 */

public class EinkaufAdapter extends RecyclerView.Adapter<EinkaufViewHolder> {

    Context c;
    ArrayList<Model> models;
    final List<String> keys = new ArrayList<>();// will contain list of keys corresponding to listview item

    public EinkaufAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @Override
    public EinkaufViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_row, parent, false);
        EinkaufViewHolder holder = new EinkaufViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final EinkaufViewHolder holder, final int position) {
        holder.nameTxt.setText(models.get(position).getName());
        holder.ammountTxt.setText(String.valueOf(models.get(position).getAmmount()) + "x ");

        if (models.get(position).getVerwalter().equals("Verwaltung")) {
            holder.verwalterTxt.setText("Verwaltung");
        } else if(models.get(position).getVerwalter().equals("IT")){
            holder.verwalterTxt.setText("IT");
        } else {
            holder.verwalterTxt.setText(models.get(position).getName());
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValue(models.get(position).getName());
            }
        });


    }

    public void deleteValue(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Shoplist").orderByChild("name").equalTo(name);;

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }


    @Override
    public int getItemCount() {
        if(models == null || models.size()>0) {
            return models.size();
        } else {
            return 0;
        }
    }
}
