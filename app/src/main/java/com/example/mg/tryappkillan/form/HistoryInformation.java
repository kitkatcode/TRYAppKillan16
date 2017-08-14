package com.example.mg.tryappkillan.form;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.interf.ITimerActivity;
import com.example.mg.tryappkillan.interf.clickMenu;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;

public class HistoryInformation extends AppCompatActivity implements  clickMenu {

    private Sessions session;
    private MyMenu myMenu;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private SharedPreferences shp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_information);

/*
* Session Section
* */
        session = new Sessions(this);
        if(!session.logginIN()) {
            Log.e("SESSION", String.valueOf(session.logginIN()));
            finish();
            session.clearSession();
            startActivity(new Intent(this,LoginForm.class));
        }

        TextView vdate = (TextView) findViewById(R.id.dateView);
        TextView vdistance = (TextView) findViewById(R.id.distanceView);
        TextView vcalories = (TextView) findViewById(R.id.caloriesView);
        TextView vstopper = (TextView) findViewById(R.id.stopperView);
        TextView vavgspeed = (TextView) findViewById(R.id.avarageSpeedView);
        TextView vmaxspeed = (TextView) findViewById(R.id.maxSpeedView);

        shp = getSharedPreferences("SessionPreference", 0);
        DataBase db = new DataBase(this);

        Bundle extras = getIntent().getExtras();
        /*Cursor row = db.query("SELECT datetime,distance,stoppertime,maxspeed  " +
                "FROM RUNS " +
               *//* "WHERE idRUN="+(extras.getInt("SESSION_ID")+1) + " AND idUSER=" + shp.getInt("idUSER",0));*//*
                "WHERE idUSER=" + shp.getInt("idUSER",0) + " AND idRUN="+(extras.getInt("SESSION_ID")+1));*/
      Cursor  row = db.query("SELECT datetime,distance,stoppertime,maxspeed " +
              "FROM RUNS " +
              "WHERE idUSER="+shp.getInt("idUSER",0));
        if (row.getCount() > 0) {
            row.move(row.getCount() - extras.getInt("SESSION_ID"));



            vdate.setText(row.getString(row.getColumnIndex("datetime")));
            vdistance.setText(row.getString((row.getColumnIndex("distance"))));

           vcalories.setText(String.valueOf(Math.ceil(0.95 * 70 * row.getDouble(row.getColumnIndex("distance")) / 1000 * 1.04 * 100) / 100));

            int stoppervalue =  row.getInt(row.getColumnIndex("stoppertime"));
            String stoppertime = String.valueOf( (stoppervalue/3600000)%24)+ ":"+ String.valueOf((stoppervalue /60000)%60)+ ":" + String.valueOf((stoppervalue/1000)%60) + ":" + String.valueOf((stoppervalue%1000));
            vstopper.setText(stoppertime);

           vavgspeed.setText(row.getDouble(row.getColumnIndex("stoppertime")) > 0 ? String.valueOf( Math.round(row.getDouble(row.getColumnIndex("distance"))*3600 / row.getInt(row.getColumnIndex("stoppertime")))): String.valueOf(0));
           vmaxspeed.setText(String.valueOf(Math.round(row.getDouble(row.getColumnIndex("maxspeed")))));
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navmenu);

        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // method invoked only when the actionBar is not null.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        myMenu = new MyMenu();
        myMenu.Menu(this);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       /* item.setChecked(true);*/

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean clickHistory(MenuItem item) {
        myMenu.clickHistory();
        return false;
    }

    @Override
    public boolean clickStatistics(MenuItem item) {
        myMenu.clickStatistics();
        return false;
    }

    @Override
    public boolean clickActivity(MenuItem item) {
        myMenu.clickActivity();
        return false;
    }

    @Override
    public boolean clickActivity2(MenuItem item) {
        myMenu.clickActivity2();
        return false;
    }
    @Override
    public boolean clickLogout(MenuItem item) {
        myMenu.clickLogout(session);
        return false;
    }
}
