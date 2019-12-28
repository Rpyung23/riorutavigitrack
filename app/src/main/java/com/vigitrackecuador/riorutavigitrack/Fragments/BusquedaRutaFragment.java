package com.vigitrackecuador.riorutavigitrack.Fragments;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vigitrackecuador.riorutavigitrack.R;
import com.vigitrackecuador.riorutavigitrack.Views.Gps_Linea_BusActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusquedaRutaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener
{

    boolean fabExpanded = false;
    FloatingActionButton fabSettings;
    LinearLayout layoutFabParadas;
    LinearLayout layoutFabTrafico;
    LinearLayout layoutFabMyPos;
    MapView mapView;
    GoogleMap googleMapRutaBusqueda;
    FloatingActionButton floatingActionButtonTrafico;
    boolean booleanControlTrafico=false;
    ImageButton imageButtonMiPos;
    public BusquedaRutaFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda_ruta, container, false);
        fabSettings = (FloatingActionButton) view.findViewById(R.id.fabSubMenu);
        layoutFabParadas = (LinearLayout) view.findViewById(R.id.layoutFabParadas);
        layoutFabTrafico = (LinearLayout) view.findViewById(R.id.layoutFabTrafico);
        layoutFabMyPos = view.findViewById(R.id.layoutFabMyPos);
        imageButtonMiPos=view.findViewById(R.id.fabPos);
        closeSubMenusFab();
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        floatingActionButtonTrafico= view.findViewById(R.id.fabTrafico);
        floatingActionButtonTrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (booleanControlTrafico!=true)
                {
                    googleMapRutaBusqueda.setTrafficEnabled(true);
                    booleanControlTrafico=true;
                }else
                    {
                        googleMapRutaBusqueda.setTrafficEnabled(false);
                        booleanControlTrafico=false;
                    }
            }
        });
        imageButtonMiPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                googleMapRutaBusqueda.setMyLocationEnabled(true);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mapView=view.findViewById(R.id.mapview_rastreoT);
        if (mapView!=null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    private void closeSubMenusFab(){
        layoutFabParadas.setVisibility(View.INVISIBLE);
        layoutFabTrafico.setVisibility(View.INVISIBLE);
        layoutFabMyPos.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.plus);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabTrafico.setVisibility(View.VISIBLE);
        layoutFabParadas.setVisibility(View.VISIBLE);
        layoutFabMyPos.setVisibility(View.VISIBLE);
        fabSettings.setImageResource(R.drawable.plus);
        fabExpanded = true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        googleMapRutaBusqueda=googleMap;
        MapsInitializer.initialize(getContext());
    }


    @Override
    public boolean onMyLocationButtonClick()
    {
        return false;
    }
}
