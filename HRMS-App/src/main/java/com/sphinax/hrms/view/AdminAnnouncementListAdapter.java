package com.sphinax.hrms.view;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormatSymbols;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.AdminAjax;
import com.sphinax.hrms.model.Ajax;

import java.time.Month;
import java.util.List;

/**
 * Created by ganesaka on 1/14/2018.
 */

public class AdminAnnouncementListAdapter extends RecyclerView.Adapter<AdminAnnouncementListAdapter.MyViewHolder> {

    private final List<AdminAjax> ajaxList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Announcement_1,tv_Announcement_3;
        public TextView date;
        public TextView month;
        public TextView year;
        public Button bt_delete;

       // public TableRow tb_first;

        public MyViewHolder(View view) {
            super(view);

            tv_Announcement_1 = view.findViewById(R.id.tv_Announ_1);
            date = view.findViewById(R.id.tv_date);
            month = view.findViewById(R.id.tv_month);
            year = view.findViewById(R.id.tv_year);
            bt_delete = view.findViewById(R.id.bt_delete);
            tv_Announcement_3 = view.findViewById(R.id.tv_Announ_3);

        }
    }


    public AdminAnnouncementListAdapter(List<AdminAjax> ajaxList) {
        this.ajaxList = ajaxList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_admin_announcement_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdminAjax ajax = ajaxList.get(position);

        holder.tv_Announcement_1.setText(ajax.getAnnouncementList());
        holder.tv_Announcement_3.setText(ajax.getActivityDesc());
        if (ajax.getActivityDate().contains("/")){
            String[] date1 = ajax.getActivityDate().split("/");
            holder.date.setText(date1[0]);

            holder.month.setText(String.valueOf(new DateFormatSymbols().getMonths()[Integer.parseInt(date1[1])-1]));
//            holder.month.setText(String.valueOf(Month.of(Integer.parseInt(date1[1])).name()));
            holder.year.setText(date1[2]);
        }

        holder.bt_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // ((RecyclerView) Activity).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
            }
        });


    }

    @Override
    public int getItemCount() {
        return ajaxList.size();
    }
}
