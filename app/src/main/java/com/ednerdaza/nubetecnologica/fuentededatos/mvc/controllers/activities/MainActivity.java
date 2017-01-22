package com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ednerdaza.nubetecnologica.fuentededatos.R;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.GlobalConfig;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.Helpers;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.Interfaces.DelegateItemAdapter;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.Interfaces.ItemModelInterface;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.adapters.AdapterItems;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.EntityModel;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.DataList;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.DataMenu;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.EntityResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DelegateItemAdapter {

    RecyclerView recyclerView ;
    LinearLayoutManager linearLayoutManager;
    AdapterItems adapterItems;
    Context context;
    NavigationView navigationView;
    private ArrayList<DataMenu> menuItems = new ArrayList<DataMenu>();
    private ArrayList<DataList> arrayListDataList = new ArrayList<DataList>();
    private ImageView imageView;
    private RelativeLayout relativeLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Helpers.setActivity(this);
        Helpers.setContexto(context);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.main_imageview);
        textView = (TextView) findViewById(R.id.main_online);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_relative);

        recyclerView = (RecyclerView)findViewById(R.id.rv_root);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //CARGAR JSON VIA WEB O VIA ASSETS
        useJson();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            useJson();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        for (DataMenu dataMenu : menuItems)
        {
            if(dataMenu.getTitle().equals(item.getTitle().toString().trim())){
                int idItem = dataMenu.getId();
                Log.v(GlobalConfig.APP_TAG, "onNavigationItemSelected >>> " + idItem);
                drawList(dataMenu.getDataList());
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClicked(DataList dataList) {
        Log.v(GlobalConfig.APP_TAG, "HICE CLICK EN --> " + dataList);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("dataList", (Serializable) dataList);
        startActivity(intent);
    }







    private void useJson() {
        if(Helpers.testConectionInternet(context)){
            recyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_cloud_queue_green_700_48dp);
            textView.setText(getResources().getString(R.string.online));
            //textView.setTextColor(ColorStateList.valueOf(R.color.verde700));
            textView.setTextColor(getResources().getColor(R.color.verde700));
            syncItems();
        }else{
            recyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_cloud_off_red_700_48dp);
            textView.setText(getResources().getString(R.string.offline));
            textView.setTextColor(getResources().getColor(R.color.rojo700));
            syncItemsAssets();
        }
    }

    /**
     * METODO QUE LEE UN SERVICIO Y CARGA LAS OBRAS QUE VIENEN DESDE ESTE
     */
    private void syncItems() {

        // MUESTRO UN CARGANDO
        Helpers.progressDialogLoadingShow(getResources().getString(R.string.wait_loading));

        EntityModel.getItems(context, new ItemModelInterface<EntityResponse>() {

            @Override
            public void completeSuccess(EntityResponse entityResponse) {
                Log.v(GlobalConfig.APP_TAG, "EXITO SINCRONIZACION >>> " + entityResponse);
                //Toast.makeText(getApplicationContext(), entities+" ", Toast.LENGTH_LONG).show();
                menuItems = entityResponse.getDataMenu();
                drawItemsMenu(menuItems);

                // CIERRO EL CARGANDO
                Helpers.progressDialogClose();

            }

            @Override
            public void completeFail(String message) {
                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                Log.v(GlobalConfig.APP_TAG, "FALLO SINCRONIZACION >>> " + message);
                // CIERRO EL CARGANDO
                Helpers.progressDialogClose();
                // ABRIMOS UN DIALOG CON EL MENSAJE QUE VIENE DEL SERVICIO
                Helpers.customDialogMessage(message).show();

            }
        });

    }

    /**
     * METODO QUE LEE UN FILE Y CARGA EL CONTENIDO DE ASSETS
     */
    private void syncItemsAssets() {
        Log.v(GlobalConfig.APP_TAG, "syncItemsAssets()"+
                "\nMETODO QUE LEE UN FILE Y CARGA EL CONTENIDO DE ASSETS\n"+this);
        // MUESTRO UN CARGANDO
        Helpers.progressDialogLoadingShow(getResources().getString(R.string.wait_loading));

        EntityModel.getItemsAssets(context, new ItemModelInterface<EntityResponse>() {
            @Override
            public void completeSuccess(EntityResponse entity) {
                Log.v(GlobalConfig.APP_TAG, "EXITO SINCRONIZACION >>> " + entity);
                //Toast.makeText(getApplicationContext(), entities+" ", Toast.LENGTH_LONG).show();
                menuItems = entity.getDataMenu();
                drawItemsMenu(menuItems);

                // CIERRO EL CARGANDO
                Helpers.progressDialogClose();
            }

            @Override
            public void completeFail(String message) {
                Log.v(GlobalConfig.APP_TAG, "FALLO SINCRONIZACION >>> " + message);
                // CIERRO EL CARGANDO
                Helpers.progressDialogClose();
                // ABRIMOS UN DIALOG CON EL MENSAJE QUE VIENE DEL SERVICIO
                Helpers.customDialogMessage(message).show();
            }
        });
    }


    /**
     * METODO QUE DIBUJA VALORES PARA EL RECYCLEVIEW CUANDO ESTE LEE DEL SERVICIO
     * @param items
     */
    private void drawItemsMenu(ArrayList<DataMenu> items) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();
        m.clear();
        for (DataMenu item : items)
        {
            m.add(item.getTitle()).setIcon(R.drawable.ic_chevron_right_black_48dp);
        }
        MenuItem mi = m.getItem(m.size()-1);
        mi.setTitle(mi.getTitle());
    }

    /**
     * METODO QUE DIBUJA VALORES PARA EL RECYCLEVIEW CUANDO ESTE LEE DEL SERVICIO
     * @param dataLists
     */
    private void drawList(ArrayList<DataList> dataLists) {
        //arrayListDataList.clear();
        arrayListDataList = dataLists;
        adapterItems = new AdapterItems(MainActivity.this, arrayListDataList);
        adapterItems.setDelegate(this);
        recyclerView.setAdapter(adapterItems);
        relativeLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }



}
