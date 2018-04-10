package com.codefrog.dioritbajrami.einkaufsliste.Start_shopping_activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codefrog.dioritbajrami.einkaufsliste.R;

/**
 * Created by dioritbajrami on 27.02.18.
 */

public class StartShoppingViewHolder extends RecyclerView.ViewHolder{

    TextView nameKaufTxt,ammountKaufTxt, verwaltungKaufTxt;
    CheckBox checkBox;
    RelativeLayout linearLayout;
    RecyclerView rv2;
    public StartShoppingViewHolder(View itemView) {
        super(itemView);

        nameKaufTxt= (TextView) itemView.findViewById(R.id.nameKaufID);
        ammountKaufTxt= (TextView) itemView.findViewById(R.id.ammountKaufID);
        verwaltungKaufTxt= (TextView) itemView.findViewById(R.id.verwaltungKaufID);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxID);
        linearLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLyout);


    }

}