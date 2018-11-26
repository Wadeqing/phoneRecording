package com.sinocall.phonerecordera.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinocall.phonerecordera.R;

/**
 * Created by qingchao on 2017/12/18.
 */

public class NotarizationFragment extends FragmentBase {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_notarization, null);
        return view;
    }
}
