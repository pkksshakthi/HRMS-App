package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.util.List;

/**
 * Created by ganesaka on 1/6/2018.
 */

public class LeaveTypeListAdapter extends BaseAdapter {

    private static final String TAG = "LeaveTypeListAdapter-";
    private final List<Ajax> detailsArrayList;
    private final Context context;

    public LeaveTypeListAdapter(Context context, List<Ajax> list) {
        this.context = context;
        detailsArrayList = list;

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
        OrderDetailHolder holder;
        //holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_apply_leave_item, parent, false);
            holder = new OrderDetailHolder();

            holder.bt_leavetype = (Button) row.findViewById(R.id.bt_leave_type);


            row.setTag(holder);
        } else {
            holder = (OrderDetailHolder) row.getTag();
        }
   //     Log.d(TAG + "ID-", bookingTaskDetails.getCategoryId() + "");



        holder.bt_leavetype.setText(bookingTaskDetails.getCompName());


        return row;
    }

    static class OrderDetailHolder {
        Button bt_leavetype;
    }
}
