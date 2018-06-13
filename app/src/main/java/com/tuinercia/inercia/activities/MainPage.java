package com.tuinercia.inercia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tuinercia.inercia.DTO.Disciplines;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.AgendaFragment;
import com.tuinercia.inercia.fragments.PagosFormularioAltaFragment;
import com.tuinercia.inercia.fragments.PagosInicioFragment;
import com.tuinercia.inercia.fragments.ReservacionClasesFragment;
import com.tuinercia.inercia.implementation.ChangeTitleImpl;
import com.tuinercia.inercia.network.InerciaApiClient;
import com.tuinercia.inercia.utils.UtilsSharedPreference;

/**
 * Created by ricar on 22/09/2017.
 */

public class MainPage extends AppCompatActivity implements ReservacionClasesFragment.ReservacionClasesListener
                                                        , NavigationView.OnNavigationItemSelectedListener
                                                        , PagosInicioFragment.PagosInicioListener
                                                        , PagosFormularioAltaFragment.PagosFormularioAltaFragmentListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View navHeader;
    Menu menu;

    static final String INTENT_EXTRA_HEADER = "parlor";
    static final String INTENT_EXTRA_HEADER_DISCIPLINE = "discipline";

    private String[] array_titles;
    ChangeTitleImpl changeTitle;

    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        user = UtilsSharedPreference.getInstance(this).getUser();

        menu = navigationView.getMenu();
        array_titles = getResources().getStringArray(R.array.array_titles);
        changeTitle = ChangeTitleImpl.getInstance(this);


        navHeader = navigationView.getHeaderView(0);
        TextView text_name =  (TextView) navHeader.findViewById(R.id.name);
        TextView text_mail = (TextView) navHeader.findViewById(R.id.mail);

        text_name.setText(user.getName());
        text_mail.setText(user.getEmail());

        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.WHITE));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.title_main,R.string.title_main);

        actionBarDrawerToggle.syncState();

        changeTitle.changeTitleByCurrentFragment(0);

        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.WHITE));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);


        addFragment(R.id.frame_content_main,new ReservacionClasesFragment(), ReservacionClasesFragment.FRAGMENT_TAG);
    }


    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment,
                                @NonNull String fragmentTag,
                                @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void replacePopFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment,
                            @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }
    private void emtyBackStack(){
        while (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onClaseSeleccionada(String fragmentTAG, Disciplines disciplineSelected) {
        /**
         * Al seleccionar el Parlor
         */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.temp_menu,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment    = null;
        boolean mainPageBack = false;
        String previousTAG = getSupportFragmentManager().findFragmentById(R.id.frame_content_main).getTag()
                           , newTag = "";

        switch (item.getItemId()){
            case R.id.nav_home:
                fragment = new ReservacionClasesFragment();
                newTag = ReservacionClasesFragment.FRAGMENT_TAG;
                mainPageBack = true;
                break;
            case R.id.nav_pagos:
                fragment = new PagosInicioFragment();
                newTag = PagosFormularioAltaFragment.FRAGMENT_TAG;
                break;
            case R.id.nav_mis_clases:
                fragment = new AgendaFragment();
                newTag = AgendaFragment.FRAGMENT_TAG;
                break;
        }

        if (!newTag.equals(previousTAG)){
//            if (mainPageBack){
//                emtyBackStack();
//                addFragment(R.id.frame_content_main,fragment,newTag);
//            }else{
                replacePopFragment(R.id.frame_content_main,fragment,newTag,previousTAG);
//            }

        }
        drawerLayout.closeDrawers();

        return false;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public String[] getArray_titles() {
        return array_titles;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsSharedPreference.getInstance(getApplicationContext()).checkLogin();
    }

    @Override
    public void onClickMejorarPlan() {
        String previousTag = getSupportFragmentManager().findFragmentById(R.id.frame_content_main).getTag();
        replaceFragment(R.id.frame_content_main, PagosFormularioAltaFragment.getInstance() , PagosFormularioAltaFragment.FRAGMENT_TAG,previousTag);
    }

    @Override
    public void OnCreatePaymentSuccess() {
        emtyBackStack();
        addFragment(R.id.frame_content_main, new PagosInicioFragment(), PagosInicioFragment.FRAGMENT_TAG);
    }
}
