package com.example.crudopp20;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private DrawerLayout drawer;
    ListFragment listFragment;
    TwoFragment twoFragment;

    ArrayList<ItemDetails> mainList;

    String[] list1 = {"Task 1","Task 2","Task 3","Task 4","Task 5"};
    ArrayList<String> list = new ArrayList(Arrays.asList(list1));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*exp lv*/
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setVisibility(View.GONE);
        // preparing list datlvExpa
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        /*exp lv*/



        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        mainList = new ArrayList<>();
        for (int i=0;i<list1.length;i++){
            try {
                mainList.add(new ItemDetails(list1[i],"These are the information of "+list1[i],"images/"+getAssets().list("images")[i%9]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (listFragment==null) listFragment = new ListFragment();
        if (twoFragment==null) twoFragment = new TwoFragment();

        if (savedInstanceState==null){
            Log.i("789456 MainActivity", "onCreate: "+savedInstanceState);
            navigationView.setCheckedItem(R.id.nav_list);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,listFragment).commit();
        }

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Item 1");
        listDataHeader.add("Item 2");
        listDataHeader.add("Item 3");

        // Adding child data
        List<String> item1 = new ArrayList<String>();
        item1.add("Sub Item 1.1");
        item1.add("Sub Item 1.2");


        List<String> item2 = new ArrayList<String>();
        item2.add("Sub Item 2.1");
        item2.add("Sub Item 2.2");

        List<String> item3 = new ArrayList<String>();
        item3.add("Sub Item 3.1");
        item3.add("Sub Item 3.2");

        listDataChild.put(listDataHeader.get(0), item1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), item2);
        listDataChild.put(listDataHeader.get(2), item3);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mainlist",mainList);
        outState.putStringArrayList("list",list);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainList = savedInstanceState.getParcelableArrayList("mainlist");
        list = savedInstanceState.getStringArrayList("list");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_list: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,listFragment).commit();  break;
            case R.id.nav_two: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,twoFragment).commit();    break;
            case R.id.nav_add: addRecord();
                Toast.makeText(this, "Add Function", Toast.LENGTH_SHORT).show(); break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addRecord(){
        final Dialog builder = new Dialog(MainActivity.this);
        builder.setContentView(R.layout.alert_label_editor2);


        final Spinner spin = (Spinner) builder.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, ""+list.get(position) +" Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list1
        final ArrayAdapter aa = new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,list1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        builder.setTitle("Information");

        Button okB,cancelB;
        okB = builder.findViewById(R.id.okB);
        cancelB = builder.findViewById(R.id.cancelB);

        okB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*list.add(aa.getItem(spin.getSelectedItemPosition()).toString());
                listFragment.updateRecyclerView();
//                Toast.makeText(MainActivity.this, ""+list.size()+":"+adapter.getItemCount(), Toast.LENGTH_SHORT).show();*/
                try {
                    mainList.add(new ItemDetails(""+aa.getItem(spin.getSelectedItemPosition()).toString(),"This is the information of "+aa.getItem(spin.getSelectedItemPosition()).toString(),"images/"+getAssets().list("images")[mainList.size()%9]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listFragment.updateRecyclerView();
                builder.dismiss();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.setCancelable(false);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        builder.getWindow().setLayout((6 * width)/7, RecyclerView.LayoutParams.WRAP_CONTENT);
        builder.show();
    }


}
