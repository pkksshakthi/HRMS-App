package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sphinax.hrms.R;
import com.sphinax.hrms.model.Ajax;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ganesaka on 1/7/2018.
 */

public class AnnouncementListAdapter extends BaseAdapter {

    private static final String TAG = "AnnouncementListAdapter-";
    private final List<Ajax> detailsArrayList;
    private final Context context;

    public AnnouncementListAdapter(Context context, List<Ajax> list) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Ajax bookingTaskDetails = (Ajax) getItem(position);
        View row = convertView;
        AnnouncementListAdapter.OrderDetailHolder holder;
        //holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.fragment_announcement_list_item, parent, false);
            holder = new AnnouncementListAdapter.OrderDetailHolder();

            holder.tv_Announcement_1 = (TextView) row.findViewById(R.id.tv_Announ_1);
            holder.date = (TextView) row.findViewById(R.id.tv_date);
            holder.month = (TextView) row.findViewById(R.id.tv_month);
            holder.year = (TextView) row.findViewById(R.id.tv_year);
            holder.tv_Announcement_3 = (TextView) row.findViewById(R.id.tv_Announ_3);

            row.setTag(holder);
        } else {
            holder = (AnnouncementListAdapter.OrderDetailHolder) row.getTag();
        }
        //     Log.d(TAG + "ID-", bookingTaskDetails.getCategoryId() + "");



        holder.tv_Announcement_1.setText("HRMS Added New Function");
       // holder.tv_Announcement_2.setText("Posted on :" + bookingTaskDetails.getActivityDate());
         holder.tv_Announcement_3.setText(bookingTaskDetails.getActivityDesc());
//        holder.tv_query_type.setText(bookingTaskDetails.getReqtypedesc());
        if (bookingTaskDetails.getActivityDate().contains("/")){

            String[] date1 = bookingTaskDetails.getActivityDate().split("/");
            holder.date.setText(date1[0]);

            holder.month.setText(Month.of(Integer.parseInt(date1[1])).name());
            holder.year.setText(date1[2]);
        }





        return row;
    }

    static class OrderDetailHolder {
        TextView tv_Announcement_1,tv_Announcement_3;
        public TextView date;
        public TextView month;
        public TextView year;
    }
}


