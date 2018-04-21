package com.sphinax.hrms.servicehandler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sphinax.hrms.global.Constants;
import com.sphinax.hrms.model.AdminCompanyData;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.model.LoginData;
import com.sphinax.hrms.model.PaymentData;
import com.sphinax.hrms.model.ServiceRequest;
import com.sphinax.hrms.utils.Utility;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class WebServiceHandler {

    private static final String TAG = "WebServiceHandler-";
    private ServiceCallback delegate = null;
    private Context serviceContext = null;
    private Activity serviceActivity = null;
    private ServiceRequest serviceRequestValue = null;

    public void genrateOTP (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.GENRATE_OTP_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            delegate.onSuccess(true);
//                                if (output.getInt("resCode") == 1) {
//                                    LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
//                                    delegate.onSuccess(true);
//                                    delegate.onReturnObject(loginDataObject);
//                                }else {
//                                    delegate.onSuccess(false);
//                                }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void checkOTP (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.CHECK_OTP_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {

                                delegate.onSuccess(true);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void changePassword (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PASSWORD_CHANGE_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {

                                delegate.onSuccess(true);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getCompanyList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.COMPANY_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                                }else {
                                    delegate.onSuccess(false);

                                }
                            } catch (Exception e) {
                                delegate.onParseError();
                            }
                        }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getBranchList(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.BRANCH_LIST_REQUEST_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getDepartmentList(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.DEPARTMENT_LIST_REQUEST_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getEmpList(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.EMPLOYEE_LIST_REQUEST_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void validateUser (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LOGIN_REQUEST_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {
                                LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(loginDataObject);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getDailyUserAttendance(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ATTENDANCE_ALREADY_ENTER_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if(output.getInt("resCode") == 1){
                            if (output.getJSONArray("ajax") != null) {
                                CompanyData companyDataObject = gson.fromJson(output.toString(), CompanyData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(companyDataObject);
                            }}else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void markDailyUserAttendance(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ATTENDANCE_ENTER_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if(output.getInt("resCode") == 1){
                                delegate.onSuccess(true);
                               }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getLeaveTypeList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LEAVE_COUNT_AND_TYPE_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getUserInfo(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PROFILE_INFO_REQUEST_URL;

            GetMethodHandler userListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            userListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getQueryTypeList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_QUERY_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                        delegate.onSuccess(false);

                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getEMPQueryList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_EMPLOYEE_LIST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void saveUserQuery(Activity activity, final Context context,  HashMap<String, String> requestMap ,ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_EMPLOYEE_SAVE_URL;

            GetMethodHandler branchListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if(output.getInt("resCode") == 1){
                                delegate.onSuccess(true);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            branchListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }



    public void getAnnouncementList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ANNOUNCEMENT_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }



    public void getYearList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PAYSLIP_YEAR_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getMonthList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PAYSLIP_MONTH_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void getDisplayPaySlip(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PAYSLIP_DETAIL_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                              if (output.getInt("resCode") == 1) {
                                if (output.getJSONObject("ajax") != null) {
                                    Log.d("ajaxList", "earning --> " + output.getJSONObject("ajax"));
                                    PaymentData companyDataObject = gson.fromJson(output.toString(), PaymentData.class);
                                    Log.d("ajaxList", "earning --> " + companyDataObject.getAjax());

                                    delegate.onSuccess(true);
                                    delegate.onReturnObject(companyDataObject);
                                } else {
                                    delegate.onSuccess(false);

                                }
                            }else {
                                  delegate.onSuccess(false);
                                  delegate.onReturnObject(null);
                              }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getAttendanceReport(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ATTENDANCE_REPORT_EMPLOYEE_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if (output.getJSONArray("ajax") != null) {
                                CompanyData companyDataObject = gson.fromJson(output.toString(), CompanyData.class);
                                Log.d("ajaxList 1", "earning --> " + output.getJSONArray("ajax"));

                                delegate.onSuccess(true);
                                delegate.onReturnObject(companyDataObject);
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }



    public void getPaySlipEarnings(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PAYSLIP_EARNING_DETAIL_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getPaySlipDeductions(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.PAYSLIP_DEDUCTION_DETAIL_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getAdminAnnouncementList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ADMIN_ANNOUNCEMENT_LIST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if (output.getJSONArray("ajax") != null) {
                                Log.d("AKKK" ,output.toString() );
                                AdminCompanyData companyDataObject = gson.fromJson(output.toString(), AdminCompanyData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(companyDataObject);
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }
//    public void getPaySlipDownload(Activity activity, final Context context, HashMap<String, String> requestMap,File directory, ServiceCallback callback) throws MalformedURLException {
//        try {
//            delegate = callback;
//            serviceContext = context;
//            serviceActivity = activity;
//
//            String url = Constants.PAYSLIP_DOWNLOAD_REQUEST_URL;
//
//            FileDownloader companyListHandler = new FileDownloader(activity, serviceContext, url, true,requestMap , directory, new AsyncResponse() {
//                @Override
//                public void processFinish(Context responseContext, JSONObject output) throws JSONException {
//                    Gson gson = new Gson();
//                    try {
//                        if (output != null) {
//                            try {
//
//                            } catch (Exception e) {
//                                delegate.onParseError();
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                        delegate.onNetworkError();
//                    }
//                }
//            });
//            companyListHandler.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            delegate.onNetworkError();
//        }
//    }

    public void getEmpLeaveList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.EMP_LEAVE_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getAllLeaveList(Activity activity, final Context context, HashMap<String, String> requestMap, ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LEAVE_LIST_REQUEST_URL;

            GetMethodHandler companyListHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
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
                            }else {
                                delegate.onSuccess(false);

                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            companyListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void saveAnnouncement (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ANNOUNCEMENT_SAVE_URL;
           // url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            PostMethodHandler validateUserHandler = new PostMethodHandler(activity, serviceContext, url,requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {
                                LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(loginDataObject);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void applyLeaveRequest (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LEAVE_APPLY_REQUEST;

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {
                                LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(loginDataObject);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void upodateHrHelp (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_EMPLOYEE_UPDATE_URL;
            url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            GetMethodHandler validateUserHandler = new GetMethodHandler(activity, serviceContext, url, requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {
                            if (output.getInt("resCode") == 0) {
                                delegate.onSuccess(true);
                            }
//                                if (output.getInt("resCode") == 1) {
//                                    LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
//                                    delegate.onSuccess(true);
//                                    delegate.onReturnObject(loginDataObject);
//                                }else {
//                                    delegate.onSuccess(false);
//                                }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


    public void updateAnnouncement (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ANNOUNCEMENT_UPDATE_URL;
            // url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            PostMethodHandler validateUserHandler = new PostMethodHandler(activity, serviceContext, url,requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {
                                LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(loginDataObject);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void deleteAnnouncement (final Activity activity, final Context context, HashMap<String, String> requestMap , ServiceCallback callback) {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.ANNOUNCEMENT_DELETE_URL;
            // url = url.replace("{COMPANYID}", Utility.getPreference(activity).getString(Constants.PREFS_COMPANY_SHORT_NAME, ""));

            PostMethodHandler validateUserHandler = new PostMethodHandler(activity, serviceContext, url,requestMap , (responseContext, output) -> {
                Gson gson = new Gson();
                try {
                    if (output != null) {
//                            if (CheckUnAuthorised(output)) {
//                                delegate.unAuthorized();
//                            } else {
                        try {

                            if (output.getInt("resCode") == 1) {
                                LoginData loginDataObject = gson.fromJson(output.toString(), LoginData.class);
                                delegate.onSuccess(true);
                                delegate.onReturnObject(loginDataObject);
                            }else {
                                delegate.onSuccess(false);
                            }
                        } catch (Exception e) {
                            delegate.onParseError();
                        }
                    }else {
                        delegate.onNetworkError();
                    }
//                        } else {
//
//                            delegate.onNetworkError();
//                        }
                } catch (Exception e) {
                    e.printStackTrace();

                    delegate.onNetworkError();
                }
            });
            validateUserHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

/*
    public void getDepartmentList(Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.DEPARTMENT_LIST_REQUEST_URL;

            GetMethodHandler departmentListHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            departmentListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

   // ANNOUNCEMENT_SAVE_URL Pending

    public void getEmployeeList (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.EMPLOYEE_LIST_REQUEST_URL;

            GetMethodHandler employeeListHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            employeeListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }


// ATTENDANCE_REPORT_EMPLOYEE_URL pending

    public void getHrHelpDeskCount(Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_COUNT_URL;

            GetMethodHandler hrHelpDeskCountHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            hrHelpDeskCountHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }





    public void getHrHelpDeskReportList(Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_LIST_REQUEST_URL;

            GetMethodHandler hrHelpDeskReportListHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            hrHelpDeskReportListHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getHrHelpDeskStatus(Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_STATUS_LIST_REQUEST_URL;

            GetMethodHandler hrHelpDeskStatusHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            hrHelpDeskStatusHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getHrHelpDeskOpenQuery (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_OPEN_QUERY_URL;

            GetMethodHandler hrHelpDeskOpenQueryHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            hrHelpDeskOpenQueryHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }
    public void saveHrHelpdesk (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.HR_HELPDESK_SAVE_URL;

            GetMethodHandler saveHrHelpdeskHandler  = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            saveHrHelpdeskHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getLeaveCount (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LEAVE_COUNT_REQUEST_URL;

            GetMethodHandler leaveCountHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            leaveCountHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

    public void getLeaveListAdmin (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.LEAVE_LIST_REQUEST_URL;

            GetMethodHandler leaveListAdminHandler = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            leaveListAdminHandler.execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }

/*
    public void (Activity activity, final Context context, ServiceRequest serviceRequest ,ServiceCallback callback) throws MalformedURLException {
        try {
            delegate = callback;
            serviceContext = context;
            serviceActivity = activity;

            String url = Constants.;

            GetMethodHandler  = new GetMethodHandler(activity, serviceContext, url, true,serviceRequest , new AsyncResponse() {
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
            .execute();
        } catch (Exception e) {
            e.printStackTrace();

            delegate.onNetworkError();
        }
    }*/

}
