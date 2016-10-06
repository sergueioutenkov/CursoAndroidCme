package com.serguei.cursos.cursoandroidcme;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Creamos el toggle (sandwich icon)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(toggle);
        }

        //No olvidar llamar este metodo ya que sincroniza nuestro drawer con el icono
        toggle.syncState();

        //Navigation View
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        //Seteamos el primer fragment como default
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FirstFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Obtenemos el ID del item
        int id = item.getItemId();

        //Fragment Manager
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Fragment fragment;
        String title = null;

        switch (id) {
            case R.id.drawer_home:
                fragment = new FirstFragment();
                title = "Home";
                break;
            case R.id.drawer_favorites:
                fragment = new SecondFragment();
                title = "Favorites";
                break;
            default:
                fragment = new Fragment();
                break;
        }

        //Reemplazo el container con el fragmento creado en el switch
        transaction.replace(R.id.container, fragment);
        transaction.commit();

        //Seteo el titulo del action bar de acuerdo al titulo creado en el switch
        getSupportActionBar().setTitle(title);

        //Cierro el drawer
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    @Override
    public void onBackPressed() {

        //Si el drawer esta abierto, cerrarlo, sino, ejecutar onBackPressed normalmente
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}