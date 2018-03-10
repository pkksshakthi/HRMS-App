package com.sphinax.hrms.sample.dummy;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sphinax.hrms.R;


public class CustomToast {

	// Custom Toast Method
	public void Show_Toast(Context context, View view, String error) {

		// Layout Inflater for inflating custom view
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflate the layout over view
		View layout = inflater.inflate(R.layout.custom_toast, view.findViewById(R.id.toast_root));

		// Get TextView id and set error
		TextView text = layout.findViewById(R.id.toast_error);
		text.setText(error);

		Toast toast = new Toast(context);// Get Toast Context
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
																		// Toast
																		// gravity
																		// and
																		// Fill
																		// Horizoontal

		toast.setDuration(Toast.LENGTH_SHORT);// Set Duration
		toast.setView(layout); // Set Custom View over toast

		toast.show();// Finally show toast
	}

}
