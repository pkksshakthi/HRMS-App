package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.CompanyData;
import com.sphinax.hrms.model.Deduction;
import com.sphinax.hrms.model.Earning;
import com.sphinax.hrms.model.PaymentData;

/**
 * Created by ganesaka on 1/10/2018.
 */

public class PaySlipListAdapter extends BaseAdapter {

    private static final String TAG = "PaySlipListAdapter-";
    private PaymentData detailsList;
    private Context context;
    private int listType;

    public PaySlipListAdapter(Context context, PaymentData list, int listType) {
        this.context = context;
        detailsList = list;
        this.listType = listType;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        if (listType == 0) {
            return detailsList.getAjax().getEarnings().size();

        } else if (listType == 1) {
            return detailsList.getAjax().getDeductions().size();

        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        if (listType == 0) {
            return detailsList.getAjax().getEarnings().get(position);

        } else if (listType == 1) {
            return detailsList.getAjax().getDeductions().get(position);
        }
        return detailsList;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Earning earningDetails = null;
        Deduction deductionetails = null;
        if (listType == 0) {
            earningDetails = (Earning) getItem(position);
        } else if (listType == 1) {
            deductionetails = (Deduction) getItem(position);
        }

        View row = convertView;
        PaySlipListAdapter.OrderDetailHolder holder;
        //holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_pay_slip_item, parent, false);
            holder = new PaySlipListAdapter.OrderDetailHolder();

            holder.tv_list_text = row.findViewById(R.id.tv_list_text);
            holder.tv_list_amt = row.findViewById(R.id.tv_list_amt);

            row.setTag(holder);
        } else {
            holder = (PaySlipListAdapter.OrderDetailHolder) row.getTag();
        }

        if (listType == 0) {
            holder.tv_list_text.setText(earningDetails.getName());
            holder.tv_list_amt.setText(String.valueOf(earningDetails.getAmount()));
        } else if (listType == 1) {
            holder.tv_list_text.setText(deductionetails.getName());
            holder.tv_list_amt.setText(String.valueOf(deductionetails.getAmount()));
        }
        return row;
    }

    static class OrderDetailHolder {
        TextView tv_list_text, tv_list_amt;
    }
}



