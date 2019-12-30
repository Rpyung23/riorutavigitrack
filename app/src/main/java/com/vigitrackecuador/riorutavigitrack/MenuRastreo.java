package com.vigitrackecuador.riorutavigitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.vigitrackecuador.riorutavigitrack.Fragments.BusquedaRutaFragment;
import com.vigitrackecuador.riorutavigitrack.Fragments.LineasFragment;

public class MenuRastreo extends AppCompatActivity
{

    Button btn_menu_mostrar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private int codigo_permiso_fine_location=10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menurastreo);
        VerificarPermisos();
        btn_menu_mostrar=findViewById(R.id.btn_mostrar_menu_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navi_vista);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.title_toolbar_drawer,R.string.title_toolbar_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.id_LineasRutas:
                        LineasFragment oL = new LineasFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_maps,oL)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.id_RutasBusqueda:
                        BusquedaRutaFragment oB = new BusquedaRutaFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_maps,oB)
                                .addToBackStack(null).commit();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        btn_menu_mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void VerificarPermisos()
    {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Otorgar Permisos para su funcionamiento", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},codigo_permiso_fine_location);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            BusquedaRutaFragment oBF = new BusquedaRutaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_maps,oBF).addToBackStack(null).commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 10:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BusquedaRutaFragment oBF = new BusquedaRutaFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_maps,oBF).addToBackStack(null).commit();
                } else {
                    Toast.makeText(this, "Permisos negados", Toast.LENGTH_SHORT).show();
                    VerificarPermisos();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(),"Codigo de permiso no identificado",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (  Integer.valueOf(android.os.Build.VERSION.SDK) < 7 //Instead use android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

}
