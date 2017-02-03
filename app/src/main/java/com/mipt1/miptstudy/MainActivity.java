package com.mipt1.miptstudy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {

    public long curr_selection = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new MainActivityFragment());
                ft.commit();
            }
        });
        // Первый запуск, не поворот
        if(savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment, new MainActivityFragment()).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Drawer dr = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.mipt_logo)
                .addDrawerItems(new PrimaryDrawerItem()
                        .withIdentifier(1)
                        .withName("Поиск задач в Корявове")
                        .withIcon(GoogleMaterial.Icon.gmd_search),
                        new ExpandableDrawerItem()
                                .withName("Материалы с mipt1.ru")
                                .withIcon(GoogleMaterial.Icon.gmd_description).withSelectable(false).withSubItems(
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(3)
                                            .withName("1 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_1),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(4)
                                            .withName("2 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_2),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(5)
                                            .withName("3 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_3),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(6)
                                            .withName("4 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_4),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(7)
                                            .withName("5 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_5),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(8)
                                            .withName("6 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_6),
                                    new SecondaryDrawerItem()
                                            .withLevel(2)
                                            .withIdentifier(9)
                                            .withName("7 семестр")
                                            .withIcon(GoogleMaterial.Icon.gmd_filter_7)))
                .build();

        dr.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(position == curr_selection) {
                    Log.d("my_log", "Clicked the same fragment");
                    return true;
                }
                curr_selection = drawerItem.getIdentifier();
                switch ((int) curr_selection) {
                    case 1:
                        ft.replace(R.id.fragment, new MainActivityFragment());
                        break;
                    case 2:
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
