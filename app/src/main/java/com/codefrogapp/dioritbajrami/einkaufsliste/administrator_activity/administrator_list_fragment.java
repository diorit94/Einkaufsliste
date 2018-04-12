package com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity;

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

import static com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.Empfelung_Firebase_Client.adapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.administrator_activity.Empfelung_Firebase_Client.empfehlungen;

/**
 * Created by dioritbajrami on 05.04.18.
 */

public class administrator_list_fragment extends Fragment {

    RecyclerView rv;
    Empfelung_Firebase_Client empfelung_firebase_client;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.administrator_fragment_empfelung_list, container, false);

        rv= (RecyclerView) view.findViewById(R.id.empfelung_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        empfelung_firebase_client=new Empfelung_Firebase_Client(getActivity(),rv);
        empfelung_firebase_client.refreshData();

        adapter = new EmpfelungAdapter(getContext(), empfehlungen);
        rv.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        return view;
    }
}
