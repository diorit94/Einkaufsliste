package com.codefrog.dioritbajrami.einkaufsliste.Start_shopping_activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Model;
import com.codefrog.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ShoppingAdapter extends RecyclerView.Adapter<StartShoppingViewHolder> {
    Context c;
    ArrayList<Model> models;


    public ShoppingAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @Override
    public StartShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.startshoppingrow, parent, false);
        StartShoppingViewHolder holder = new StartShoppingViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StartShoppingViewHolder holder, final int position) {

        holder.checkBox.setOnCheckedChangeListener(null);

        holder.nameKaufTxt.setText(models.get(position).getName());
        holder.ammountKaufTxt.setText(String.valueOf(models.get(position).getAmmount()) + "x ");

        if (models.get(position).getVerwalter().equals("Verwaltung")) {
            holder.verwaltungKaufTxt.setText("Verwaltung");
        } else if(models.get(position).getVerwalter().equals("IT")){
            holder.verwaltungKaufTxt.setText("IT");
        } else {
            holder.verwaltungKaufTxt.setText(models.get(position).getVerwalter());
        }



        if (models.get(position).getArticel()) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));
            holder.checkBox.setChecked(true);
        } else if(!models.get(position).getArticel()) {
            holder.linearLayout.setBackgroundColor(0x00000000);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    holder.linearLayout.setBackgroundColor(Color.parseColor("#d3d3d3"));
                    boughtArticle(models.get(position).getName(), true);
                }else{
                    holder.linearLayout.setBackgroundColor(0x00000000);
                    boughtArticle(models.get(position).getName(), false);
                }
            }
        });

    }


    public void boughtArticle(String name, final boolean b){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Shoplist").orderByChild("name").equalTo(name);;

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().child("articel").setValue(b);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }


    /*public void checkifAllBought(final boolean aretheybought){
        FirebaseDatabase.getInstance().getReference().child("Shoplist").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            EinkaufModel user = snapshot.getValue(EinkaufModel.class);
                            if(user.getBought()){
                                System.out.println(user.getBought());
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }*/


    @Override
    public int getItemCount() {
        return models.size();
    }

}
