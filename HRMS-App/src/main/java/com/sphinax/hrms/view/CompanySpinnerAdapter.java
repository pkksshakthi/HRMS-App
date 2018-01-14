package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sphinax.hrms.model.Ajax;

import java.util.ArrayList;

/**
 * Created by ganesaka on 12/25/2017.
 */

public class CompanySpinnerAdapter extends ArrayAdapter<Ajax> {
    private final Context context;
    private final ArrayList<Ajax> data;
    private final int viewResourceId;
    private final int listViewResourceId;
    private final int spinnerType;

    public CompanySpinnerAdapter(Context context, int textViewResourceId, int listTextViewResourceId, ArrayList<Ajax> objects,int spinnerType) {
        super(context, listTextViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.data = objects;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.viewResourceId = textViewResourceId;
        this.listViewResourceId = listTextViewResourceId;
        this.spinnerType = spinnerType;
    }


    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (viewResourceId == 0) {
            View.inflate(context, android.R.layout.simple_spinner_item, null);
        } else {
            textView = (TextView) View.inflate(context, viewResourceId, null);
            textView.setTextColor(Color.BLACK);
        }
        if (data != null) {
            assert textView != null;
            if(spinnerType == 1){
                textView.setText(data.get(position).getCompName());
            }else if(spinnerType == 2){
                textView.setText(data.get(position).getBranchName());
            } else if(spinnerType == 3 ){
                textView.setText(data.get(position).getDeptName());
            }

        }

        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (listViewResourceId == 0) {
                convertView = vi.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            } else {
                convertView = vi.inflate(listViewResourceId, parent, false);
            }
        }
        TextView textView = ((TextView) convertView);
        textView.setText(data.get(position).getCompName());
        textView.setTextColor(Color.BLACK);

        return convertView;
    }

    private static class ViewHolder {
        public TextView tvText;
    }
}


