package com.example.mg.tryappkillan.form;

import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mg.tryappkillan.R;


public class DataFragment2 extends Fragment{ // TODO - CLICK  implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private LocationManager lm;

    public DataFragment2() {
        // Required empty public constructor

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("FRAG","START");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("FRAG","Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("FRAG","Pause");
    }

    // TODO: Rename and change types and number of parameters
    public static DataFragment2 newInstance(String param1, String param2) {
        DataFragment2 fragment = new DataFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.data_fragment2,container,false);


        Button upButton = (Button) view.findViewById(R.id.startWorkout);
      //TODO - CLICK   upButton.setOnClickListener(this);
        //map.getMapAsync(this);
             //   map.getMapAsync(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public boolean visibilityValue(boolean visible)
    {

        return visible;
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.e("FRAG","VISIBLE");
        }
        visibilityValue(visible);

    }

   /* public void onClick(View v) {
    TODO - CLICK
        switch (v.getId()) {
            case R.id.startWorkout:
                /** Do things you need to..
                 fragmentTwo = new FragmentTwo();

                                 fragmentTransaction.replace(R.id.frameLayoutFragmentContainer, fragmentTwo);
                 fragmentTransaction.addToBackStack(null);

                 fragmentTransaction.commit();

                //startWorkout(v);
                break;
        }
    }*/

  /* public void startWorkout(View view) {
    TODO - CLICK

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            final LocationListener locList = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    Intent intent = new Intent(getActivity().getBaseContext(),MapsActivity.class);
                    intent.putExtra("location", location);
                    startActivity(intent);




                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                  //  Button but = (Button) getActivity().findViewById(R.id.startWorkout);
                  //  but.setText("onStatusChanged");
                }

                @Override
                public void onProviderEnabled(String s) {
                  //  Button but = (Button) getActivity().findViewById(R.id.startWorkout);
                  //  but.setText("onProviderEnabled");
                }

                @Override
                public void onProviderDisabled(String s) {
                  //  Button but = (Button) getActivity().findViewById(R.id.startWorkout);
                  //  but.setText("onProviderDisabled");
                }

            };
            //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, locList);
            // lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locList);
             lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locList);


        } else {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Button but = (Button) getActivity().findViewById(R.id.startWorkout);
            but.setText("stop");
            return;
        }
    } */

     interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

 }