package com.example.mg.tryappkillan.form;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.interf.clickMenu;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ListOfStatistics extends AppCompatActivity implements clickMenu {

    private Sessions session;
    public String selCond ;
    private String selCond2;
    Spinner spinner;
    private  NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private MyMenu myMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_statistics);

        selCond = "";
        selCond2 = "";
        checkSession();

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.kind_of_statistic));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        final ListView listView = (ListView) findViewById(R.id.listView);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //     Toast.makeText(this,"W",.LENGTH_SHORT);
                TextView textView = (TextView) findViewById(R.id.labelView);
                TextView textView2 = (TextView) findViewById(R.id.labelView2);
                Calendar cal = Calendar.getInstance();

                if (i == 0) {
                    Log.e("Option", "ALL");
                    selCond = "";
                    selCond2 = "";
                    textView.setText("");
                    textView2.setText("");
                } else if (i == 1) {
                    selCond = "AND strftime('%Y',datetime) = strftime('%Y','now'-1)";
                    selCond2 = "AND strftime('%Y',datetime) = strftime('%Y','now')";
                    Log.e("Option", "YEAR");
                    int year = cal.get(Calendar.YEAR);
                    textView.setText(String.valueOf(year - 1));
                    textView2.setText(String.valueOf(year));
                    Log.e("Option", "WEEK");
                } else if (i == 2) {
                    selCond = "AND strftime('%Y',datetime) = strftime('%Y','now'-1) AND strftime('%m',datetime) = strftime('%m','now'-1)";
                    selCond2 = "AND strftime('%Y',datetime) = strftime('%Y','now') AND strftime('%m',datetime) = strftime('%m','now')";
                    Log.e("Option", "MONTH");


                    int month = cal.get(Calendar.MONTH);

//                    TODO: REMOVE TEST RANGE
                    Log.e("JANUARY", String.valueOf(Calendar.JANUARY));
                    Log.e("NOVEMBER", String.valueOf(Calendar.NOVEMBER));

                    String textmonth;
                    String textmonth2;
                    switch (month) {
                        case 0:
                            textmonth = "Grudzień";
                            textmonth2 = "Styczeń";
                            break;
                        case 1:
                            textmonth = "Styczeń";
                            textmonth2 = "Luty";
                            break;
                        case 2:
                            textmonth = "Luty";
                            textmonth2 = "Marzec";
                            break;
                        case 3:
                            textmonth = "Marzec";
                            textmonth2 = "Kwieczeń";
                            break;
                        case 4:
                            textmonth = "Kwieczeń";
                            textmonth2 = "Maj";
                            break;
                        case 5:
                            textmonth = "Maj";
                            textmonth2 = "Czerwiec";
                            break;
                        case 6:
                            textmonth = "Czerwiec";
                            textmonth2 = "Lipiec";
                            break;
                        case 7:
                            textmonth = "Lipiec";
                            textmonth2 = "Sierpień";
                            break;
                        case 8:
                            textmonth = "Sierpień";
                            textmonth2 = "Wrzesień";
                            break;
                        case 9:
                            textmonth = "Wrzesień";
                            textmonth2 = "Październik";
                            break;
                        case 10:
                            textmonth = "Październik";
                            textmonth2 = "Listopad";
                            break;
                        case 11:
                            textmonth = "Listopad";
                            textmonth2 = "Grudzień";
                            break;
                        default:
                            textmonth = "";
                            textmonth2 = "";
                    }
                    textView.setText(textmonth);
                    textView2.setText(textmonth2);
                } else if (i == 3) {
                    selCond = "AND strftime('%Y',datetime) = strftime('%Y','now'-1) AND strftime('%m',datetime) = strftime('%m','now'-1) AND  strftime('%W','now'-1) = strftime('%W',datetime)";
                    selCond2 = "AND strftime('%Y',datetime) = strftime('%Y','now') AND strftime('%m',datetime) = strftime('%m','now') AND  strftime('%W','now') = strftime('%W',datetime)";
                    textView.setText("Poprzedni tydzień");
                    textView2.setText("Obecny tydzień");
                }
                //final ListView listView =  (ListView) findViewById(R.id.listView);
                listView.setAdapter(new VizAdapter(ListOfStatistics.this));

                //Spinner spinner = (Spinner) findViewById(R.id.spinner);
                //spinner.setAdapter((SpinnerAdapter) adapterView.getAdapter());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                listView.setAdapter(new VizAdapter(ListOfStatistics.this));
            }
        });


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navmenu);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
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
        private String phsQuantity;
        private String value;
        private String value2;
        private String unit;


        SingleRow(String phsQuantity,String value, String value2,String unit) {
            this.phsQuantity = phsQuantity;
            this.value = value;
            this.value2 = value2;
            this.unit = unit;
        }

    }
    private class VizAdapter extends BaseAdapter {
        private ArrayList<SingleRow> list;
        private String value[];
        private String value2[];
        private DataBase db;
        private Cursor row;
        private Cursor row2;
        private Context context;
        private SharedPreferences shp;
        private int stoppervalue;
        private String stoppertime;

        VizAdapter(Context c) {
            context = c;
            list = new ArrayList<>();
            db = new DataBase(c);
            shp = getSharedPreferences("SessionPreference", 0);
            /*
            * Get Value from Database
            * */
            row = db.query("SELECT IFNULL(sum(distance),0) as sdistance,IFNULL(sum(stoppertime),0) as stime, IFNULL(sum(maxspeed),0) as smaxspeed " +
                    "FROM RUNS " +
                    "WHERE idUSER=" + shp.getInt("idUSER", 0) + " " + selCond);

            row.moveToFirst();
            value = new String[5];
            value[0] = row.getString(row.getColumnIndex("sdistance"));
            // value[1] = String.valueOf(Math.ceil(0.95 * 70 * row.getDouble(row.getColumnIndex("sdistance")) / 1000 * 1.04 * 100) / 100);
            //  value[1] = String.valueOf(Math.ceil(6.916 * row.getDouble(row.getColumnIndex("sdistance"))));

            value[1] = String.valueOf(Math.ceil(0.95 * 70 * ( row.getDouble(row.getColumnIndex("sdistance")))/1000 * 1.04) * 100 / 100);


            stoppervalue =  row.getInt(row.getColumnIndex("stime"));
            stoppertime = String.valueOf( (stoppervalue/3600000)%24)+ ":"+ String.valueOf((stoppervalue /60000)%60)+ ":" + String.valueOf((stoppervalue/1000)%60);

            value[2] = stoppertime;
            value[3] = stoppervalue >0 ?String.valueOf(Math.round(row.getDouble(row.getColumnIndex("sdistance"))*3600/ stoppervalue)):String.valueOf(0);//TODO: Correct this value of avgspeed
            value[4] = row.getString(row.getColumnIndex("smaxspeed"));

            /*
            * Get Value2 from Database
            * */
            if (!getResources().getStringArray(R.array.kind_of_statistic)[0].equals(spinner.getSelectedItem().toString())) {
                //sum(cast(stoppertime as INT)
                row2 = db.query("SELECT IFNULL(sum(distance),0) as sdistance,IFNULL(sum(stoppertime),0) as stime, IFNULL(sum(maxspeed),0) as smaxspeed " +
                        "FROM RUNS " +
                        "WHERE idUSER=" + shp.getInt("idUSER", 0) + " " + selCond2);
                row2.moveToFirst();
                value2 = new String[5];
                value2[0] = row2.getString(row2.getColumnIndex("sdistance"));
                // value2[1] = String.valueOf(Math.ceil(6.916 * row2.getDouble(row2.getColumnIndex("sdistance"))));
                //value2[1] = String.valueOf(Math.ceil(((((0.95) * 70) * ( row.getDouble(row.getColumnIndex("sdistance") / 1000) * 1.04) * 100)) / 100));
                Log.e("VALUE2[0]",String.valueOf(row2.getDouble(row2.getColumnIndex("sdistance"))));
                Log.e("VALUE2[1]",String.valueOf(Math.ceil(0.95 * 70 * ( row2.getDouble(row2.getColumnIndex("sdistance") )/1000 * 1.04) * 100) / 100));
                value2[1] = String.valueOf(Math.round(0.95 * 70 * ( row2.getDouble(row2.getColumnIndex("sdistance")))/1000 * 1.04) * 100 / 100);

                stoppervalue =  row2.getInt(row2.getColumnIndex("stime"));
                stoppertime = String.valueOf( (stoppervalue/3600000)%24)+ ":"+ String.valueOf((stoppervalue /60000)%60)+ ":" + String.valueOf((stoppervalue/1000)%60);

                value2[2] = stoppertime;
                value2[3] = stoppervalue >0 ?String.valueOf(Math.round(row2.getDouble(row2.getColumnIndex("sdistance"))*3600/ stoppervalue)):String.valueOf(0);//TODO: Correct this value of avgspeed
                value2[4] = row2.getString(row2.getColumnIndex("smaxspeed"));
            } else {
                value2 = new String[5];
                Arrays.fill(value2," ");

            }
           /*
            *  Amount of physical quantity
            * */
            Log.e("Amount of PHSQ", String.valueOf(getResources().getStringArray(R.array.physical_quantity).length));

            /*
            *  Insert physical quantity, value and value2 to list.
            * */
            for (int i = 0; i < getResources().getStringArray(R.array.physical_quantity).length; i++) {
                String phsQuantity = getResources().getStringArray(R.array.physical_quantity)[i];
                String unit = getResources().getStringArray(R.array.unit)[i];
                list.add(new SingleRow(phsQuantity, value[i], value2[i],unit));
            }
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
        public int getCount() {
            return list.size();
        }

        @Override

        /*
        * Display Rows
        * */
        public View getView(int i, View view, ViewGroup viewGroup) {


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowList = inflater.inflate(R.layout.statistic_single_row, viewGroup, false);

            Log.e("GETVIEW",String.valueOf(list.size()));

            SingleRow temp = list.get(i);
            TextView phsQuantity = (TextView) rowList.findViewById(R.id.physicalQuantityView);
            TextView vValue = (TextView) rowList.findViewById(R.id.valueView);
            TextView vValue2 = (TextView) rowList.findViewById(R.id.valueView2);
            TextView vUnit = (TextView) rowList.findViewById(R.id.unitView);

            phsQuantity.setText(temp.phsQuantity);

            //TODO: Check list
            vValue.setText(temp.value);
            vValue2.setText(temp.value2);
            vUnit.setText(temp.unit);


            return rowList;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


       /* item.setChecked(true);*/
        if(mToggle.onOptionsItemSelected(item)){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}