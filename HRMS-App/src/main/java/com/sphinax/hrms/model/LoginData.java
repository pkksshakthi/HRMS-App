package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ganesaka on 1/6/2018.
 */

public class LoginData {

    @SerializedName("markAttendacne")
    @Expose
    private String markAttendacne;

    @SerializedName("deptId")
    @Expose
    private Integer deptId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("biometricId")
    @Expose
    private Integer biometricId;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("reportsTo")
    @Expose
    private String reportsTo;

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
    @SerializedName("adminOremp")
    @Expose
    private String adminOremp;
    @SerializedName("empName")
    @Expose
    private String empName;
    @SerializedName("empImage")
    @Expose
    private String empImage;

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

    public String getAdminOremp() {
        return adminOremp;
    }

    public void setAdminOremp(String adminOremp) {
        this.adminOremp = adminOremp;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
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

    public Integer getBiometricId() {
        return biometricId;
    }

    public void setBiometricId(Integer biometricId) {
        this.biometricId = biometricId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getMarkAttendacne() {
        return markAttendacne;
    }

    public void setMarkAttendacne(String markAttendacne) {
        this.markAttendacne = markAttendacne;
    }
}
