package com.codefrogapp.dioritbajrami.einkaufsliste;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import at.markushi.ui.CircleButton;

/**
 * Created by dioritbajrami on 23.02.18.
 */

public class Einkaufsliste_ViewHolder {

    public static class EinkaufslisteViewViewHolder extends RecyclerView.ViewHolder{
        ItemClickListener itemclick;
        public TextView Einkaufsliste_Titel;
        CircleButton circleButton;

        public EinkaufslisteViewViewHolder(View v){
            super(v);
            Einkaufsliste_Titel = (TextView)itemView.findViewById(R.id.button_text_id);
            circleButton = (CircleButton)itemView.findViewById(R.id.circleButtonId);
        }

    }

}
