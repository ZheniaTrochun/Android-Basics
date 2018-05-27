package com.trochun.lab4;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trochun.lab4.SimplePagerAdapter.Page;
import com.trochun.lab4.SimplePagerAdapter.SimplePage;
import com.trochun.lab4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LocateMediaFragment.LocateMediaCallback {

    private ActivityMainBinding binding;

    private SimplePagerAdapter simplePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Page audioPage = new SimplePage(
                LocateMediaFragment.init(MediaType.AUDIO),
                "Audio"
        );
        Page videoPage = new SimplePage(
                LocateMediaFragment.init(MediaType.VIDEO),
                "Video"
        );

        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), audioPage, videoPage);
        binding.vpMediaTypes.setAdapter(simplePagerAdapter);
        binding.tlMediaType.setupWithViewPager(binding.vpMediaTypes);
    }

    @Override
    public void onMediaLocated(Uri uri, MediaType mediaType) {
        startActivity(
                PlayerActivity.IntentBuilder.create(this)
                        .withMediaUri(uri)
                        .build()
        );
    }
}
