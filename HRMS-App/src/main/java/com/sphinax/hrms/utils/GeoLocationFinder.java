package com.sphinax.hrms.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by ganesaka on 1/7/2018.
 */

public class GeoLocationFinder {
    private static final String TAG = "GeoLocationFinder-";

        private static GeoLocationFinder instance;

        private GeoLocationFinder() {
        }

        public static GeoLocationFinder getInstance() {
            if (instance == null)
                instance = new GeoLocationFinder();
            return instance;
        }

        /***This method retuns the Address Object by the location **/
        public static Address getLocation(Context context) {

            Address fetchedAddress = null;
            Location location = null;
            LocationManager locationManager;


            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d(TAG + "LocaitonManager-", "Network Provider is disabled.");
                // Toast.makeText(context, "Please Enable the GPS Connection.", Toast.LENGTH_SHORT).show();
                // Utility.showToastMessage(context, context.getResources().getString(R.string.networkProviderMessage));
            } else {
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    location = locationManager.getLastKnownLocation(provider);
                    if (location == null) {
                        Log.d(TAG + " -", "NO VAL");
                    } else {
                        fetchedAddress = getMyLocationAddress(context, location);
                        return fetchedAddress;
                    }
                }
                fetchedAddress = getMyLocationAddress(context, location);
                return fetchedAddress;
            }

            return fetchedAddress;

        }

        /***This method retuns the Address Object by the location **/

        private static Address getMyLocationAddress(Context context, Location location) {
            Address fetchedAddress1 = null;
            List<Address> addresses = null;
            try {

                Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
                //Place your latitude and longitude
                // List<Address> addresses = geocoder.getFromLocation(37.423247, -122.085469, 1);

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Log.d(TAG + "latitude-", "-" + latitude);
                    Log.d(TAG + "longitude-", "-" + longitude);

                    addresses = geocoder.getFromLocation(latitude, longitude, 1);

                }
                if (addresses != null) {

                    Address fetchedAddress = addresses.get(0);
                    StringBuilder strAddress = new StringBuilder();

                    for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                    }

                /*Log.d("VAL", "  " + fetchedAddress.getFeatureName());

                Log.d("VAL", "  " + fetchedAddress.getThoroughfare());
                Log.d("VAL", "  " + fetchedAddress.getSubThoroughfare());

                Log.d("VAL", "  " + fetchedAddress.getAdminArea());
                Log.d("VAL", "  " + fetchedAddress.getSubAdminArea());

                Log.d("VAL", "  " + fetchedAddress.getLocality());
                Log.d("VAL", "  " + fetchedAddress.getSubLocality());

                Log.d("VAL", "  " + fetchedAddress.getCountryCode());
                Log.d("VAL", "  " + fetchedAddress.getCountryName());

                Log.d("VAL", "  " + fetchedAddress.getPostalCode());

                Log.d("VAL", "  " + fetchedAddress.getAddressLine(0));
                Log.d("VAL", "  " + fetchedAddress.getAddressLine(1));
                Log.d("VAL", "  " + fetchedAddress.getAddressLine(2));*/

                    return fetchedAddress;

                } else {
                    return fetchedAddress1;
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                return fetchedAddress1;
            }

        }

        /*** This method retuns the double for distance current location to destination location  **/

        public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::   This function converts decimal degrees to radians                  :*/
     /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::   This function converts radians to decimal degrees                  :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
        private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }



        /*** This method used for maps google apis call **/

        public static void getNearestLocality(Context context, Address fetchedAddress){


            //Address fetchedAddress1 = Utility.addressretreive(context, Constants.LOCATION_ADDRESS_DATA, Address.class);

            if(fetchedAddress != null) {
                StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                // sb.append("location=" + mLatitude + "," + mLongitude);
                //sb.append("location=" + "12.8893107" + "," + "77.6495036" );
                sb.append("location=" + fetchedAddress.getLatitude() + "," + fetchedAddress.getLongitude() );
                sb.append("&radius=1000");
                // sb.append("&types=" + type);
                // sb.append("&types=" + "sublocality_level_1");
                sb.append("&types=" + "sublocality");
                sb.append("&sensor=true");
                sb.append("&key=AIzaSyBKXOOxnWu7nq_oOMZTMnEyYz4ldC6fIBM");

                // Creating a new non-ui thread task to download Google place json data
                PlacesTask placesTask = new PlacesTask();

                // Invokes the "doInBackground()" method of the class PlaceTask
                placesTask.execute(sb.toString());
                Log.d(TAG + "URL", "-API-"+ sb.toString());


            }else{

            }
        }



/**
 * A class, to download Google Places
 */
private static class PlacesTask extends AsyncTask<String, Integer, String> {

    String data = null;

    // Invoked by execute() method of this object
    @Override
    protected String doInBackground(String... url) {
        try {
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());

        }
        Log.d(TAG + "PlacesTask-", " " + data);


        return data;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(String result) {
        ParserTask parserTask = new ParserTask();

        // Start parsing the Google places in JSON format
        // Invokes the "doInBackground()" method of the class ParseTask
        parserTask.execute(result);
    }

}

    /**
     * A method to download json data from url
     */
    @SuppressLint("LongLogTag")
    private static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            Log.d("xyz", " " + url);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d(TAG+"Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }


/**
 * A class to parse the Google Places in JSON format
 */
 private static class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

    JSONObject jObject;

    // Invoked by execute() method of this object
    @Override
    protected List<HashMap<String, String>> doInBackground(String... jsonData) {

        List<HashMap<String, String>> places = null;
        PlaceJSONParser placeJsonParser = new PlaceJSONParser();

        try {
            jObject = new JSONObject(jsonData[0]);

            /** Getting the parsed data as a List construct */
            places = placeJsonParser.parse(jObject);

        } catch (Exception e) {
            Log.d(TAG+"Exception", e.toString());
        }
        return places;
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(List<HashMap<String, String>> list) {
        Log.d(TAG, " " + list.size());

        ArrayList<String> placeNameList = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++) {

            // Getting a place from the places list
            HashMap<String, String> hmPlace = list.get(i);

            // Getting latitude of the place
            double lat = Double.parseDouble(hmPlace.get("lat"));

            // Getting longitude of the place
            double lng = Double.parseDouble(hmPlace.get("lng"));

            // Getting name
            String name = hmPlace.get("place_name");
            Log.d(TAG, "place_name- " + name);
            placeNameList.add(name);
            // Getting vicinity
            String vicinity = hmPlace.get("vicinity");
            Log.d(TAG, "vicinity- " + vicinity);

        }

        //Global.setPlaceNameList(placeNameList);

    }

}
}