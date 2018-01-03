package com.example.mg.tryappkillan.form;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mg.tryappkillan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;


//@SuppressLint("ValidFragment")
public class MapFragment extends Fragment implements OnMapReadyCallback,Serializable {
    private MapView mMapView;
    private boolean isViewShown;
    private GoogleMap googleMap;
    private MarkerOptions options;
    public ArrayList<Location> locations;
    private String stringer;
   // private PolylineOptions rectOptions = new PolylineOptions();
    private Polyline polyLine;



    public MapFragment() {
    }
    //TRY2 - Getter & Setter
    public String getStringer() {
        Log.e("LOG","stringer2");
        return stringer;
    }
    //TRY2 - Getter & Setter
    public void setStringer(String stringer) {
        Log.e("LOG","stringer");
        this.stringer = stringer; //stringer;
    }
    //TRY2 - Getter & Setter
    public ArrayList<Location> getLocations() {

        return locations;
    }
   public void setLocationsElement(Location location)
    {
        this.locations.add(location);
    }
    //TRY2 - Getter & Setter
    public void setLocations(ArrayList<Location> locations) {

        this.locations = locations;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.e("LOG","OnCreate");
        options = new MarkerOptions();
        locations = new ArrayList<>();
        isViewShown = false;
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        Thread timer = new Thread() {
             @Override
             public void run() {
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         if (googleMap != null) {
                                Log.e("Info2", String.valueOf(locations.size()));
                             for (int i = 0; i < locations.size(); i++) {
                                 addLocationToMap(i);
                             }
                         }
                     }
                 });
             }
         };
        if (isVisibleToUser) {
            timer.start();
        }
        else
        {
            timer.interrupt();
        }

    }

    public void addLocationToMap(int i) {
        Log.e("Info2","added location to map");
        if (getView() != null) {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(locations.get(i).getLatitude(), locations.get(i).getLongitude())));
            updatePolyLine(marker);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(marker.getPosition())
                    .zoom(17)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }


    public void updatePolyLine(Marker marker) {
        List<LatLng> points = polyLine.getPoints();
        points.add(marker.getPosition());
        polyLine.setPoints(points);


    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_fragment,container,false);
      try {
           MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);



      } catch (InflateException e)
        {
            Log.e(TAG, "Inflate exception");
        }
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onMapReady(GoogleMap map) {
        googleMap = map;
        polyLine = map.addPolyline(new PolylineOptions());
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.e("LOG","onPause");
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
       mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

