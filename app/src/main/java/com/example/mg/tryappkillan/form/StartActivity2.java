package com.example.mg.tryappkillan.form;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.form.help.Stopper;
import com.example.mg.tryappkillan.interf.ITimerActivity;
import com.example.mg.tryappkillan.interf.clickMenu;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mg.tryappkillan.R.id.caloriesView;
import static com.example.mg.tryappkillan.R.id.editText;
import static com.example.mg.tryappkillan.R.id.etDistanceView;

/**
 * Created by mg on 28/03/17.
 */


public class StartActivity2 extends AbsStartActivity implements DataFragment2.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,ITimerActivity, clickMenu {


    private LocationRequest mLocationRequest;
    private MapFragment mapFragment;
    private LocationManager lm;


    private Stopper mstopper;
    private Thread mThreadForStopper;
    private LocationListener ll;
    private ArrayList<Location> array_sample;
    private MyMenu myMenu;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    private Sessions session;
    /*
    * Google Play Services
    * */
    private GoogleApiClient mGoogleApiClient;
    /*
    * Logic group
    */
    private double mydistance;
    private double maxspeed;
    private double avgspeed;
    private double calories;
    private int stopperTime;
    /*
    * SharedPreferences
    * */
    private SharedPreferences mySharedPref;
    private SharedPreferences.Editor editor;
    /*
    * View elements in StartActivity
    * */
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Button button;
    private TextView stopper;
    private TextView vdistance;
    private TextView vcalories;
    private TextView vavgspeed;
    private TextView vmaxspeed;
    private EditText editText;
    /*
    * Constants
    * */
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    public final static int DETECTION_INTERVAL_MILLISECONDS = 20000;
    public final static int REQUEST_LOCATION_INTERNAL = 500;
    /*
    * Flags
    * */
    private boolean click;
    /*
    * Counters
    * */
    /*
    * TODO: TEST variable
    * */


    /*
    * LIFE CYCLE METHODS
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   /*
    * Static created view elements in StartActivity
    * */
        setContentView(R.layout.activity_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  toolbar.inflateMenu(R.menu.menu_head);
        //it creates back button.
   //     setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

//        Create pager 1. Dane, 2.Mapa
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        Check Google availability of PlayServices
        if (checkPlayServices()) {
            builderGoogleApi();
            createLocationRequest();
        }
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        click = false;
        editor = null;
        mySharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        session = new Sessions(this);
        session = new Sessions(this);
        if (!session.logginIN()) {
            Log.e("SESSION", String.valueOf(session.logginIN()));
            finish();
            session.clearSession();
            startActivity(new Intent(this, LoginForm.class));
        }
//        You shouldn't create GoogleApiClient in onStart because of double reference on second run
        if (mGoogleApiClient != null) mGoogleApiClient.connect();


        array_sample = new ArrayList<>();


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

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

//  TODO:change
//        mActivityRecognition = new ActivityRecognition();
//        TODO:END change


        return super.onCreateView(parent, name, context, attrs);
    }

 /*   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
      //  setContentView(R.layout.activity_start);

    }
*/

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("LOG", "onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("SA","onStart");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("SA","onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("SA","onResume");

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("SA","onStop");
    }

    @Override
    protected void onDestroy() {
        Log.e("SA", "onDestroy");
        if(click) {
            click = false;
            clickedButton();
        }
        super.onDestroy();

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            mGoogleApiClient.disconnect();
        }
    }

    private void findPathsOfViews() {
        stopper = (TextView) findViewById(R.id.stopperView);
        button = (Button) findViewById(R.id.startWorkout);
        vdistance = (TextView) findViewById(R.id.distanceView);
        vcalories = (TextView) findViewById(caloriesView);
        vavgspeed = (TextView) findViewById(R.id.avarageSpeedView);
        vmaxspeed = (TextView) findViewById(R.id.maxSpeedView);
        editText =  (EditText) findViewById(R.id.etDistanceView);
    }





    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DataFragment2(), "Dane");
        viewPager.getCurrentItem();
        viewPager.setAdapter(adapter);
    }


    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

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


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);

        }

        //@Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        //@Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void startWorkout(View view) {
        clickedButton();
    }


    private void clickedButton() {
        findPathsOfViews();

        if (isCorrectFormatOfDistance()) {
            click = !click;
            if (click) {

                button.setText("stop");
                editText.setEnabled(false);


                mstopper = new Stopper(this);
                mThreadForStopper = new Thread(mstopper);
                if (!mThreadForStopper.isAlive()) {
                    mThreadForStopper.start();
                    mstopper.start();
                }
                setDataInsideRUNS();

          /*
                //Button button = new Button(this);
                //button.setText("Pauza");
                // LinearLayout linearLayout = (LinearLayout) findViewById(R.id.1)

         *//*   mstopper = new Stopper(ITimerActivity);
            mThreadForStopper = new Thread(mstopper);

            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }
            if (!mThreadForStopper.isAlive()) {
                mThreadForStopper.start();
                mstopper.start();
            }
            setDataInsideRUNS();
             startLocationUpdates(ll);
            click = true;
            }*/
            }else {
            button.setText("start");

            editText.setEnabled(true);

                stopperTime = mstopper.getSince();
                mstopper.mstop();
                mThreadForStopper.interrupt();
                mThreadForStopper = null;
                mstopper = null;

            mydistance = Double.valueOf(editText.getText().toString());

            calories = Math.ceil(((((0.95) * 70) * (mydistance/1000) * 1.04) * 100)) / 100;
            vcalories.setText(String.valueOf(calories));

            avgspeed = Double.valueOf(Math.ceil(mydistance*3600 /stopperTime));
            vavgspeed.setText(String.valueOf(avgspeed));
            maxspeed = avgspeed;


            updateDataInsideRUNS();

            /*
            * Get last stopper Time
            * */
               /* stopperTime = mstopper.getSince();
                //Stopping stopper
                mstopper.stop();
                mThreadForStopper.interrupt();
                mThreadForStopper = null;
                mstopper = null;

                stopLocationUpdates(ll);
                updateDataInsideRUNS();

                //Clear values of location listener
                calories = 0;
                mydistance = 0;
                maxspeed = 0;
                array_sample.clear();


                try {
                    mapFragment.getGoogleMap().clear();
                    mapFragment.getGoogleMap().resetMinMaxZoomPreference();
                    mapFragment.getGoogleMap().stopAnimation();
                    mapFragment.locations.clear();
                } catch (NullPointerException e) {

                }

                button.setText("start");
                clearTextViews(); //TODO: Turn ON clearTextView after test

                return;*/
            }
            editor = mySharedPref.edit();
            editor.putBoolean("startWorkoutc", click);
            editor.commit();

        }
    }

    private boolean isCorrectFormatOfDistance() {
        String text = editText.getText().toString();
        //Max distance ->  1 bln [m] ->  1 mln [km]
        String pattern = "[0-9]{1,10}(.[0-9]{1,2}){0,1}";

        if(text.matches(pattern)) return true;
        else {
            editText.setError("Wpisano błędne dane do formularza");
            return false;
        }
    }
 /*       if( editText.isFocusable())
        {
            editText.setFocusable(false);
            return  true;
        }
        else
        {

            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            return false;
        }*/
      /*  if (text.matches(pattern)) {
            // after do below command. I think is always false due to nothing happend
           // editText.setFocusable((false));

            // below comands makes that afther first click in log is true, after second click false and it repeats.
            return  true;
        } else {
            editText.setError("Wpisano błędne dane do formularza");
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
            return false;
        }*/


    private void clearTextViews() {

        vdistance.setText("0.00");
        vcalories.setText("0.00");
        vavgspeed.setText("0.00");
        vmaxspeed.setText("0.00");
        //TODO: it doesn't work - why?
        stopper.setText("00:00:00:000");
    }
    //Turn off Location Request
   protected void stopLocationUpdates(LocationListener ll)
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,ll);
    }

    /**
     * Lacation Update
     * */
    protected void startLocationUpdates(LocationListener ll) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) ==  PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, ll) ;
        }

    }
    /**
     * Method to create prepare Request location
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(REQUEST_LOCATION_INTERNAL);
        mLocationRequest.setSmallestDisplacement(20);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    /**
     * Method to create GoogleApiClient
     * */
    protected synchronized void builderGoogleApi()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .addApi(LocationServices.API).build();
    }
    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    /*
    * Method sets data inside SUBRUNS.
    * */

   private void setDataInsideGPS(Location location)
    {
        SharedPreferences shp = this.getSharedPreferences("SessionPreference",0);
        DataBase db = new DataBase(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date datetime = new Date();

        db.execute("INSERT INTO GPS ( " +
                "idGPS,latitude,longitude,datetime,idRUN" +
                ")" +
                "VALUES (" +
                "NULL," + location.getLatitude() +
                ", " + location.getLongitude() + ", '" + dateFormat.format(datetime) + "'," + shp.getInt("idRUN",0)+
                ")");
        db.close();
    }
    private void setDataInsideRUNS(){
        SharedPreferences shp = this.getSharedPreferences("SessionPreference",0);
        DataBase db = new DataBase(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = new Date();
     //   dateFormat.setTimeZone(TimeZone.getDefault());
        db.execute("INSERT INTO RUNS(" +
                "idRUN,datetime,distance,stoppertime, maxspeed, idUSER" +
                ")" +
                "VALUES( NULL,'" +dateFormat.format(datetime)+"',"+ 0+ ","+ 0+ ","+ 0+ ","+shp.getInt("idUSER",0) +
                ")");
    /*
    * Set idRun - it's needed to update
    * */
        Cursor result = db.query("SELECT idRUN FROM RUNS WHERE idUSER="+shp.getInt("idUSER",0) + " ORDER BY idRUN DESC LIMIT 1");
        result.moveToFirst();
        SharedPreferences.Editor shpE = shp.edit();
        shpE.putInt("idRUN",result.getInt(0));
        shpE.commit();
        db.close();
    }
    //Override RUNS record - it allows to avoid end check.
    private void updateDataInsideRUNS(){
        SharedPreferences shp = this.getSharedPreferences("SessionPreference",0);
        DataBase db = new DataBase(this);

        db.execute("UPDATE RUNS " +
                "SET distance="+mydistance+", stoppertime="+stopperTime +", maxspeed="+maxspeed+" "+
                "WHERE idRUN="+shp.getInt("idRUN",0) );

        Log.e("distance",String.valueOf(mydistance));

        Cursor row = db.query("SELECT datetime,distance,stoppertime " +
                "FROM RUNS " +
                "WHERE idRUN="+shp.getInt("idRUN",0));
        row.moveToFirst();
        db.close();
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopper.setText(time);
            }
        });
    }
    private double calcDistance(ArrayList<Location> sample )
    {
    /*
    * Compare current location with last sampled location.
    * */
        Location lastlocation = new Location("gps");

        Log.e("LAT",String.valueOf(sample.get(sample.size()-1).getLatitude()));
        Log.e("LNG",String.valueOf(sample.get(sample.size()-1).getLongitude()));
        if(sample.size() != 1)
        {
            lastlocation.set(sample.get(sample.size()-2));
            return Math.ceil(sample.get(sample.size()-1).distanceTo(lastlocation)); //return [m]
        }
        return 0;
    }


    private void buildAlertMessageNoGps(){
        //  final AlertDialog.Builder

        final AlertDialog.Builder alt = new AlertDialog.Builder(this,R.style.Theme_AppCompat );

        alt
                .setTitle("Ustawienia GPS")
                .setMessage("GPS jest wyłączony. Czy chcesz przejść do ustawień, aby go włączyć?")
                .setCancelable(false)
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    /*
    * Testing
    * */
  /*  private Location testGPS()
    {

        Location location= new Location("test");
        EditText test = (EditText) findViewById(R.id.gpslatitude);
        EditText test2 = (EditText) findViewById(R.id.gpslongitude);

        EditText timer = (EditText) findViewById(R.id.timer);
    if(test.getText().length() != 0 & test2.getText().length() != 0) {
        location.setLatitude(Double.valueOf(test.getText().toString()));
        location.setLongitude(Double.valueOf(test2.getText().toString()));
    }

        double stopperhour =0;

        if(timer.getText().length() != 0)
        {
            stopperhour=(Double.valueOf(timer.getText().toString().trim())/3600000);
            stopper.setText(String.valueOf(timer.getText().toString().trim()));
            //TODO convert to sec
        }
         array_sample.add(location);
       double smalldistance = calcDistance(array_sample) ;
        mydistance += smalldistance;


        if (mapFragment.getGoogleMap() != null) {
            double mydistancekm = Math.ceil(mydistance /10)/100;
            mapFragment.setLocationsElement(location);
            mapFragment.addLocationToMap(mapFragment.locations.size() - 1);

            setDataInsideSUBRUNS(location);

            if (mydistance != 0) {
                //Show distance on layout
                vdistance.setText(String.valueOf(mydistancekm));

                //Count & Show calories on layout
                calories = Math.ceil(((((0.95) * 70) * (mydistance / 1000) * 1.04) * 100)) / 100;
                vcalories.setText(String.valueOf(calories));


                //Count & Show average speed on layout
                double avgspeed = Math.ceil(mydistancekm *100/ stopperhour)/100;
                vavgspeed.setText(String.valueOf(avgspeed));


                //Count & Show max speed on layout
                double newspeed = Math.ceil( mydistancekm *100 / stopperhour)/100;

                if (newspeed > maxspeed) {
                    maxspeed = newspeed;
                    vmaxspeed.setText(String.valueOf(maxspeed));
                }
            }

        }

        return location;
    }*/

    private  void cleartestGPS()
    {
        array_sample = null;
        MapFragment mapFragment = new MapFragment();
        mapFragment.getGoogleMap().clear();
        clearTextViews();

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // super.onOptionsItemSelected(item);
        return mToggle.onOptionsItemSelected(item) ? true : super.onOptionsItemSelected(item);
    }
}


