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

import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFilteredPersonal.adapter;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.FirebaseClientFilteredPersonal.modelsPersonal;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.Tab1Fragment.rv;
import static com.codefrogapp.dioritbajrami.einkaufsliste.Einkaufsliste_Activity_classes.TabbedActivity.fireBaseClientFilteredPersonal;

/**
 * Created by dioritbajrami on 28.02.18.
 */

public class Tab3Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container, false);

        rv= (RecyclerView) view.findViewById(R.id.tab3RecyclerViewID);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        fireBaseClientFilteredPersonal=new FirebaseClientFilteredPersonal(getActivity(),rv);
        fireBaseClientFilteredPersonal.refreshData();

        adapter = new AdapterClass(getContext(), modelsPersonal);
        rv.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        return view;
    }
}