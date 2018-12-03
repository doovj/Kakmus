package com.doovj.kakmus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {
    MaterialSearchBar searchBar;
    RecyclerView rvkamus;
    KamusAdapter kamusAdapter;
    KamusHelper kamusHelper;
    boolean isEnglish = true;
    ArrayList<KamusModel> kamusModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        rvkamus = (RecyclerView)findViewById(R.id.recyclerview);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        searchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);

        kamusAdapter = new KamusAdapter(this);
        rvkamus.setLayoutManager(new LinearLayoutManager(this));
        rvkamus.setAdapter(kamusAdapter);

        kamusHelper = new KamusHelper(this);

        getSupportActionBar().setSubtitle("English - Indonesia");

        kamusHelper.open();

        kamusModels = kamusHelper.getAllData(isEnglish);

        kamusHelper.close();

        kamusAdapter.addItem(kamusModels);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english_indo) {
            isEnglish = true;
            kamusHelper.open();

            getSupportActionBar().setSubtitle("English - Indonesia");
            kamusModels = kamusHelper.getAllData(isEnglish);

            kamusHelper.close();

            kamusAdapter.addItem(kamusModels);
        } else if (id == R.id.nav_indo_english) {
            isEnglish = false;
            kamusHelper.open();

            getSupportActionBar().setSubtitle("Indonesia - English");
            kamusModels = kamusHelper.getAllData(isEnglish);

            kamusHelper.close();

            kamusAdapter.addItem(kamusModels);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        String kata = String.valueOf(text);
        kamusHelper.open();
        kamusModels = kamusHelper.getDataByKata(kata, isEnglish);
        kamusHelper.close();
        kamusAdapter.addItem(kamusModels);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                kamusHelper.open();

                kamusModels = kamusHelper.getAllData(isEnglish);

                kamusHelper.close();

                kamusAdapter.addItem(kamusModels);
                break;
        }
    }
}
