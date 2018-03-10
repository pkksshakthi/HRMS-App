package com.sphinax.hrms.common.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sphinax.hrms.R;
import com.sphinax.hrms.utils.HRMSNetworkCheck;

/**
 * A simple {@link Fragment} subclass.
 */
public class SomeProblemFragment extends Fragment {

    private static final String TAG = "SomeProblemFragment-";
    private static Context context;
    private View mView;
    private FragmentManager fragmentManager;

    public SomeProblemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_some_problem, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);
        mView = view;
        context = view.getContext();
        fragmentManager = getActivity().getSupportFragmentManager();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!HRMSNetworkCheck.checkInternetConnection(getActivity())) {
           // Utility.callErrorScreen(getActivity(), R.id.content_frame, fragmentManager, new SomeProblemFragment(), false, null, Constants.FRAMENT_ERROR);
            return;
        }


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK);

    }
}
