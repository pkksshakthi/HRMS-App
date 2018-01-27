package com.sphinax.hrms.global;

/**
 * Created by ganesaka on 12/24/2017.
 */

/**It is Constants class where we declare all static variables**/
public class Constants {

    //Live
    private static final String domain = "http://e-lite.in:8080/ezhrRest/";
    private static final String client = "user/";
    //private static final String marketing = "ezhrRest/marketing/";


    public static final String COMPANY_NAME_URL_PARAMETER = "{COMPANYID}";
    //ADMIN

    public static final String COMPANY_LIST_REQUEST_URL = domain + client +"Comp";
    public static final String BRANCH_LIST_REQUEST_URL = domain + client +"Branch";
    public static final String DEPARTMENT_LIST_REQUEST_URL = domain + client +"Dept";
    public static final String ANNOUNCEMENT_SAVE_URL = domain + client + "Save";
    public static final String EMPLOYEE_LIST_REQUEST_URL = domain + client + "Employee";
    public static final String ATTENDANCE_REPORT_EMPLOYEE_URL = domain + client + "AttReport";
    public static final String HR_HELPDESK_COUNT_URL = domain + client + "InitialHrHelpDeskReport";
    public static final String HR_HELPDESK_LIST_REQUEST_URL = domain + client + "HrHelpDeskReport";
    public static final String HR_HELPDESK_STATUS_LIST_REQUEST_URL = domain + client + "ReqStatus";
    public static final String HR_HELPDESK_OPEN_QUERY_URL = domain + client + "HrHelpDeskOpenQuery";
    public static final String HR_HELPDESK_SAVE_URL = domain + client + "HrHelpDeskSave";
    public static final String LEAVE_COUNT_REQUEST_URL = domain + client + "leavecount";
    public static final String LEAVE_LIST_REQUEST_URL = domain + client + "leaveList";
    public static final String LEAVE_APPROVE_PERMISSION_URL = domain + client + "saveHrPermission";


    // EMPLOYEE

    public static final String LOGIN_REQUEST_URL = domain + COMPANY_NAME_URL_PARAMETER +"/validateLogin";
    public static final String PROFILE_INFO_REQUEST_URL = domain + client +"EmpInfList";
    public static final String ANNOUNCEMENT_LIST_REQUEST_URL = domain + client + "AnnouncementList";
    public static final String ATTENDANCE_ENTER_URL = domain + client + "markYourAttendance";
    public static final String ATTENDANCE_ALREADY_ENTER_URL = domain + client + "showAttendance";
    public static final String LEAVE_COUNT_AND_TYPE_URL = domain + client + "getEmployeeLeaveCheckingTableNew";
    public static final String LEAVE_LIST_EMPLOYEE_REQUEST_URL = domain + client + "list";
    public static final String HR_HELPDESK_EMPLOYEE_SAVE_URL = domain + client + "saveHrhelpdesk";
    public static final String PAYSLIP_YEAR_LIST_REQUEST_URL = domain + client + "Year";
    public static final String PAYSLIP_MONTH_LIST_REQUEST_URL = domain + client + "Month";
    public static final String PAYSLIP_DETAIL_REQUEST_URL = domain + client + "DisplayPayslip";
    public static final String PAYSLIP_EARNING_DETAIL_REQUEST_URL = domain + client + "Earnings";
    public static final String PAYSLIP_DEDUCTION_DETAIL_REQUEST_URL = domain + client + "Deductions";
    public static final String PAYSLIP_DOWNLOAD_REQUEST_URL = domain + client + "DownloadPayslip";



   /* SHARED PREFERENCE PARAMETERS */

    public static final String PREFS_NAME = "hrms";
    public static final String PREFS_COMPANY_ID = "company_id";
    public static final String PREFS_COMPANY_NAME = "company_name";
    public static final String PREFS_COMPANY_SHORT_NAME = "company_short_name";
    public static final String PREFS_COMPANY_IMAGE = "company_image";

//    public static final String PREFS_USER_ID = "user_id";
//    public static final String PREFS_USER_NAME = "user_name";
//    public static final String PREFS_USER_TYPE = "user_type";




    /*Fragment Name*/

    public static final String FRAMENT_LOGIN = "LOGIN USER SCREEN";
    public static final String FRAMENT_FORGOT_PASSWORD = "FORGOT PASSWORD SCREEN";
    public static final String FRAMENT_USER_MENU = "DASHBOARD SCREEN";
    public static final String FRAMENT_ANTTENDANCE_ENTER = "ANTTENDANCE ENTER SCREEN";
    public static final String FRAMENT_ANTTENDANCE_LIST = "ANTTENDANCE REPORT SCREEN";
    public static final String FRAMENT_LEAVE_APPLY = "LEAVE APPLY SCREEN";
    public static final String FRAMENT_LEAVE_MANAGEMENT = "LEAVE MANAGEMENT SCREEN";
    public static final String FRAMENT_USER_INFO = "USER PROFILE SCREEN";
    public static final String FRAMENT_ANNOUNCEMENT_LIST = "ANNOUNCEMENT LIST SCREEN";
    public static final String FRAMENT_PAYSLIP = "PAYSLIP DETAIL SCREEN";
    public static final String FRAMENT_HR_HELPDESK_ENTER = "HR HELPDESK ENTER SCREEN";
//    public static final String FRAMENT_ = "";
//    public static final String FRAMENT_ = "";
//    public static final String FRAMENT_ = "";
//    public static final String FRAMENT_ = "";
//    public static final String FRAMENT_ = "";
//    public static final String FRAMENT_ = "";



}
