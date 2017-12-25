package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ganesaka on 12/24/2017.
 */

public class Ajax {

    @SerializedName("compId")
    @Expose
    private Integer compId;
    @SerializedName("shortName")
    @Expose
    private String shortName;
    @SerializedName("compName")
    @Expose
    private String compName;
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
}
