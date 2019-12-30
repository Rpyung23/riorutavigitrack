package com.vigitrackecuador.riorutavigitrack.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.internal.maps.zzu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vigitrackecuador.riorutavigitrack.Fragments.MapaLineaFragment;
import com.vigitrackecuador.riorutavigitrack.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gps_Linea_BusActivity extends AppCompatActivity
{
    Toolbar toolbar;
    String codelinea;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        codelinea=getIntent().getStringExtra("codeLinea");
        setContentView(R.layout.activity_gps__linea__bus);
        toolbar=findViewById(R.id.toolbar_gps_linea_bus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar_linea();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        MapaLineaFragment oML = new MapaLineaFragment(codelinea);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_mapa_gps_linea,oML).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    private void toolbar_linea()
    {

        switch (codelinea)
        {
            case "L1":
                getSupportActionBar().setTitle("Rastreo Linea 01");
                break;
            case "L2":
                getSupportActionBar().setTitle("Rastreo Linea 02");
                break;
            case "L3":
                getSupportActionBar().setTitle("Rastreo Linea 03");
                break;
            case "L4":
                getSupportActionBar().setTitle("Rastreo Linea 04");
                break;
            case "L5":
                getSupportActionBar().setTitle("Rastreo Linea 05");
                break;
            case "L6":
                getSupportActionBar().setTitle("Rastreo Linea 06");
                break;
            case "L7":
                getSupportActionBar().setTitle("Rastreo Linea 07");
                break;
            case "L8":
                getSupportActionBar().setTitle("Rastreo Linea 08");
                break;
            case "L9":
                getSupportActionBar().setTitle("Rastreo Linea 09");
                break;
            case "LX":
                getSupportActionBar().setTitle("Rastreo Linea 10");
                break;
            case "X1":
                getSupportActionBar().setTitle("Rastreo Linea 11");
                break;
            case "X2":
                getSupportActionBar().setTitle("Rastreo Linea 12");
                break;
            case "X3":
                getSupportActionBar().setTitle("Rastreo Linea 13");
                break;
            case "X4":
                getSupportActionBar().setTitle("Rastreo Linea 14");
                break;
            case "X5":
                getSupportActionBar().setTitle("Rastreo Linea 15");
                break;
            case "X6":
                getSupportActionBar().setTitle("Rastreo Linea 16");
                break;
        }
    }
}
