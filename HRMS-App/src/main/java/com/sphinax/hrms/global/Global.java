package com.sphinax.hrms.global;

import com.sphinax.hrms.model.Ajax;

import java.util.ArrayList;

/**
 * Created by ganesaka on 1/6/2018.
 */

public class Global {

    private static boolean markAttendance;
    private static ArrayList<Ajax> leaveList;

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
}
