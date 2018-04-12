package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

/**
 * Created by dioritbajrami on 06.04.18.
 */

public class EmpfelungViewHolder extends RecyclerView.ViewHolder {

    TextView nameTxt;
    Button deleteBtn;

    public EmpfelungViewHolder(View itemView) {
        super(itemView);
        nameTxt= (TextView) itemView.findViewById(R.id.empeflung_text_id);
        deleteBtn = (Button)itemView.findViewById(R.id.deleteEmpfelungClickID);
    }

}
