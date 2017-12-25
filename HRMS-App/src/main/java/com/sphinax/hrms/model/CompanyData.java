package com.sphinax.hrms.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by ganesaka on 12/24/2017.
 */

public class CompanyData {

    @SerializedName("resCode")
    @Expose
    private Integer resCode;
    @SerializedName("resType")
    @Expose
    private String resType;
    @SerializedName("ajax")
    @Expose
    private List<Ajax> ajax = null;

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
