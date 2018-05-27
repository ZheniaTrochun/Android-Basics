package com.trochun.lab3;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.trochun.lab3.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements InputFragment.InputCallback {
    private ActivityMainBinding binding;
    private InputFragment inputFragment;
    private OutputFragment outputFragment;
    private SimplePagerAdapter simplePagerAdapter;

    private DataStore dataStore;

    private Disposable saveSubscription;
    private Disposable openSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        inputFragment = new InputFragment();
        outputFragment = new OutputFragment();
        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), inputFragment, outputFragment);
        binding.vpFragmentHolder.setAdapter(simplePagerAdapter);
        dataStore = new FileDataStore(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (openSubscription != null) {
            openSubscription.dispose();
        }
        if (saveSubscription != null) {
            saveSubscription.dispose();
        }
    }

    @Override
    public void onOkClicked(DataModel dataModel) {
        saveSubscription = dataStore.add(dataModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> show(dataModel),
                        error -> {
                            Toast errorToast = Toast.makeText(this, R.string.save_error_message, Toast.LENGTH_LONG);
                            errorToast.show();
                        }
                );
    }

    private void show(DataModel record) {
        outputFragment.addDataModel(record);
        binding.vpFragmentHolder.setCurrentItem(binding.vpFragmentHolder.getCurrentItem() + 1, true);
    }

    @Override
    public void onOpenClicked() {
        openSubscription = dataStore.all()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    this::open,
                    error -> {
                        Toast errorToast = Toast.makeText(this, R.string.open_error_message, Toast.LENGTH_LONG);
                        errorToast.show();
                    }
                );
    }

    private void open(List<DataModel> data) {
        Intent intent = new Intent(this, StoredDataActivity.class);
        intent.putParcelableArrayListExtra(StoredDataActivity.EXTRA_DATA, new ArrayList<>(data));
        startActivity(intent);
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
