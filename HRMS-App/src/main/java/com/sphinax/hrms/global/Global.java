package com.sphinax.hrms.global;

import com.sphinax.hrms.model.Ajax;
import com.sphinax.hrms.model.LoginData;

import java.util.ArrayList;

/**
 * Created by ganesaka on 1/6/2018.
 */

public class Global {

    private static boolean markAttendance;
    private static ArrayList<Ajax> leaveList;
    private static Ajax userInfoData;
    private static LoginData loginInfoData;
    private static boolean userDataTaken = false;
    private static int TabPosition = 0;

    public static boolean isUserDataTaken() {
        return userDataTaken;
    }

    public static void setUserDataTaken(boolean userDataTaken) {
        Global.userDataTaken = userDataTaken;
    }

    public static LoginData getLoginInfoData() {
        return loginInfoData;
    }

    public static void setLoginInfoData(LoginData loginInfoData) {
        Global.loginInfoData = loginInfoData;
    }

    public static Ajax getUserInfoData() {
        return userInfoData;
    }

    public static void setUserInfoData(Ajax userInfoData) {
        Global.userInfoData = userInfoData;
    }

    public static boolean isMarkAttendance() {
        return markAttendance;
    }

    public static void setMarkAttendance(boolean markAttendance) {
        Global.markAttendance = markAttendance;
    }

    public static ArrayList<Ajax> getLeaveList() {
        return leaveList;
    }

    public static void setLeaveList(ArrayList<Ajax> leaveList) {
        Global.leaveList = leaveList;
    }

    public static int getTabPosition() {
        return TabPosition;
    }

    public static void setTabPosition(int tabPosition) {
        TabPosition = tabPosition;
    }

    public static String nullChecking(String val) {
        return ((String.valueOf(val).equals("null")) ? "N/A" : String.valueOf(val));
    }
}
