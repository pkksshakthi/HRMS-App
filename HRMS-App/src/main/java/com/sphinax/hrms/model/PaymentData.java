package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ganesaka on 2/6/2018.
 */

public class PaymentData implements Serializable {

    @SerializedName("resCode")
    @Expose
    private Integer resCode;
    @SerializedName("resType")
    @Expose
    private String resType;
    @SerializedName("ajax")
    @Expose
    private Ajax ajax;

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

    public Ajax getAjax() {
        return ajax;
    }

    public void setAjax(Ajax ajax) {
        this.ajax = ajax;
    }

}
