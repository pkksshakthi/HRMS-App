package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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


public class LeaveTypeListAdapter extends RecyclerView.Adapter<LeaveTypeListAdapter.MyViewHolder> {

    private static final String TAG = "LeaveTypeListAdapter-";
    private  List<Ajax> detailsArrayList;
    private  Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  bt_leavetype;;
        public TextView  tv_bal;;

        public MyViewHolder(View view) {
            super(view);
            bt_leavetype =  view.findViewById(R.id.bt_leave_type);
            tv_bal =  view.findViewById(R.id.tv_bal);
        }
    }


    public LeaveTypeListAdapter(Context context,List<Ajax> ajaxList) {
        this.detailsArrayList = ajaxList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_apply_leave_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ajax ajax = detailsArrayList.get(position);

        holder.bt_leavetype.setText(ajax.getLeaveTypeDescs());
        holder.tv_bal.setText(String.valueOf(ajax.getLeaveBalance()) +  " / " + String.valueOf(ajax.getLeaveTotal()));
        }

    @Override
    public int getItemCount() {
        return detailsArrayList.size();
    }
}
