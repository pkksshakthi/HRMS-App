package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class Ajax implements Serializable {



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
    @SerializedName("branchId")
    @Expose
    private Integer branchId;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("deptId")
    @Expose
    private Integer deptId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("empDesc")
    @Expose
    private String empDesc;
    @SerializedName("New")
    @Expose
    private Integer _new;
    @SerializedName("OnHold")
    @Expose
    private Integer onHold;
    @SerializedName("NeedInfo")
    @Expose
    private Integer needInfo;
    @SerializedName("InProgress")
    @Expose
    private Integer inProgress;
    @SerializedName("completed")
    @Expose
    private Integer completed;
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
    private FromDate fromDate;
    @SerializedName("toDate")
    @Expose
    private ToDate toDate;
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

    @SerializedName("earnings")
    @Expose
    private Long earnings;
    @SerializedName("netpay")
    @Expose
    private Long netpay;
    @SerializedName("deductions")
    @Expose
    private Long deductions;

    @SerializedName("earningDesc")
    @Expose
    private String earningDesc;
    @SerializedName("earningAmt")
    @Expose
    private Long earningAmt;

    public String getEarningDesc() {
        return earningDesc;
    }

    public void setEarningDesc(String earningDesc) {
        this.earningDesc = earningDesc;
    }

    public Long getEarningAmt() {
        return earningAmt;
    }

    public void setEarningAmt(Long earningAmt) {
        this.earningAmt = earningAmt;
    }
    public Long getEarnings() {
        return earnings;
    }

    public void setEarnings(Long earnings) {
        this.earnings = earnings;
    }

    public Long getNetpay() {
        return netpay;
    }

    public void setNetpay(Long netpay) {
        this.netpay = netpay;
    }

    public Long getDeductions() {
        return deductions;
    }

    @SerializedName("deductionDesc")
    @Expose
    private String deductionDesc;
    @SerializedName("deductionAmt")
    @Expose
    private Long deductionAmt;

    public String getDeductionDesc() {
        return deductionDesc;
    }

    public void setDeductionDesc(String deductionDesc) {
        this.deductionDesc = deductionDesc;
    }

    public Long getDeductionAmt() {
        return deductionAmt;
    }

    public void setDeductionAmt(Long deductionAmt) {
        this.deductionAmt = deductionAmt;
    }
    public void setDeductions(Long deductions) {
        this.deductions = deductions;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(Integer monthValue) {
        this.monthValue = monthValue;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(String announcementList) {
        this.announcementList = announcementList;
    }

    public Integer getEmpCompId() {
        return empCompId;
    }

    public void setEmpCompId(Integer empCompId) {
        this.empCompId = empCompId;
    }

    public String getEmpcompName() {
        return empcompName;
    }

    public void setEmpcompName(String empcompName) {
        this.empcompName = empcompName;
    }

    public String getEmpBranchName() {
        return empBranchName;
    }

    public void setEmpBranchName(String empBranchName) {
        this.empBranchName = empBranchName;
    }

    public String getEmpImg() {
        return empImg;
    }

    public void setEmpImg(String empImg) {
        this.empImg = empImg;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }


    public Integer getEmpDesignId() {
        return empDesignId;
    }

    public void setEmpDesignId(Integer empDesignId) {
        this.empDesignId = empDesignId;
    }

    public String getEmpDesign() {
        return empDesign;
    }

    public void setEmpDesign(String empDesign) {
        this.empDesign = empDesign;
    }

    public Long getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(Long empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBioId() {
        return bioId;
    }

    public void setBioId(String bioId) {
        this.bioId = bioId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDesignationDesc() {
        return designationDesc;
    }

    public void setDesignationDesc(String designationDesc) {
        this.designationDesc = designationDesc;
    }
    public String getCompImg() {
        return compImg;
    }

    public void setCompImg(String compImg) {
        this.compImg = compImg;
    }
    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public FromDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(FromDate fromDate) {
        this.fromDate = fromDate;
    }

    public ToDate getToDate() {
        return toDate;
    }

    public void setToDate(ToDate toDate) {
        this.toDate = toDate;
    }

    public String getLeaveTypeDesc() {
        return leaveTypeDesc;
    }

    public void setLeaveTypeDesc(String leaveTypeDesc) {
        this.leaveTypeDesc = leaveTypeDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeaveStatusDesc() {
        return leaveStatusDesc;
    }

    public void setLeaveStatusDesc(String leaveStatusDesc) {
        this.leaveStatusDesc = leaveStatusDesc;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Integer leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Integer getLeaveStatusId() {
        return leaveStatusId;
    }

    public void setLeaveStatusId(Integer leaveStatusId) {
        this.leaveStatusId = leaveStatusId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSessionDesc() {
        return sessionDesc;
    }

    public void setSessionDesc(String sessionDesc) {
        this.sessionDesc = sessionDesc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    public String getEmpMsg() {
        return empMsg;
    }

    public void setEmpMsg(String empMsg) {
        this.empMsg = empMsg;
    }

    public Integer getReqTypeId() {
        return reqTypeId;
    }

    public void setReqTypeId(Integer reqTypeId) {
        this.reqTypeId = reqTypeId;
    }

    public String getReqtypedesc() {
        return reqtypedesc;
    }

    public void setReqtypedesc(String reqtypedesc) {
        this.reqtypedesc = reqtypedesc;
    }
    public Integer getReqStatusId() {
        return reqStatusId;
    }

    public void setReqStatusId(Integer reqStatusId) {
        this.reqStatusId = reqStatusId;
    }

    public String getReqStatusDesc() {
        return reqStatusDesc;
    }

    public void setReqStatusDesc(String reqStatusDesc) {
        this.reqStatusDesc = reqStatusDesc;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public String getReqDesc() {
        return reqDesc;
    }

    public void setReqDesc(String reqDesc) {
        this.reqDesc = reqDesc;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public Integer getReqtypeId() {
        return reqtypeId;
    }

    public void setReqtypeId(Integer reqtypeId) {
        this.reqtypeId = reqtypeId;
    }

    public String getReqTypeDesc() {
        return reqTypeDesc;
    }

    public void setReqTypeDesc(String reqTypeDesc) {
        this.reqTypeDesc = reqTypeDesc;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public Integer getNew() {
        return _new;
    }

    public void setNew(Integer _new) {
        this._new = _new;
    }

    public Integer getOnHold() {
        return onHold;
    }

    public void setOnHold(Integer onHold) {
        this.onHold = onHold;
    }

    public Integer getNeedInfo() {
        return needInfo;
    }

    public void setNeedInfo(Integer needInfo) {
        this.needInfo = needInfo;
    }

    public Integer getInProgress() {
        return inProgress;
    }

    public void setInProgress(Integer inProgress) {
        this.inProgress = inProgress;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpDesc() {
        return empDesc;
    }

    public void setEmpDesc(String empDesc) {
        this.empDesc = empDesc;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCompShortName() {
        return compShortName;
    }

    public void setCompShortName(String compShortName) {
        this.compShortName = compShortName;
    }

    public String getAdminOremp() {
        return adminOremp;
    }

    public void setAdminOremp(String adminOremp) {
        this.adminOremp = adminOremp;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }
}
