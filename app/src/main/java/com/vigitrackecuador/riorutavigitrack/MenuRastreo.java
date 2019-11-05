package com.vigitrackecuador.riorutavigitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.vigitrackecuador.riorutavigitrack.Fragments.BusquedaRutaFragment;
import com.vigitrackecuador.riorutavigitrack.Fragments.LineasFragment;

public class MenuRastreo extends AppCompatActivity
{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menurastreo);
        BusquedaRutaFragment oBF = new BusquedaRutaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_maps,oBF).addToBackStack(null).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navi_vista);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.title_toolbar_drawer,R.string.title_toolbar_drawer);
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

    @Override
    public void onBackPressed() {
        // This will be called either automatically for you on 2.0
        // or later, or by the code above on earlier versions of the
        // platform.
        return;
    }
}
