package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
 * Created by dioritbajrami on 06.04.18.
 */

public class EmpfelungAdapter extends RecyclerView.Adapter<EmpfelungViewHolder>{

    //TODO ACTIVATE THE DELETE BUTTON

    Context c;
    ArrayList<EmpfelungenModel> empfehlungen;

    public EmpfelungAdapter(Context c, ArrayList<EmpfelungenModel> empfehlungen) {
        this.c = c;
        this.empfehlungen = empfehlungen;
    }

    @Override
    public EmpfelungViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empfelung_row, parent, false);
        EmpfelungViewHolder holder = new EmpfelungViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(EmpfelungViewHolder holder, final int position) {
        holder.nameTxt.setText(empfehlungen.get(position).getName());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmpfehlung(empfehlungen.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return empfehlungen.size();
    }

    public void deleteEmpfehlung(final String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query fireQuery = ref.child("Empfehlungen").orderByChild("name").equalTo(name);

        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot fireSnapshot : dataSnapshot.getChildren()) {
                    fireSnapshot.getRef().removeValue();
                    Toast.makeText(c, name + " würde gelöscht!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }


}
