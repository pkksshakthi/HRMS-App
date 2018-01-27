package com.sphinax.hrms.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.util.List;

/**
 * Created by ganesaka on 1/14/2018.
 */

public class EmployeeLeaveListAdapter extends RecyclerView.Adapter<EmployeeLeaveListAdapter.MyViewHolder> {

    private List<Ajax> ajaxList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_status, tv_date, tv_leave_type;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_emp_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_leave_type = (TextView) view.findViewById(R.id.tv_leave_type);
        }
    }


    public EmployeeLeaveListAdapter(List<Ajax> ajaxList) {
        this.ajaxList = ajaxList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_approve_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Ajax ajax = ajaxList.get(position);

        holder.tv_name.setText(ajax.getDescription());
        holder.tv_status.setText(ajax.getLeaveStatusDesc());
        holder.tv_date.setText(String.valueOf(ajax.getFromDate().getDate()) + "/" + String.valueOf(ajax.getFromDate().getMonth())
                + "/" + String.valueOf(ajax.getFromDate().getYear()));
        holder.tv_leave_type.setText(ajax.getLeaveTypeDesc());

    }

    @Override
    public int getItemCount() {
        return ajaxList.size();
    }
}
