package com.vigitrackecuador.riorutavigitrack.Fragments;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;
import com.vigitrackecuador.riorutavigitrack.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.android.volley.VolleyLog.TAG;

public class BusquedaRutaFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int RESULT_CANCELED = 1578 ;
    Marker marker_destino;
    private MaterialSearchBar materialSearchBar;
    boolean fabExpanded = false;
    LatLng latLngDestino;
    TextView textViewDestino;
    FloatingActionButton fabSettings;
    GoogleApiClient googleApiClient;
    int AUTOCOMPLETE_REQUEST_CODE = 1579;
    LinearLayout layoutFabParadas;
    LinearLayout layoutFabTrafico;
    LinearLayout layoutFabMyPos;
    MapView mapView;
    GoogleMap googleMapRutaBusqueda;
    FloatingActionButton floatingActionButtonTrafico;
    boolean booleanControlTrafico=false;
    ImageButton imageButtonMiPos;
    AlertDialog.Builder builder;
    public BusquedaRutaFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda_ruta, container, false);
        //materialSearchBar = view.findViewById(R.id.searchBar);
        // Initialize the SDK
        Places.initialize(getContext(),getString(R.string.google_maps_api));
        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(getContext());
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
        /*mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Places.initialize(getContext(), getString(R.string.google_maps_api));
        placesClient = Places.createClient(getContext());
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                getActivity().startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
                    //opening or closing a navigation drawer
                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }
            }
        });
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        //.setLocationRestriction(RectangularBounds.newInstance(new LatLng(-1.714916, -78.802894),new LatLng(-1.571669, -78.549663)))
                        //NE :-1.621647, -78.570078
                        .setCountry("EC")
                        .setQuery(s.toString())
                        .build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {
                        if (task.isSuccessful()) {
                            FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.getAutocompletePredictions();
                                List<String> suggestionsList = new ArrayList<>();
                                for (int i = 0; i < predictionList.size(); i++) {
                                    AutocompletePrediction prediction = predictionList.get(i);
                                    suggestionsList.add(prediction.getFullText(null).toString());
                                }
                                materialSearchBar.updateLastSuggestions(suggestionsList);
                                if (!materialSearchBar.isSuggestionsVisible()) {
                                    materialSearchBar.showSuggestionsList();
                                }
                            }
                        } else {
                            Log.i("mytag", "prediction fetching task unsuccessful");
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (position >= predictionList.size()) {
                    return;
                }
                AutocompletePrediction selectedPrediction = predictionList.get(position);
                String suggestion = materialSearchBar.getLastSuggestions().get(position).toString();
                materialSearchBar.setText(suggestion);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialSearchBar.clearSuggestions();
                    }
                }, 1000);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(materialSearchBar.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                final String placeId = selectedPrediction.getPlaceId();
                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                FetchPlaceRequest fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
                placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                    @Override
                    public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                        Place place = fetchPlaceResponse.getPlace();
                        Log.i("mytag", "Place found: " + place.getName());
                        LatLng latLngOfPlace = place.getLatLng();
                        if (latLngOfPlace != null) {
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace, DEFAULT_ZOOM));
                            LatLng oLDestino = new LatLng(latLngOfPlace.latitude,latLngOfPlace.longitude);
                            marker_destino=googleMapRutaBusqueda.addMarker(new MarkerOptions().title("Destino").position(oLDestino)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino)));
                            marker_destino.showInfoWindow();
                            googleMapRutaBusqueda.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOfPlace,18));
                            Toast.makeText(getContext(), "LatLng : "+latLngOfPlace, Toast.LENGTH_SHORT).show();
                            marker_destino.setDraggable(true);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            apiException.printStackTrace();
                            int statusCode = apiException.getStatusCode();
                            Log.i("mytag", "place not found: " + e.getMessage());
                            Log.i("mytag", "status code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });*/
        materialSearchBar=view.findViewById(R.id.searchBar);
        materialSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
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
        LatLng oLRiobamba = new LatLng(-1.67098,-78.6471176);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(oLRiobamba,13);
        googleMapRutaBusqueda.moveCamera(cameraUpdate);
        MapsInitializer.initialize(getContext());
    }


    @Override
    public boolean onMyLocationButtonClick()
    {
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 1579:
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    obtencionDestino(place);
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    // TODO: Handle the error.
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                break;
        }
    }

    private void obtencionDestino(Place place )
    {
        if(marker_destino!=null){marker_destino.remove();}
        materialSearchBar.setPlaceHolder(place.getAddress());
        latLngDestino=place.getLatLng();
        //Toast.makeText(getContext(), "LanLon : "+place.getAddress(), Toast.LENGTH_SHORT).show();
        marker_destino=googleMapRutaBusqueda.addMarker(new MarkerOptions().position(latLngDestino)
                .title("Mi Destino").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_destino)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(),16);
        googleMapRutaBusqueda.moveCamera(cameraUpdate);
        marker_destino.showInfoWindow();
        marker_destino.setDraggable(true);
        //BuscarRuta();
    }

    private void BuscarRuta()
    {
        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getContext(), "Buscando", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
