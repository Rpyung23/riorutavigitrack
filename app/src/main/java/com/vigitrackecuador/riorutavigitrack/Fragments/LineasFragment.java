package com.vigitrackecuador.riorutavigitrack.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vigitrackecuador.riorutavigitrack.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LineasFragment extends Fragment {


    public LineasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lineas, container, false);
        return view;
    }

}
