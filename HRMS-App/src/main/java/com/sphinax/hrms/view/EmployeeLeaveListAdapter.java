package com.sphinax.hrms.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
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
        public TextView tv_status, tv_date, tv_leave_type, tv_days;
        public ImageView img_status;
       // public TableRow tb_first;

        public MyViewHolder(View view) {
            super(view);
            // tv_name = view.findViewById(R.id.tv_emp_name);
            tv_status = view.findViewById(R.id.tv_status);
            tv_date = view.findViewById(R.id.tv_date);
            tv_leave_type = view.findViewById(R.id.tv_leave_type);
            tv_days = view.findViewById(R.id.tv_days);
            //tb_first = view.findViewById(R.id.tb_index_1);
            img_status = view.findViewById(R.id.iv_user_photo);
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
//        if (position == 0) {
//            holder.tb_first.setVisibility(View.VISIBLE);
//        } else {
//            holder.tb_first.setVisibility(View.GONE);
//        }
        //holder.tv_name.setText(ajax.getEmployeeDescription());
        holder.tv_leave_type.setText(ajax.getLeaveTypeDesc());
        holder.tv_date.setText(ajax.getFromDate());
//        holder.tv_date.setText(String.valueOf(ajax.getFromDate().getDate()) + "/" + String.valueOf(ajax.getFromDate().getMonth())
//                + "/" + String.valueOf(ajax.getFromDate().getYear()));
        if (ajax.getLeaveStatusDesc().equalsIgnoreCase("PENDING")) {
            holder.img_status.setBackgroundResource(R.drawable.pending);
        } else if (ajax.getLeaveStatusDesc().equalsIgnoreCase("APPROVED")) {
            holder.img_status.setBackgroundResource(R.drawable.approved);
        } else {
            holder.img_status.setBackgroundResource(R.drawable.rejected);
        }
//      holder.tv_status.setText(ajax.getLeaveStatusDesc());
        holder.tv_days.setText(String.valueOf(ajax.getNoofdays()));

    }

    @Override
    public int getItemCount() {
        return ajaxList.size();
    }
}
