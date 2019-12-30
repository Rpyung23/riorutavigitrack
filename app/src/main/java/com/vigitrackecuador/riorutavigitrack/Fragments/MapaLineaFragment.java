package com.vigitrackecuador.riorutavigitrack.Fragments;


import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.kml.KmlLayer;
import com.vigitrackecuador.riorutavigitrack.R;
import com.vigitrackecuador.riorutavigitrack.Views.Gps_Linea_BusActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaLineaFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener
{
    KmlLayer kmlLayer ;
    MapView mapView;
    private String codelinea;
    private RequestQueue requestQueue;
    private JsonArrayRequest jsonArrayRequest;
    private JSONObject jsonObject;
    private Marker marker;
    ArrayList<Marker>oM= new ArrayList<>();
    private MarkerOptions markerOptions;
    private GoogleMap GoogleMap2;
    private Handler handler = new Handler();

    public MapaLineaFragment(String aux) {
        // Required empty public constructor
        codelinea=aux;
    }
    public String getCodelinea(){return  codelinea;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_mapa_linea, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView=view.findViewById(R.id.maview_gps_linea_bus);
        if (mapView!=null)
        {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
            PosicionesBus();
            handler.postDelayed(mPosiciones, 9000);
        }
    }

    private void PosicionesBus()
    {

        BorrarMarket();
        String url ="https://www.vigitrackecuador.com/WebService_Rio_Rutas/rastreo_gps_bus_linea.php?rutaLetra=";
        String url_final =url+codelinea;
        jsonArrayRequest = new JsonArrayRequest(url_final, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                //BorrarMarket();
                GoogleMap2.clear();
                if (kmlLayer!=null){kmlLayer.removeLayerFromMap();}
                kml();
                for (int i=0;i<response.length();i++)
                {
                    try {
                        jsonObject = new JSONObject();
                        jsonObject = response.getJSONObject(i);
                        //Crear un objeto LatLng para obtener las posiciones
                        LatLng oL = new LatLng(jsonObject.getDouble("UltiLatiMoni"),jsonObject.getDouble("UltiLongMoni"));
                        //Creo un objeto marcador y lo posiciono y le doy los grados
                        int angulo = jsonObject.getInt("UltiRumbMoni");
                        float aux = Float.valueOf(angulo);
                        if (jsonObject.getInt("UltiRumbMoni")<=180)
                        {
                            oM.add(GoogleMap2.addMarker(new MarkerOptions().position(oL).rotation(aux)
                                    .title("Bus N° "+jsonObject.getString("CodiVehiMoni"))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_igual_menor_180))));
                            //Marker marker = new Marker().showInfoWindow();
                        }else
                        {
                            oM.add(GoogleMap2.addMarker(new MarkerOptions().position(oL).rotation(aux)
                                    .title("Bus N° "+jsonObject.getString("CodiVehiMoni"))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_mayor_180))));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "JSONException e  : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "ERROR RESPONSE "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void kml()
    {
        switch (getCodelinea())
        {
            case "L1":

                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea1,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L2":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea2,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L3":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea3,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L4":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea4,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L5":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea5,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L6":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea6,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L7":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea7,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L8":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea8,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "L9":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea9,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "LX":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea10,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X1":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea11,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X2":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea12,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X3":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea13,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X4":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea14,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X5":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea15,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "X6":
                try {
                    kmlLayer = new KmlLayer(GoogleMap2,R.raw.capalinea16,getContext());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        try {
            kmlLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void BorrarMarket()
    {
        Toast.makeText(getContext(), "tam : "+oM.size(), Toast.LENGTH_SHORT).show();
        if (oM.size()>0)
        {
            for (int i=0;i<oM.size();i++)
            {
                oM.remove(oM.get(i));
            }
            oM.clear();
        }
    }
    private Runnable mPosiciones = new Runnable() {
        @Override
        public void run() {
            PosicionesBus();
            handler.postDelayed(this, 9000);
        }
    };

    public void stopRepeating(View view)
    {
        handler.removeCallbacks(mPosiciones);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopRepeating(getView());
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        GoogleMap2 =googleMap;
        //GoogleMap2.setTrafficEnabled(true);
        GoogleMap2.setMyLocationEnabled(true);
        GoogleMap2.setOnMyLocationButtonClickListener(this);
        GoogleMap2.setOnMyLocationClickListener(this);
        LatLng oLRiobamba = new LatLng(-1.67098, -78.6471176);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(oLRiobamba,12);
        GoogleMap2.moveCamera(cameraUpdate);
        MapsInitializer.initialize(getContext());
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
