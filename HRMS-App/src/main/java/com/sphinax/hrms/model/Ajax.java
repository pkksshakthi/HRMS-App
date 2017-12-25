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
