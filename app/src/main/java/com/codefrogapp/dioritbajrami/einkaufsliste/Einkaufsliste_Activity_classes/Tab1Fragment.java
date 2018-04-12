package com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefrogapp.dioritbajrami.einkaufsliste.R;

import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClient.adapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClient.models;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.TabbedActivity.fireBaseClient;


public class Tab1Fragment extends Fragment {

    final static String DB_URL="https://einkaufsliste-dc837.firebaseio.com/";
    static RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        rv= (RecyclerView) view.findViewById(R.id.tab1RecyclerViewID);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fireBaseClient=new FirebaseClient(getActivity(),rv);
        fireBaseClient.refreshData();

        adapter = new AdapterClass(getContext(), models);
        rv.setAdapter(adapter);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        return view;
    }
}
