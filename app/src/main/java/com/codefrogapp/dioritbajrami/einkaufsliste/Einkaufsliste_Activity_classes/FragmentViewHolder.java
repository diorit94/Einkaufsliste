package com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

/**
 * Created by dioritbajrami on 23.02.18.
 */

public class FragmentViewHolder extends RecyclerView.ViewHolder  {

    TextView nameTxt,ammountTxt,verwalterTxt;
    Button deleteBtn;

    public FragmentViewHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.nameID);
        ammountTxt= (TextView) itemView.findViewById(R.id.ammountID);
        verwalterTxt= (TextView) itemView.findViewById(R.id.typeID);
        deleteBtn = (Button)itemView.findViewById(R.id.deleteClickID);
    }

}