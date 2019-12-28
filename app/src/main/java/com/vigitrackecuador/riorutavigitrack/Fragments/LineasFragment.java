package com.vigitrackecuador.riorutavigitrack.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vigitrackecuador.riorutavigitrack.Adapter.cAdapterLineasFragment;
import com.vigitrackecuador.riorutavigitrack.Interface.RecyclerViewOnItemClickListener;
import com.vigitrackecuador.riorutavigitrack.POO.cBuses;
import com.vigitrackecuador.riorutavigitrack.R;
import com.vigitrackecuador.riorutavigitrack.Views.Gps_Linea_BusActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LineasFragment extends Fragment
{
    Toolbar toolbar;
    ArrayList<cBuses>oB= new ArrayList<>();
    RecyclerView recyclerView;
    public LineasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lineas, container, false);
        llenar_lineas();
        toolbar=view.findViewById(R.id.toolbar_linea_bus);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.lineas_fragments);
        recyclerView=view.findViewById(R.id.recyclerView_Lineas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        cAdapterLineasFragment oAda = new cAdapterLineasFragment(new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getContext(), "Code Linea : "+oB.get(position).getCode_bus(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),Gps_Linea_BusActivity.class);
                intent.putExtra("codeLinea",oB.get(position).getCode_bus());
                startActivity(intent);
            }
        }, oB, R.layout.views_linea, getActivity());
        recyclerView.setAdapter(oAda);
        return view;
    }
    public void llenar_lineas()
    {
        oB.add(new cBuses("L1","Linea 01","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L2","Linea 02","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L3","Linea 03","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L4","Linea 04","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L5","Linea 05","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L6","Linea 06","06:20","21:30","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L7","Linea 07","06:20","19:00","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L8","Linea 08","06:20","19:00","Cada 2 minutos, en horas picos, cada 3 minutos en el resto del día.") );
        oB.add(new cBuses("L9","Linea 09","06:20","19:00","Cada 6 minutos, en horas picos, cada 8 minutos en el resto del día.") );
        oB.add(new cBuses("LX","Linea 10","06:20","19:00","Cada 6 minutos, en horas picos, cada 8 minutos en el resto del día.") );
        oB.add(new cBuses("X1","Linea 11","06:20","19:00","Cada 6 minutos, en horas picos, cada 8 minutos en el resto del día.") );
        oB.add(new cBuses("X2","Linea 12","06:20","19:00","Cada 6 minutos, en horas picos, cada 8 minutos en el resto del día.") );
        oB.add(new cBuses("X3","Linea 13","06:20","21:30","Cada 3 minutos, en todo el día.") );
        oB.add(new cBuses("X4","Linea 14","06:20","21:30","Cada 3 minutos, en todo el día.") );
        oB.add(new cBuses("X5","Linea 15","06:20","19:00","Cada 6 minutos, en todo el día.") );
        oB.add(new cBuses("X6","Linea 16","06:20","21:30","Cada 15 minutos, en todo el día.") );

    }

}
