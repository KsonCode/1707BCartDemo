package com.laoxu.a1707bcartdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragment extends androidx.fragment.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        System.out.println(this+"");
        System.out.println(getContext()+"");
        System.out.println(getActivity()+"");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
