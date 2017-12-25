package com.sphinax.hrms.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by ganesaka on 12/24/2017.
 */

public class CompanyData {


    @SerializedName("ajax")
    @Expose
    private List<Ajax> ajax = null;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Pending")
    @Expose
    private Integer pending;
    @SerializedName("Approval")
    @Expose
    private Integer approval;
    @SerializedName("Reject")
    @Expose
    private Integer reject;
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
    @SerializedName("resCode")
    @Expose
    private Integer resCode;
    @SerializedName("resType")
    @Expose
    private String resType;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("compId")
    @Expose
    private String compId;
    @SerializedName("compShortName")
    @Expose
    private String compShortName;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompShortName() {
        return compShortName;
    }

    public void setCompShortName(String compShortName) {
        this.compShortName = compShortName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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
    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

    public Integer getReject() {
        return reject;
    }

    public void setReject(Integer reject) {
        this.reject = reject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public List<Ajax> getAjax() {
        return ajax;
    }

    public void setAjax(List<Ajax> ajax) {
        this.ajax = ajax;
    }

}
