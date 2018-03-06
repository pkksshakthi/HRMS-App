package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ganesaka on 12/25/2017.
 */

public class FromDate {

    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("hours")
    @Expose
    private Integer hours;
    @SerializedName("minutes")
    @Expose
    private Integer minutes;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("nanos")
    @Expose
    private Integer nanos;
    @SerializedName("seconds")
    @Expose
    private Integer seconds;
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("timezoneOffset")
    @Expose
    private Long timezoneOffset;
    @SerializedName("year")
    @Expose
    private Integer year;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

// --Commented out by Inspection START (3/5/2018 1:10 AM):
//    public Integer getDay() {
//        return day;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:10 AM)

    public void setDay(Integer day) {
        this.day = day;
    }

// --Commented out by Inspection START (3/5/2018 1:11 AM):
//    public Integer getHours() {
//        return hours;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:11 AM)

    public void setHours(Integer hours) {
        this.hours = hours;
    }

// --Commented out by Inspection START (3/5/2018 1:11 AM):
//    public Integer getMinutes() {
//        return minutes;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:11 AM)

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getNanos() {
        return nanos;
    }

    public void setNanos(Integer nanos) {
        this.nanos = nanos;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(Long timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
