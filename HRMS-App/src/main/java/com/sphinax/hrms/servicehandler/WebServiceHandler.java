package com.sphinax.hrms.servicehandler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.CompanyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class WebServiceHandler {

    private static final String TAG = "WebServiceHandler-";
    private ServiceCallback delegate = null;
    private Context serviceContext = null;
    private Activity serviceActivity = null;

//    public void checkCity(Activity activity, Context context, String date, ServiceCallback callback) throws MalformedURLException {
//        try {
//            delegate = callback;
//            serviceContext = context;
//            serviceActivity = activity;
//            String url = Constants.CITY_REQUEST_DATA_CHANGE_URL;
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("lastFetchedDate", date);
//
//            String json = jsonObject.toString();
//
//            Log.d(TAG + "currentDateTime -", json);
//            PostMethodHandler cityHandler = new PostMethodHandler(activity, serviceContext, url, json, new AsyncResponse() {
//                @Override
//                public void processFinish(Context responseContext, JSONObject output) throws JSONException {
//
//                    try {
//                        if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
//                                try {
//                                    if (output.has("cityDataChanged")) {
//                                        if (output.getBoolean("cityDataChanged")) {
//                                            delegate.onSuccess(true);
//                                        } else {
//                                            delegate.onSuccess(false);
//                                        }
//                                    } else {
//                                        delegate.onDataNotAvailable();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    delegate.onParseError();
//                                }
//                            }
//                        } else {
//                            delegate.onNetworkError();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        delegate.onNetworkError();
//                    }
//                }
//            });
//            cityHandler.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//            delegate.onNetworkError();
//        }
//    }


//    public void getBookingDetails(Activity activity, final Context context, String bookingId, ServiceCallback callback) throws MalformedURLException {
//        try {
//            delegate = callback;
//            serviceContext = context;
//            serviceActivity = activity;
//
//            String url = Constants.BOOKING_ORDER_DETAIL_URL;
//            url = url.replace("{BOOKINGNO}", String.valueOf(bookingId));
//
//
//            GetMethodHandler bookingDetailsHandler = new GetMethodHandler(activity, serviceContext, url, true, true, new AsyncResponse() {
//                @Override
//                public void processFinish(Context responseContext, JSONObject output) throws JSONException {
//                    Gson gson = new Gson();
//                    try {
//                        if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
//                                try {
//                                    if (output.getJSONObject("bookingDetails") != null) {
//
//                                        BookingDetails bookingDtailsObject = gson.fromJson(output.getJSONObject("bookingDetails").toString(), BookingDetails.class);
//
//                                        Utility.setCatchData(serviceContext, bookingDtailsObject, Constants.chacheBookingDetails);
//                                        delegate.onSuccess(true);
//                                    }
//
//
//                                } catch (Exception e) {
//                                    delegate.onParseError();
//                                }
//                            }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        delegate.onNetworkError();
//                    }
//                }
//            });
//            bookingDetailsHandler.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            delegate.onNetworkError();
//        }
//    }


    public void getCompanyList(Activity activity, final Context context, ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;
            String json = "";

            String url = Constants.COMPANY_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, true,json , new AsyncResponse() {
                @Override
                public void processFinish(Context responseContext, JSONObject output) throws JSONException {
                    Gson gson = new Gson();
                    try {
                        if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                                try {
                                    if (output.getJSONArray("ajax") != null) {
                                        CompanyData companyDataObject = gson.fromJson(output.toString(), CompanyData.class);
                                        delegate.onSuccess(true);
                                        delegate.onReturnObject(companyDataObject);
                                    }
                                } catch (Exception e) {
                                    delegate.onParseError();
                                }
                            }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        delegate.onNetworkError();
                    }
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getBranchList(Activity activity, final Context context, int branchId,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.BRANCH_LIST_REQUEST_URL;
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("compId", 1000);
            String json = jsonObject.toString();

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, true,json , new AsyncResponse() {
                @Override
                public void processFinish(Context responseContext, JSONObject output) throws JSONException {
                    Gson gson = new Gson();
                    try {
                        if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                            try {
                                if (output.getJSONArray("ajax") != null) {
                                    CompanyData companyDataObject = gson.fromJson(output.toString(), CompanyData.class);
                                    delegate.onSuccess(true);
                                    delegate.onReturnObject(companyDataObject);
                                }
                            } catch (Exception e) {
                                delegate.onParseError();
                            }
                        }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                        delegate.onNetworkError();
                    }
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


}
