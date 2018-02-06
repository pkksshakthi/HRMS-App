package com.sphinax.hrms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ganesaka on 2/6/2018.
 */

public class Total implements Serializable {

    @SerializedName("earnings")
    @Expose
    private Long earnings;
    @SerializedName("netpay")
    @Expose
    private Long netpay;
    @SerializedName("deductions")
    @Expose
    private Long deductions;

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


}
