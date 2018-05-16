package com.trochun.lab2;

import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.trochun.lab2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements InputFragment.InputCallback {
    private ActivityMainBinding binding;
    private InputFragment inputFragment;
    private OutputFragment outputFragment;
    private SimplePagerAdapter simplePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        inputFragment = new InputFragment();
        outputFragment = new OutputFragment();
        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), inputFragment, outputFragment);
        binding.vpFragmentHolder.setAdapter(simplePagerAdapter);

    }

    @Override
    public void onOkClicked(DataModel dataModel) {
        outputFragment.addDataModel(dataModel);
        binding.vpFragmentHolder.setCurrentItem(binding.vpFragmentHolder.getCurrentItem() + 1, true);
    }

    @Override
    public void onBackPressed() {
        if (binding.vpFragmentHolder.getCurrentItem() == 1) {
            binding.vpFragmentHolder.setCurrentItem(binding.vpFragmentHolder.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }
}
