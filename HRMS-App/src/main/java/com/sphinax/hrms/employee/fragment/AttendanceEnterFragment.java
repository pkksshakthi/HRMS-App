package com.sphinax.hrms.employee.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sphinax.hrms.R;
import com.sphinax.hrms.common.fragment.SomeProblemFragment;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.global.Global;
import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.servicehandler.ServiceCallback;
import com.sphinax.hrms.servicehandler.WebServiceHandler;
import com.sphinax.hrms.utils.GeoLocationFinder;
import com.sphinax.hrms.utils.HRMSNetworkCheck;
import com.sphinax.hrms.utils.RequestPermissionHandler;
import com.sphinax.hrms.utils.Utility;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class AttendanceEnterFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {


    private static final String TAG = "AttendanceEnterFragment-";
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 1;
    private static Context context;
    private final WebServiceHandler webServiceHandler = new WebServiceHandler();
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private View mView;
    private Button bt_In_att, bt_out_att;
    private ImageView iv_userPhoto;
    private TextView tv_att_details;
    private ProgressDialog pdia;
    private ArrayList<Ajax> ajaxList;
    private String currentAddress;
    private SupportMapFragment mapFragment;
    private double longitude;
    private double latitude;
    private FragmentManager fragmentManager;
    //private  boolean allowRefresh = true;
    private RequestPermissionHandler mRequestPermissionHandler;
    private LocationRequest mLocationRequest;
    private Location location;
    private LocationManager manager;

    public AttendanceEnterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance_enter, null, false);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        loadMapView();

        return view;
    }

    public void onStart() {
        super.onStart();


        googleApiClient.connect();

    }

    public void onStop() {
        super.onStop();

        googleApiClient.disconnect();


    }

    private void handleButtonClicked() {
        mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                // Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });
        mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        }, 124, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                //  Toast.makeText(getActivity(), "request permission success", Toast.LENGTH_SHORT).show();
                loadMapView();
                loadMap();

            }

            @Override
            public void onFailed() {
                Toast.makeText(getActivity(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        mRequestPermissionHandler = new RequestPermissionHandler();
        handleButtonClicked();

        loadMapView();

        fetchDailyUserAttendance();
        loadComponent();
        setListeners();


        if (Global.isMarkAttendance()) {
            bt_In_att.setEnabled(false);
            bt_In_att.setClickable(false);

        }
        loadBitmap(Global.getLoginInfoData().getEmpImage());

    }

    private void loadComponent() {
        bt_In_att = mView.findViewById(R.id.bt_mark_in_att);
        bt_out_att = mView.findViewById(R.id.bt_mark_out_att);
        tv_att_details = mView.findViewById(R.id.tv_att_details);
        iv_userPhoto = mView.findViewById(R.id.iv_user_photo);
    }

    private void setListeners() {
        bt_In_att.setOnClickListener(this);
    }

    private void loadMapView() {
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void loadBitmap(String urlIV) {
        try {
            // URL url = new URL(Constants.IMAGE_URL +urlIV);
            Picasso.with(context)
                    .load(Constants.IMAGE_URL + urlIV)
                    .resize(48, 48).error(R.drawable.icon_profile_image).into(iv_userPhoto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("LongLogTag")
    private void loadMap() {
        if (HRMSNetworkCheck.checkInternetConnection(context)) {

            // Location location = GeoLocationFinder.getLocationOnly(context);

            try {
                if (HRMSNetworkCheck.checkInternetConnection(context)) {

                    // mMap.clear();


                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (location != null) {
                        //Getting longitude and latitude
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        Address address = GeoLocationFinder.getMyLocationAddress(context, location);
                        currentAddress = "" + address.getAddressLine(0) + "-" + address.getPostalCode();

                        moveMap();
                    }
                }
            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null)
            loadMap();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_mark_in_att:

                confirmAtendance();
                loadMap();
                tv_att_details.setText("" + currentAddress);
                break;
        }
    }


    private void checkAttendanceMarked() {

        if (ajaxList != null) {

            if (ajaxList.get(0).getCheckInTime() != null && !ajaxList.get(0).getCheckInTime().equalsIgnoreCase("")) {
                bt_In_att.setText(covertDate(ajaxList.get(0).getCheckInTime()));
                tv_att_details.setText(ajaxList.get(0).getLocation());
                if (ajaxList.get(0).getCheckOutTime().equalsIgnoreCase("")) {
                    bt_out_att.setText(" CHECK-OUT TIME ");
                } else {
                    bt_In_att.setText(covertDate(ajaxList.get(0).getCheckOutTime()));
                }
            }
        }
    }

    @SuppressLint("LongLogTag")
    private String covertDate(String dtStart) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        try {

            Date date = format.parse(dtStart);

            DateFormat anthorformat = new SimpleDateFormat("hh:mm:ss aa");
            String timeValue = anthorformat.format(date);
            return "Check-In Time \n " + timeValue;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST:
                if (canAccessLocation()) {
                    doLocationThing();
                } else {
                    bzzzt();
                }
                break;
        }
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == getActivity().checkSelfPermission(perm));
        }
        return false;
    }

    private void doLocationThing() {
        Utility.showCustomToast(context, mView, getResources().getString(R.string.toast_location));
    }

    private void bzzzt() {
        Utility.showCustomToast(context, mView, getResources().getString(R.string.toast_bzzz));
    }


    /***Method used to get already enter checkIn from the OPS Service **/

    @SuppressLint("LongLogTag")
    private void fetchDailyUserAttendance() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String datetime = dateformat.format(c.getTime());
            Log.d(TAG, "onViewCreated: " + datetime);
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("refId", Global.getLoginInfoData().getBiometricId().toString());
            requestMap.put("InDate", datetime);

            webServiceHandler.getDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (flag) {
                        //enterUserAttendance();
                        Global.setMarkAttendance(flag);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                    } else {
                        if (Global.getLoginInfoData().getMarkAttendacne().equalsIgnoreCase("S")) {
                            Global.setMarkAttendance(false);
                        } else {
                            bt_In_att.setEnabled(false);
                            bt_In_att.setClickable(false);
                        }
                        bt_In_att.setText("Check-In Time");
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    CompanyData companyData = (CompanyData) obj;
                    ajaxList = new ArrayList<>();
                    ajaxList = (ArrayList<Ajax>) companyData.getAjax();
                    Log.d(TAG, "size --> " + ajaxList.size());
                    Log.d(TAG, "size --> " + ajaxList.get(0).getCheckInTime());

                    checkAttendanceMarked();


                }

                @Override
                public void onParseError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onNetworkError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void enterUserAttendance() {
        if (!HRMSNetworkCheck.checkInternetConnection(context)) {
            Utility.showCustomToast(context, mView, getResources().getString(R.string.invalidInternetConnection));
            return;
        }
        final String datetime;
        pdia = new ProgressDialog(context);
        if (pdia != null) {
            pdia.setMessage("Loading...");
            pdia.show();
        }
        try {
            Calendar c = Calendar.getInstance();
            //SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            datetime = dateformat.format(c.getTime());
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("compId", Utility.getPreference(getActivity()).getString(Constants.PREFS_COMPANY_ID, ""));
            requestMap.put("empId", Global.getLoginInfoData().getUserId());
            requestMap.put("refId", Global.getLoginInfoData().getBiometricId().toString());
            requestMap.put("locationId", currentAddress);
            requestMap.put("empimageDesc", "123456789.JPG");
            requestMap.put("clkInTime", datetime);

            webServiceHandler.markDailyUserAttendance((Activity) context, context, requestMap, new ServiceCallback() {

                @Override
                public void onSuccess(boolean flag) {

                    if (pdia != null) {
                        pdia.dismiss();
                    }

                    if (!flag) {
                        bt_In_att.setText("Check-In Time");
                        tv_att_details.setText("");
                    } else if (flag) {
                        fetchDailyUserAttendance();
                        //bt_In_att.setText("Check-In Time \n "+datetime);
                        bt_In_att.setEnabled(false);
                        bt_In_att.setClickable(false);
                        Global.setMarkAttendance(flag);
                    }
                }

                @Override
                public void onReturnObject(Object obj) {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onParseError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

                @Override
                public void onNetworkError() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                    Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);

                }

                @Override
                public void unAuthorized() {
                    if (pdia != null) {
                        pdia.dismiss();
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            if (googleApiClient != null) {
                googleApiClient.connect();
            }
        }
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
            Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }
    }

    private void confirmAtendance() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("CHECK-IN");


        builder.setMessage("Hello, please to conform that ready to check in");


        //Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterUserAttendance();
            }
        });

        //No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        //Cancel Button
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
