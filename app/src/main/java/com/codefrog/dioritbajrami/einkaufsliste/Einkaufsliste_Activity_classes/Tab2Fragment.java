package com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefrog.dioritbajrami.einkaufsliste.R;

import static com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFiltered.adapter;
import static com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFiltered.modelsVerwaltung;
import static com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Tab1Fragment.rv;
import static com.codefrog.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.TabbedActivity.fireBaseClientFiltered;


public class Tab2Fragment extends Fragment {

    final static String DB_URL="https://einkaufsliste-dc837.firebaseio.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        rv= (RecyclerView) view.findViewById(R.id.tab2RecyclerViewID);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fireBaseClientFiltered=new FirebaseClientFiltered(getActivity(),rv);
        fireBaseClientFiltered.refreshData();

        adapter = new AdapterClass(getContext(), modelsVerwaltung);
        rv.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
    }
}