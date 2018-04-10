package com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codefrog.dioritbajrami.einkaufsliste.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;
import static com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.TabbedActivity.coordinatorLayout;

/**
 * Created by dioritbajrami on 23.02.18.
 */
public class AdapterClass extends RecyclerView.Adapter<FragmentViewHolder> {

    Context c;
    ArrayList<Model> models;

    public AdapterClass(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @Override
    public FragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_row, parent, false);
        FragmentViewHolder holder = new FragmentViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FragmentViewHolder holder, final int position) {
        holder.nameTxt.setText(models.get(position).getName());
        holder.ammountTxt.setText(String.valueOf(models.get(position).getAmmount()) + "x ");

        if (models.get(position).getVerwalter().equals("Verwaltung")) {
            holder.verwalterTxt.setText("Verwaltung");
        } else if (models.get(position).getVerwalter().equals("IT")) {
            holder.verwalterTxt.setText("IT");
        } else {
            holder.verwalterTxt.setText(models.get(position).getVerwalter());
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteValue(models.get(position).getFirebaseid());
                saveTemporary(models.get(position).getName(), models.get(position).getAmmount(), models.get(position).getVerwalter(), models.get(position).getArticel(), models.get(position).getFirebaseid());
                showSnackBar(models.get(position).getName(), models.get(position).getFirebaseid());
            }
        });
    }

    public void deleteValue(String key) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query fireQuery = ref.child("Shoplist").orderByChild("firebaseid").equalTo(key);


        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot fireSnapshot : dataSnapshot.getChildren()) {
                    fireSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    Model m = new Model();
    public void saveTemporary(final String name, final int ammount, final String verwalter, boolean articel, final String firebaseid) {
        m.setName(name);
        m.setAmmount(ammount);
        m.setVerwalter(verwalter);
        m.setArticel(articel);
        m.setFirebaseid(firebaseid);
    }

    public void showSnackBar(String name, final String id) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        final Snackbar snackbar = Snackbar
                .make(coordinatorLayout, name + " Wurde gelöscht", Snackbar.LENGTH_LONG)
                .setAction("Rückgängig machen", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ref.child("Shoplist").child(id).setValue(m);
                    }
                });
        snackbar.show();
    }


    @Override
    public int getItemCount() {
        return models.size();
    }
}