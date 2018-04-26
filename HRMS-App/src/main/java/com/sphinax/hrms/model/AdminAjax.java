package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class AdminAjax implements Serializable {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("checkInTime")
    @Expose
    private String checkInTime;
    @SerializedName("checkOutTime")
    @Expose
    private String checkOutTime;
    @SerializedName("reportsTo")
    @Expose
    private String reportsTo;
    @SerializedName("empMail")
    @Expose
    private String empMail;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("morning")
    @Expose
    private String morning;
    @SerializedName("evening")
    @Expose
    private String evening;
    @SerializedName("empBranchId")
    @Expose
    private Integer empBranchId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("empImg")
    @Expose
    private String empImg;
    @SerializedName("empCompId")
    @Expose
    private Integer empCompId;
    @SerializedName("empcompName")
    @Expose
    private String empcompName;
    @SerializedName("empBranchName")
    @Expose
    private String empBranchName;
    @SerializedName("empDept")
    @Expose
    private String empDept;
    @SerializedName("empDesignId")
    @Expose
    private Integer empDesignId;
    @SerializedName("empDesign")
    @Expose
    private String empDesign;
    @SerializedName("empMobile")
    @Expose
    private Long empMobile;
    @SerializedName("empDeptId")
    @Expose
    private Integer empDeptId;
    @SerializedName("compId")
    @Expose
    private Integer compId;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("compName")
    @Expose
    private String compName;
    @SerializedName("compImg")
    @Expose
    private String compImg;
//    @SerializedName("branchId")
//    @Expose
//    private Integer branchId;
    @SerializedName("branchName")
    @Expose
    private String branchName;
//    @SerializedName("deptId")
//    @Expose
//    private Integer deptId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("empDesc")
    @Expose
    private String empDesc;
    @SerializedName("New")
    @Expose
    private Integer _new;
    @SerializedName("On Hold")
    @Expose
    private Integer onHold;
    @SerializedName("Need Info")
    @Expose
    private Integer needInfo;
    @SerializedName("In progress")
    @Expose
    private Integer inProgress;
    @SerializedName("Completed")
    @Expose
    private Integer completed;
    @SerializedName("leaveReason")
    @Expose
    private String leaveReason;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("reqId")
    @Expose
    private Integer reqId;
    @SerializedName("reqDesc")
    @Expose
    private String reqDesc;
    @SerializedName("reqDate")
    @Expose
    private String reqDate;
    @SerializedName("responseDate")
    @Expose
    private String responseDate;
    @SerializedName("reqtypeId")
    @Expose
    private Integer reqtypeId;
    @SerializedName("reqTypeDesc")
    @Expose
    private String reqTypeDesc;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("reqStatusId")
    @Expose
    private Integer reqStatusId;
    @SerializedName("reqStatusDesc")
    @Expose
    private String reqStatusDesc;
    @SerializedName("empMsg")
    @Expose
    private String empMsg;
    @SerializedName("reqTypeId")
    @Expose
    private Integer reqTypeId;
    @SerializedName("reqtypedesc")
    @Expose
    private String reqtypedesc;
    @SerializedName("empId")
    @Expose
    private String empId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("designationDesc")
    @Expose
    private String designationDesc;
    @SerializedName("deptDesc")
    @Expose
    private String deptDesc;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("leaveTypeDesc")
    @Expose
    private String leaveTypeDesc;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("leaveStatusDesc")
    @Expose
    private String leaveStatusDesc;
    @SerializedName("leaveId")
    @Expose
    private Integer leaveId;
    @SerializedName("leaveTypeId")
    @Expose
    private Integer leaveTypeId;
    @SerializedName("leaveStatusId")
    @Expose
    private Integer leaveStatusId;
    @SerializedName("sessionId")
    @Expose
    private Integer sessionId;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("sessionDesc")
    @Expose
    private String sessionDesc;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("compShortName")
    @Expose
    private String compShortName;
    @SerializedName("adminOremp")
    @Expose
    private String adminOremp;
    @SerializedName("empImage")
    @Expose
    private String empImage;

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("bioId")
    @Expose
    private String bioId;
    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("activityDate")
    @Expose
    private String activityDate;
    @SerializedName("activityDesc")
    @Expose
    private String activityDesc;
    @SerializedName("announcementList")
    @Expose
    private String announcementList;

    @SerializedName("year")
    @Expose
    private Long year;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("monthValue")
    @Expose
    private Integer monthValue;
//
//    @SerializedName("earnings")
//    @Expose
//    private Long earnings;
//    @SerializedName("netpay")
//    @Expose
//    private Long netpay;
//    @SerializedName("deductions")
//    @Expose
//    private Long deductions;

    @SerializedName("earningDesc")
    @Expose
    private String earningDesc;
    @SerializedName("earningAmt")
    @Expose
    private Long earningAmt;

    @SerializedName("deductionDesc")
    @Expose
    private String deductionDesc;
    @SerializedName("deductionAmt")
    @Expose
    private Long deductionAmt;


    @SerializedName("Leave Type")
    @Expose
    private String leaveType;
    @SerializedName("Leave Apply")
    @Expose
    private Double leaveApply;
    @SerializedName("Leave Balance")
    @Expose
    private Double leaveBalance;
    @SerializedName("Leave Total")
    @Expose
    private Double leaveTotal;

    @SerializedName("Earnings")
    @Expose
    private final List<Earning> earnings = null;
    @SerializedName("deductions")
    @Expose
    private final List<Deduction> deductions = null;
    @SerializedName("total")
    @Expose
    private final List<Total> total = null;
    @SerializedName("Leave Type Id")
    @Expose
    private Integer leaveTypeIds;
    @SerializedName("Leave Type Desc")
    @Expose
    private String leaveTypeDescs;

    @SerializedName("EmployeeDescription")
    @Expose
    private String employeeDescription;

    @SerializedName("noofdays")
    @Expose
    private Double noofdays;
    @SerializedName("FromsessionDesc")
    @Expose
    private String fromsessionDesc;
    @SerializedName("ToSessionDesc")
    @Expose
    private String toSessionDesc;
    @SerializedName("AppliedOn")
    @Expose
    private String appliedOn;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("activityId")
    @Expose
    private Integer activityId;

    @SerializedName("Rejected")
    @Expose
    private Integer Rejected;
    @SerializedName("Approved")
    @Expose
    private Integer Approved;
    @SerializedName("Pending")
    @Expose
    private Integer Pending;


    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRejected() {
        return Rejected;
    }

    public void setRejected(Integer rejected) {
        Rejected = rejected;
    }

    public Integer getApproved() {
        return Approved;
    }

    public void setApproved(Integer approved) {
        Approved = approved;
    }

    public Integer getPending() {
        return Pending;
    }

    public void setPending(Integer pending) {
        Pending = pending;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String remarks) {
        this.remarks = leaveReason;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getFromsessionDesc() {
        return fromsessionDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setFromsessionDesc(String fromsessionDesc) {
//        this.fromsessionDesc = fromsessionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getToSessionDesc() {
        return toSessionDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setToSessionDesc(String toSessionDesc) {
//        this.toSessionDesc = toSessionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getAppliedOn() {
        return appliedOn;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setAppliedOn(String appliedOn) {
//        this.appliedOn = appliedOn;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getRemarks() {
        return remarks;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmployeeDescription() {
        return employeeDescription;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmployeeDescription(String employeeDescription) {
//        this.employeeDescription = employeeDescription;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getFromDate() {
        return fromDate;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setFromDate(String fromDate) {
//        this.fromDate = fromDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getToDate() {
        return toDate;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setToDate(String toDate) {
//        this.toDate = toDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Double getNoofdays() {
        return noofdays;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setNoofdays(Double noofdays) {
//        this.noofdays = noofdays;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Integer getLeaveTypeIds() {
        return leaveTypeIds;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveTypeIds(Integer leaveTypeIds) {
//        this.leaveTypeIds = leaveTypeIds;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getLeaveTypeDescs() {
        return leaveTypeDescs;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveTypeDescs(String leaveTypeDescs) {
//        this.leaveTypeDescs = leaveTypeDescs;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
    public List<Earning> getEarnings() {
        return earnings;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEarnings(List<Earning> earnings) {
//        this.earnings = earnings;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public List<Deduction> getDeductions() {
        return deductions;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDeductions(List<Deduction> deductions) {
//        this.deductions = deductions;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public List<Total> getTotal() {
        return total;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setTotal(List<Total> total) {
//        this.total = total;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getUser() {
//        return user;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setUser(String user) {
//        this.user = user;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getDate() {
//        return date;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDate(String date) {
//        this.date = date;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getMorning() {
        return morning;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setMorning(String morning) {
//        this.morning = morning;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEvening() {
        return evening;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEvening(String evening) {
//        this.evening = evening;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Integer getEmpBranchId() {
        return empBranchId;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpBranchId(Integer empBranchId) {
//        this.empBranchId = empBranchId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
    public String getAddress() {
        return address;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setAddress(String address) {
//        this.address = address;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getLeaveType() {
//        return leaveType;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveType(String leaveType) {
//        this.leaveType = leaveType;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Double getLeaveApply() {
//        return leaveApply;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveApply(Double leaveApply) {
//        this.leaveApply = leaveApply;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Double getLeaveBalance() {
        return leaveBalance;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveBalance(Double leaveBalance) {
//        this.leaveBalance = leaveBalance;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Double getLeaveTotal() {
        return leaveTotal;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveTotal(Double leaveTotal) {
//        this.leaveTotal = leaveTotal;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)


// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getEarningDesc() {
//        return earningDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEarningDesc(String earningDesc) {
//        this.earningDesc = earningDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Long getEarningAmt() {
//        return earningAmt;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEarningAmt(Long earningAmt) {
//        this.earningAmt = earningAmt;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
//    public Long getEarnings() {
//        return earnings;
//    }
//
//    public void setEarnings(Long earnings) {
//        this.earnings = earnings;
//    }
//
//    public Long getNetpay() {
//        return netpay;
//    }
//
//    public void setNetpay(Long netpay) {
//        this.netpay = netpay;
//    }
//
//    public Long getDeductions() {
//        return deductions;
//    }


// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getDeductionDesc() {
//        return deductionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDeductionDesc(String deductionDesc) {
//        this.deductionDesc = deductionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Long getDeductionAmt() {
//        return deductionAmt;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDeductionAmt(Long deductionAmt) {
//        this.deductionAmt = deductionAmt;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
//    public void setDeductions(Long deductions) {
//        this.deductions = deductions;
//    }
    public String getMonth() {
        return month;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setMonth(String month) {
//        this.month = month;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Integer getMonthValue() {
        return monthValue;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setMonthValue(Integer monthValue) {
//        this.monthValue = monthValue;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Long getYear() {
        return year;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setYear(Long year) {
//        this.year = year;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
    public String getActivityDate() {
        return activityDate;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setActivityDate(String activityDate) {
//        this.activityDate = activityDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getActivityDesc() {
        return activityDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setActivityDesc(String activityDesc) {
//        this.activityDesc = activityDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public String getAnnouncementList() {
        return announcementList;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setAnnouncementList(String announcementList) {
        this.announcementList = announcementList;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getEmpCompId() {
//        return empCompId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpCompId(Integer empCompId) {
//        this.empCompId = empCompId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpcompName() {
        return empcompName;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpcompName(String empcompName) {
//        this.empcompName = empcompName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpBranchName() {
        return empBranchName;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpBranchName(String empBranchName) {
//        this.empBranchName = empBranchName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpImg() {
        return empImg;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpImg(String empImg) {
//        this.empImg = empImg;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getEmpDeptId() {
//        return empDeptId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpDeptId(Integer empDeptId) {
//        this.empDeptId = empDeptId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)


// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getEmpDesignId() {
//        return empDesignId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpDesignId(Integer empDesignId) {
//        this.empDesignId = empDesignId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpDesign() {
        return empDesign;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpDesign(String empDesign) {
//        this.empDesign = empDesign;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Long getEmpMobile() {
        return empMobile;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpMobile(Long empMobile) {
//        this.empMobile = empMobile;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpDept() {
        return empDept;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpDept(String empDept) {
//        this.empDept = empDept;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getTime() {
//        return time;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setTime(String time) {
//        this.time = time;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getBioId() {
//        return bioId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setBioId(String bioId) {
//        this.bioId = bioId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getLocation() {
        return location;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLocation(String location) {
//        this.location = location;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getFirstName() {
//        return firstName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getDesignationDesc() {
//        return designationDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDesignationDesc(String designationDesc) {
//        this.designationDesc = designationDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
    public String getCompImg() {
        return compImg;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCompImg(String compImg) {
//        this.compImg = compImg;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getDeptDesc() {
//        return deptDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDeptDesc(String deptDesc) {
//        this.deptDesc = deptDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)



    public String getLeaveTypeDesc() {
        return leaveTypeDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveTypeDesc(String leaveTypeDesc) {
//        this.leaveTypeDesc = leaveTypeDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getDescription() {
//        return description;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setDescription(String description) {
//        this.description = description;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getLeaveStatusDesc() {
        return leaveStatusDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveStatusDesc(String leaveStatusDesc) {
//        this.leaveStatusDesc = leaveStatusDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getLeaveId() {
//        return leaveId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveId(Integer leaveId) {
//        this.leaveId = leaveId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getLeaveTypeId() {
//        return leaveTypeId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveTypeId(Integer leaveTypeId) {
//        this.leaveTypeId = leaveTypeId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getLeaveStatusId() {
//        return leaveStatusId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLeaveStatusId(Integer leaveStatusId) {
//        this.leaveStatusId = leaveStatusId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getSessionId() {
//        return sessionId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setSessionId(Integer sessionId) {
//        this.sessionId = sessionId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getLastName() {
//        return lastName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getSessionDesc() {
//        return sessionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setSessionDesc(String sessionDesc) {
//        this.sessionDesc = sessionDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getCompanyName() {
//        return companyName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCompanyName(String companyName) {
//        this.companyName = companyName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)



// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getEmpMsg() {
//        return empMsg;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpMsg(String empMsg) {
//        this.empMsg = empMsg;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Integer getReqTypeId() {
        return reqTypeId;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqTypeId(Integer reqTypeId) {
//        this.reqTypeId = reqTypeId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getReqtypedesc() {
//        return reqtypedesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqtypedesc(String reqtypedesc) {
//        this.reqtypedesc = reqtypedesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getReqStatusId() {
        return reqStatusId;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setReqStatusId(Integer reqStatusId) {
        this.reqStatusId = reqStatusId;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public String getReqStatusDesc() {
        return reqStatusDesc;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setReqStatusDesc(String reqStatusDesc) {
        this.reqStatusDesc = reqStatusDesc;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpName() {
        return empName;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpName(String empName) {
//        this.empName = empName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public Integer getReqId() {
        return reqId;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqId(Integer reqId) {
//        this.reqId = reqId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public String getReqDesc() {
        return reqDesc;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setReqDesc(String reqDesc) {
        this.reqDesc = reqDesc;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getReqDate() {
        return reqDate;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqDate(String reqDate) {
//        this.reqDate = reqDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getResponseDate() {
//        return responseDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setResponseDate(String responseDate) {
//        this.responseDate = responseDate;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getReqtypeId() {
//        return reqtypeId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqtypeId(Integer reqtypeId) {
//        this.reqtypeId = reqtypeId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getReqTypeDesc() {
        return reqTypeDesc;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReqTypeDesc(String reqTypeDesc) {
//        this.reqTypeDesc = reqTypeDesc;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public Integer getSize() {
//        return size;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setSize(Integer size) {
//        this.size = size;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)


// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getNew() {
        return _new;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setNew(Integer _new) {
        this._new = _new;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getOnHold() {
        return onHold;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setOnHold(Integer onHold) {
        this.onHold = onHold;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getNeedInfo() {
        return needInfo;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setNeedInfo(Integer needInfo) {
        this.needInfo = needInfo;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getInProgress() {
        return inProgress;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setInProgress(Integer inProgress) {
        this.inProgress = inProgress;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public Integer getCompleted() {
        return completed;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
    public void setCompleted(Integer completed) {
        this.completed = completed;
    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpId() {
        return empId;
    }

    public void setEmpId() {
        this.empId = "";
    }

    public String getEmpDesc() {
        return empDesc;
    }

    public void setEmpDesc() {
        this.empDesc = "Select Employee";
    }

//    public Integer getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId() {
//        this.deptId = 0;
//    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName() {
        this.deptName = "Select Department";
    }

    public Integer getCompId() {
        return compId;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCompId(Integer compId) {
//        this.compId = compId;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getShortName() {
        return shortName;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setShortName(String shortName) {
//        this.shortName = shortName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getCompName() {
        return compName;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCompName(String compName) {
//        this.compName = compName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

//    public Integer getBranchId() {
//        return branchId;
//    }
//
//    public void setBranchId() {
//        this.branchId = 0;
//    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName() {
        this.branchName = "Select Branch";
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getCompShortName() {
//        return compShortName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCompShortName(String compShortName) {
//        this.compShortName = compShortName;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getAdminOremp() {
//        return adminOremp;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setAdminOremp(String adminOremp) {
//        this.adminOremp = adminOremp;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getEmpImage() {
//        return empImage;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpImage(String empImage) {
//        this.empImage = empImage;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getCheckInTime() {
        return checkInTime;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCheckInTime(String checkInTime) {
//        this.checkInTime = checkInTime;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getCheckOutTime() {
        return checkOutTime;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setCheckOutTime(String checkOutTime) {
//        this.checkOutTime = checkOutTime;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public String getReportsTo() {
//        return reportsTo;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setReportsTo(String reportsTo) {
//        this.reportsTo = reportsTo;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getEmpMail() {
        return empMail;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setEmpMail(String empMail) {
//        this.empMail = empMail;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)

    public String getStatus() {
        return status;
    }

// --Commented out by Inspection START (3/5/2018 1:07 AM):
//    public void setStatus(String status) {
//        this.status = status;
//    }
// --Commented out by Inspection STOP (3/5/2018 1:07 AM)
}
