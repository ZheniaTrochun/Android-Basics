package com.trochun.lab3;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trochun.lab3.databinding.ActivityStoredDataBinding;

import java.util.Collections;
import java.util.List;

public class StoredDataActivity extends AppCompatActivity {

    public static String EXTRA_DATA = "data";

    private ActivityStoredDataBinding binding;
    private SimplePagerAdapter simplePagerAdapter;

    private List<DataModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stored_data);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DATA)) {
            data = intent.getParcelableArrayListExtra(EXTRA_DATA);
        } else {
            data = Collections.emptyList();
        }

        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), createOutputFragments(data));
        binding.vpFragmentHolder.setAdapter(simplePagerAdapter);
    }

    private OutputFragment[] createOutputFragments(List<DataModel> data) {
        OutputFragment[] fragments = new OutputFragment[data.size()];
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = OutputFragment.init(data.get(i));
        }
        return fragments;
    }
}
