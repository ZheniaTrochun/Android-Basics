package com.trochun.lab3;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trochun.lab3.databinding.FragmentOutputBinding;

public class OutputFragment extends Fragment {

    private static String ARG_DATA = "data";

    private FragmentOutputBinding binding;
    private DataModel dataModel;

    public static OutputFragment init(DataModel dm) {
        OutputFragment fragment = new OutputFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_DATA, dm);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void addDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        fillViews();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            dataModel = getArguments().getParcelable(ARG_DATA);
        } else {
            dataModel = new DataModel();
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_output, container, false);
        fillViews();
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void fillViews() {
        binding.tvFromTxt.setText("From:" + dataModel.getFrom());
        binding.tvToTxt.setText("To:" + dataModel.getTo());
        binding.tvDeparture.setText("Departure time:" + (dataModel.isMorning() ? "morning" : "evening"));
    }
}
