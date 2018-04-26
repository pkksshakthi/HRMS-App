package com.sphinax.hrms.view;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by ganesaka on 4/26/2018.
 */

public class AdminLeaveAdapter extends RecyclerView.Adapter<AdminLeaveAdapter.MyViewHolder> {

    private final List<Ajax> ajaxList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public  TextView tv_date;
        public  TextView tv_month;
        public  TextView tv_ref;
        public  TextView tv_querytype;
        public  ImageView img_user;
        // public TableRow tb_first;

        public MyViewHolder(View view) {
            super(view);
            // tv_name = view.findViewById(R.id.tv_emp_name);
            tv_name = view.findViewById(R.id.tv_name);
            tv_date = view.findViewById(R.id.tv_date);
            tv_month = view.findViewById(R.id.tv_month);
            tv_ref = view.findViewById(R.id.tv_refid);
            //tb_first = view.findViewById(R.id.tb_index_1);
            tv_querytype = view.findViewById(R.id.tv_query_type);
            img_user = view.findViewById(R.id.iv_user_photo);
        }
    }


    public AdminLeaveAdapter(List<Ajax> ajaxList) {
        this.ajaxList = ajaxList;
    }

    @Override
    public AdminLeaveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_admin_leave_list__item, parent, false);

        return new AdminLeaveAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(AdminLeaveAdapter.MyViewHolder holder, int position) {
        Ajax ajax = ajaxList.get(position);

        holder.tv_name.setText(ajax.getEmpName());

        if (ajax.getAppliedOn().contains("/")){

            String[] date1 = ajax.getAppliedOn().split("/");
            holder.tv_date.setText(date1[0]);
            holder.tv_month.setText(getMonthForInt(Integer.parseInt(date1[1]) - 1));

           // holder.tv_month.setText(String.valueOf(Month.of(Integer.parseInt(date1[1])).name()));
        }


        holder.tv_querytype.setText(ajax.getLeaveReason());
        holder.tv_ref.setText(String.valueOf(ajax.getNoofdays()));

    }

    @Override
    public int getItemCount() {
        return ajaxList.size();
    }

    String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month;
    }
}
