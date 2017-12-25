package com.sphinax.hrms.servicehandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sphinax.hrms.utils.HRMSNetworkCheck;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class GetMethodHandler extends AsyncTask<Void, Void, JSONObject> {

    private static final String TAG = "GetMethodHandler-";
    private AsyncResponse delegate = null;
    private Context context;
    private URL url;
    private Activity activity = null;
    private Boolean isData;
    private String jsonString;

    public GetMethodHandler() {
    }

    public GetMethodHandler(Activity activity, Context context, String url, boolean isData, String jsonString, AsyncResponse response) throws MalformedURLException {
        this.context = context;
        this.url = new URL(url);
        this.delegate = response;
        this.isData = isData;
        this.activity = activity;
        this.jsonString = jsonString;
        Log.d(TAG + "URL-", url);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @SuppressLint("LongLogTag")
    @Override
    protected JSONObject doInBackground(Void... arg) {
        JSONObject jsonObject = null;
        try {
            // check internet connectivity

            if (!HRMSNetworkCheck.checkInternetConnection(context)) {
                return null;
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(600); //set timeout to 60 seconds
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
          /*  if(!jsonString.equalsIgnoreCase("")){
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                String input = jsonString;
                Log.d(TAG + "Request-", input);
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.close();
            }*/
            conn.connect();
            String response = "";
            int status = conn.getResponseCode();
            Scanner inStream = null;
            Log.d(TAG + "Response Code-", status + "");
            if (status == HttpsURLConnection.HTTP_BAD_REQUEST) {
                inStream = new Scanner(conn.getErrorStream());
            }
            if (status == HttpsURLConnection.HTTP_OK) {
                inStream = new Scanner(conn.getInputStream());
            }
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                inStream = new Scanner(conn.getErrorStream());
            }
            if (inStream != null) {
                //process the stream and store it in StringBuilder
                while (inStream.hasNextLine())
                    response += (inStream.nextLine());
            }
            conn.disconnect();
            Log.d(TAG + "Response-", response);
            if (status == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                if (!response.isEmpty()) {
                    Log.d(TAG + "SC_UNAUTHORIZED-", response);
                    JSONObject json = new JSONObject(response);
                    jsonObject = json.getJSONObject("meta");
                }
            } else {
                if (!response.isEmpty()) {
                     jsonObject = new JSONObject(response);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        try {
            delegate.processFinish(this.context, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
