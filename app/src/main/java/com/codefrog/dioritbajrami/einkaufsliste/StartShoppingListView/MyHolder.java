package com.codefrog.dioritbajrami.einkaufsliste.StartShoppingListView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codefrog.dioritbajrami.einkaufsliste.ItemClickListener;
import com.codefrog.dioritbajrami.einkaufsliste.R;

/**
 * Created by dioritbajrami on 03.03.18.
 */

public class MyHolder {

    TextView nameKaufTxt,ammountKaufTxt, verwaltungKaufTxt;
    CheckBox checkBox;
    RelativeLayout linearLayout;
    ItemClickListener itemclick;

    public MyHolder(View itemView) {

        nameKaufTxt= (TextView) itemView.findViewById(R.id.nameKaufID);
        ammountKaufTxt= (TextView) itemView.findViewById(R.id.ammountKaufID);
        verwaltungKaufTxt= (TextView) itemView.findViewById(R.id.verwaltungKaufID);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxID);
        linearLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLyout);

    }

}
