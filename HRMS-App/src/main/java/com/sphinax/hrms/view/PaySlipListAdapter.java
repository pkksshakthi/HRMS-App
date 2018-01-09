package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.util.List;

/**
 * Created by ganesaka on 1/10/2018.
 */

public class PaySlipListAdapter extends BaseAdapter {

    private static final String TAG = "PaySlipListAdapter-";
    private final List<Ajax> detailsArrayList;
    private final Context context;
    private int listType;

    public PaySlipListAdapter(Context context, List<Ajax> list,int listType) {
        this.context = context;
        detailsArrayList = list;
        this.listType = listType;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return detailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return detailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Ajax bookingTaskDetails = (Ajax) getItem(position);
        View row = convertView;
        PaySlipListAdapter.OrderDetailHolder holder;
        //holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_pay_slip_item, parent, false);
            holder = new PaySlipListAdapter.OrderDetailHolder();

            holder.tv_list_text = (TextView) row.findViewById(R.id.tv_list_text);
            holder.tv_list_amt = (TextView) row.findViewById(R.id.tv_list_amt);

            row.setTag(holder);
        } else {
            holder = (PaySlipListAdapter.OrderDetailHolder) row.getTag();
        }
        //     Log.d(TAG + "ID-", bookingTaskDetails.getCategoryId() + "");


        if(listType == 0 ){
            holder.tv_list_text.setText(bookingTaskDetails.getEarningDesc());
            holder.tv_list_amt.setText(String.valueOf(bookingTaskDetails.getEarningAmt()));
        }else if( listType == 1){
            holder.tv_list_text.setText(bookingTaskDetails.getDeductionDesc());
            holder.tv_list_amt.setText(String.valueOf( bookingTaskDetails.getDeductionAmt()));
        }





        return row;
    }

    static class OrderDetailHolder {
        TextView tv_list_text,tv_list_amt,tv_Announcement_3;
    }
}



