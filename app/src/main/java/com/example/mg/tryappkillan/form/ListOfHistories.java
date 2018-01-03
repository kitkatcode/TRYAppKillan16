package com.example.mg.tryappkillan.form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.interf.clickMenu;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;

import java.util.ArrayList;

/**
 * Created by mg on 16/01/17.
 */

public class ListOfHistories extends AppCompatActivity implements clickMenu {

    private Sessions session;
    private MyMenu myMenu;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

/*
* Session Section
* */
        checkSession();

/*
* ListView Section
* */
        ListView listV = (ListView) findViewById(R.id.listView);
//        It sets view of the elements of list and runs the getView method
        listV.setAdapter(new VizAdapter(this));


        //TODO: Uncomment this after record repair.
      listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(),HistoryInformation.class);
                intent.putExtra("SESSION_ID",position);
                startActivity(intent);

                //startActivity(new Intent(view.getContext(),HistoryInformation.class));

            }
        });
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

    private void checkSession() {
        session = new Sessions(this);
        if(!session.logginIN()) {
            Log.e("SESSION", String.valueOf(session.logginIN()));
            finish();
            session.clearSession();
            startActivity(new Intent(this,LoginForm.class));
        }
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


    private class SingleRow {
       private String datetime;
       private String distance;
       private String stopperTime;
       private String calories;


        SingleRow(String datetime,String distance, String stopperTime,String calories) {
            this.datetime = datetime;
            this.distance = distance;
            this.stopperTime = stopperTime;
            this.calories = calories;

        }

    }

   private class VizAdapter extends BaseAdapter {
        private ArrayList<SingleRow> list;
        private Cursor row;
        private DataBase db;
        private Context context;
        private SharedPreferences shp;

        VizAdapter(Context c) {
            context = c;
           shp = getSharedPreferences("SessionPreference",0);

            list = new ArrayList<>();
            db = new DataBase(c);
            row = db.query("SELECT datetime,distance,stoppertime " +
                    "FROM RUNS " +
                    "WHERE idUSER="+shp.getInt("idUSER",0));

            if (row.getCount() > 0) {
                row.moveToLast();
                for (int i = 0; i < row.getCount(); i++) {
                    String datetime = row.getString(row.getColumnIndex("datetime"));
                    String distance = row.getString(row.getColumnIndex("distance"));
                    int stoppervalue =  row.getInt(row.getColumnIndex("stoppertime"));
                    String stoppertime = String.valueOf( (stoppervalue/3600000)%24)+ ":"+ String.valueOf((stoppervalue /60000)%60)+ ":" + String.valueOf((stoppervalue/1000)%60);
                    String calories =  String.valueOf(Math.ceil(6.916 * row.getDouble(row.getColumnIndex("distance")))/100);
                    list.add(new SingleRow(datetime,distance,stoppertime,calories));
                    row.moveToPrevious();
                }
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowList = inflater.inflate(R.layout.history_single_row, parent, false);

                TextView datetime = (TextView) rowList.findViewById(R.id.dataTimeView);
                TextView vtime = (TextView) rowList.findViewById(R.id.timeView);
                TextView vtrack = (TextView) rowList.findViewById(R.id.trackView);
                TextView vcalories = (TextView) rowList.findViewById(R.id.caloriesView);

                //   Log.e("Rows",String.valueOf(position));
                SingleRow obj = list.get(position);
                //  Log.e("BAZA",temp.datetime);
                datetime.setText(obj.datetime);
                vtrack.setText(obj.distance);
                vtime.setText(obj.stopperTime);
                vcalories.setText(obj.calories);

            return rowList;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       /* item.setChecked(true);*/
        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
}