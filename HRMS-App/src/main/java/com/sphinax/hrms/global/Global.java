package com.sphinax.hrms.global;

/**
 * Created by ganesaka on 1/6/2018.
 */

public class Global {

    private static boolean markAttendance;

    public static boolean isMarkAttendance() {
        return markAttendance;
    }

    public static void setMarkAttendance(boolean markAttendance) {
        Global.markAttendance = markAttendance;
    }
}
