package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.util.List;

/**
 * Created by ganesaka on 1/7/2018.
 */

public class QueryListAdapter extends BaseAdapter {

    private static final String TAG = "QueryListAdapter-";
    private final List<Ajax> detailsArrayList;
    private final Context context;

    public QueryListAdapter(Context context, List<Ajax> list) {
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
        QueryListAdapter.OrderDetailHolder holder;
        //holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_enter_hrhelpdesk_item, parent, false);
            holder = new QueryListAdapter.OrderDetailHolder();

            holder.bt_refid = row.findViewById(R.id.bt_refid);
            holder.bt_message = row.findViewById(R.id.bt_message);
            holder.tv_date = row.findViewById(R.id.tv_date);
            holder.tv_query_type = row.findViewById(R.id.tv_query_type);
           // holder.iv_user_image =  row.findViewById(R.id.iv_user_image);


            row.setTag(holder);
        } else {
            holder = (QueryListAdapter.OrderDetailHolder) row.getTag();
        }

        holder.bt_refid.setText(String.valueOf(bookingTaskDetails.getReqId()));
        holder.bt_message.setText(bookingTaskDetails.getReqDesc());
        holder.tv_date.setText(bookingTaskDetails.getReqDate());
        holder.tv_query_type.setText(bookingTaskDetails.getStatus());

        return row;
    }

    static class OrderDetailHolder {
        TextView bt_refid,bt_message;
        TextView tv_query_type,tv_date;
        ImageView iv_user_image;
    }
}

