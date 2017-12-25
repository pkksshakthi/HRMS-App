package com.sphinax.hrms.view;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class CustomSpinnerAdapter extends ArrayAdapter<Ajax> {
    private final Context context;
    private final ArrayList<Ajax> data;
    private final int viewResourceId;
    private final int listViewResourceId;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, int listTextViewResourceId, ArrayList<Ajax> objects) {
        super(context, listTextViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.data = objects;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.viewResourceId = textViewResourceId;
        this.listViewResourceId = listTextViewResourceId;
    }


    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (viewResourceId == 0) {
            View.inflate(context, android.R.layout.simple_spinner_item, null);
        } else {
            textView = (TextView) View.inflate(context, viewResourceId, null);
        }
        if (data != null) {
            assert textView != null;
            textView.setText(data.get(position).getCompName());
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

        return convertView;
    }

    private static class ViewHolder {
        public TextView tvText;
    }
}


