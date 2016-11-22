package com.zhengjy.test.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhengjy.test.R;

/**
 * Created by yl1222 on 2016/11/15.
 */

public class ContactLogFragment extends Fragment {
    private static final String TAG = ContactLogFragment.class.getSimpleName();
    private Activity mActivity;

    public static ContactLogFragment getInstance () {
        return new ContactLogFragment();
    }

    public ContactLogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_log, container, false);

        return view;
    }

}
