package com.trochun.lab2;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trochun.lab2.databinding.FragmentInputBinding;

public class InputFragment extends Fragment {

    private DataModel dataModel;
    private FragmentInputBinding binding;
    private InputCallback inputCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false);
        dataModel = new DataModel();
        initListeners();

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inputCallback = (InputCallback) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        inputCallback = null;
    }

    private void initListeners() {
        binding.rbMorning.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                dataModel.setMorning(true);
            }
            binding.rbEvening.setChecked(!b);
        });
        binding.rbEvening.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                dataModel.setMorning(false);
            }
            binding.rbMorning.setChecked(!b);
        });
        binding.btnOk.setOnClickListener(v -> {
            if (notNull(binding.etFrom.getText().toString())
                    && notNull(binding.etTo.getText().toString())
                    && (binding.rbEvening.isChecked() || binding.rbMorning.isChecked())) {
                dataModel.setFrom(binding.etFrom.getText().toString());
                dataModel.setTo(binding.etTo.getText().toString());
                inputCallback.onOkClicked(dataModel);
            }
        });
    }

    private static boolean notNull(String string) {
        return string != null && string.length() > 0 && string.replaceAll("\\s", "").length() > 0;
    }

    public interface InputCallback {
        void onOkClicked(DataModel dataModel);
    }
}
