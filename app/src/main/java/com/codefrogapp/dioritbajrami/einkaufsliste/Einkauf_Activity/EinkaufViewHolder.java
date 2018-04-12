package com.codefrogapp.dioritbajrami.einkaufsliste.Einkauf_Activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

/**
 * Created by dioritbajrami on 28.02.18.
 */


public class EinkaufViewHolder extends RecyclerView.ViewHolder {

    TextView nameTxt, ammountTxt, verwalterTxt;
    Button deleteBtn;

    public EinkaufViewHolder(View itemView) {
        super(itemView);

        nameTxt = (TextView) itemView.findViewById(R.id.nameID);
        ammountTxt = (TextView) itemView.findViewById(R.id.ammountID);
        verwalterTxt= (TextView) itemView.findViewById(R.id.typeID);
        deleteBtn = (Button) itemView.findViewById(R.id.deleteClickID);

    }

}

