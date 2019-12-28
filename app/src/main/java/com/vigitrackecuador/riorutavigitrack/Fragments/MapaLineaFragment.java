package com.vigitrackecuador.riorutavigitrack.Fragments;


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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vigitrackecuador.riorutavigitrack.R;
import com.vigitrackecuador.riorutavigitrack.Views.Gps_Linea_BusActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaLineaFragment extends Fragment implements OnMapReadyCallback
{
    Marker temp;
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
                for (int i=0;i<response.length();i++)
                {
                    try {
                        jsonObject = new JSONObject();
                        jsonObject = response.getJSONObject(i);
                        //Crear un objeto LatLng para obtener las posiciones
                        LatLng oL = new LatLng(jsonObject.getDouble("UltiLatiMoni"),jsonObject.getDouble("UltiLongMoni"));
                        //Creo un objeto marcador y lo posiciono y le doy los grados
                        marker = new Marker(new zzt()
                        {
                            private  LatLng latLng;
                            private float rotation;
                            @Override
                            public void remove() throws RemoteException {

                            }

                            @Override
                            public String getId() throws RemoteException {
                                return null;
                            }

                            @Override
                            public void setPosition(LatLng AlatLng) throws RemoteException
                            {
                                latLng = AlatLng;
                            }

                            @Override
                            public LatLng getPosition() throws RemoteException {
                                return latLng;
                            }

                            @Override
                            public void setTitle(String s) throws RemoteException {

                            }

                            @Override
                            public String getTitle() throws RemoteException {
                                return null;
                            }

                            @Override
                            public void setSnippet(String s) throws RemoteException {

                            }

                            @Override
                            public String getSnippet() throws RemoteException {
                                return null;
                            }

                            @Override
                            public void setDraggable(boolean b) throws RemoteException {

                            }

                            @Override
                            public boolean isDraggable() throws RemoteException {
                                return false;
                            }

                            @Override
                            public void showInfoWindow() throws RemoteException {

                            }

                            @Override
                            public void hideInfoWindow() throws RemoteException {

                            }

                            @Override
                            public boolean isInfoWindowShown() throws RemoteException {
                                return false;
                            }

                            @Override
                            public void setVisible(boolean b) throws RemoteException {

                            }

                            @Override
                            public boolean isVisible() throws RemoteException {
                                return false;
                            }

                            @Override
                            public boolean zzj(zzt zzt) throws RemoteException {
                                return false;
                            }

                            @Override
                            public int zzj() throws RemoteException {
                                return 0;
                            }

                            @Override
                            public void zzg(IObjectWrapper iObjectWrapper) throws RemoteException {

                            }

                            @Override
                            public void setAnchor(float v, float v1) throws RemoteException {

                            }

                            @Override
                            public void setFlat(boolean b) throws RemoteException {

                            }

                            @Override
                            public boolean isFlat() throws RemoteException {
                                return false;
                            }

                            @Override
                            public void setRotation(float v) throws RemoteException {
                                rotation = v;
                            }

                            @Override
                            public float getRotation() throws RemoteException {
                                return rotation;
                            }

                            @Override
                            public void setInfoWindowAnchor(float v, float v1) throws RemoteException {

                            }

                            @Override
                            public void setAlpha(float v) throws RemoteException {

                            }

                            @Override
                            public float getAlpha() throws RemoteException {
                                return 0;
                            }

                            @Override
                            public void setZIndex(float v) throws RemoteException {

                            }

                            @Override
                            public float getZIndex() throws RemoteException {
                                return 0;
                            }

                            @Override
                            public void zze(IObjectWrapper iObjectWrapper) throws RemoteException {

                            }

                            @Override
                            public IObjectWrapper zzk() throws RemoteException {
                                return null;
                            }

                            @Override
                            public IBinder asBinder() {
                                return null;
                            }
                        });
                        marker.setPosition(oL);
                        int angulo = jsonObject.getInt("UltiRumbMoni");
                        float aux = Float.valueOf(angulo);
                        marker.setRotation(aux);
                        //agrego el objeto marcador a el ArraysList de tipo Market
                        oM.add(marker);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "JSONException e  : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                AgregarMrketsMapa();
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

    private void AgregarMrketsMapa()
    {

        for (int i=0;i<oM.size();i++)
        {
            //Toast.makeText(this, "Lat: "+oM.get(i).getPosition().latitude+" Lon: "+oM.get(i).getPosition().longitude, Toast.LENGTH_SHORT).show();
            try
            {
                markerOptions = new MarkerOptions();
                markerOptions.position(oM.get(i).getPosition());
                if (oM.get(i).getRotation()>0 && oM.get(i).getRotation()<=180)
                {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_igual_menor_180)).rotation(oM.get(i).getRotation());
                    GoogleMap2.addMarker(markerOptions);
                }else
                    {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_mayor_180)).rotation(oM.get(i).getRotation());
                        GoogleMap2.addMarker(markerOptions);
                    }

            }catch (Exception e)
            {
                Toast.makeText(getContext(), "e : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void BorrarMarket()
    {
        Toast.makeText(getContext(), "tam : "+oM.size(), Toast.LENGTH_SHORT).show();
        if (oM.size()>0)
        {
            for (int i=0;i<oM.size();i++)
            {
                oM.remove(i);
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
        GoogleMap2.setTrafficEnabled(true);
        MapsInitializer.initialize(getContext());
    }
}
