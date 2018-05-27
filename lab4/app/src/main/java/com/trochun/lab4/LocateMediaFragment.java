package com.trochun.lab4;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.trochun.lab4.databinding.FragmentLocateMediaBinding;

import java.util.HashMap;
import java.util.Map;

public class LocateMediaFragment extends Fragment {

    private static class MediaTypeAttributes {

        private final String typeFilter;
        private final int sampleResId;

        public MediaTypeAttributes(String typeFilter, @RawRes int sampleResId) {
            this.typeFilter = typeFilter;
            this.sampleResId = sampleResId;
        }

        public String getTypeFilter() {
            return typeFilter;
        }

        public int getSampleResId() {
            return sampleResId;
        }
    }

    private static String ARG_MEDIA_TYPE = "mediaType";

    private static Map<MediaType, MediaTypeAttributes> MEDIA_TYPE_ATTRIBUTES = createMediaTypeAttributes();
    private static Map<MediaType, MediaTypeAttributes> createMediaTypeAttributes() {
        Map<MediaType, MediaTypeAttributes> ma = new HashMap<>();
        ma.put(
                MediaType.AUDIO,
                new MediaTypeAttributes(
                        "audio/*",
                        R.raw.audio_sample
                )
        );
        ma.put(
                MediaType.VIDEO,
                new MediaTypeAttributes(
                        "video/*",
                        R.raw.video_sample
                )
        );
        return ma;
    }

    private static int REQUEST_OPEN_LOCAL = 1000;

    private FragmentLocateMediaBinding binding;

    private LocateMediaCallback locateMediaCallback;

    private MediaType mediaType;

    public static LocateMediaFragment init(MediaType mediaType) {
        LocateMediaFragment fragment = new LocateMediaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MEDIA_TYPE, mediaType.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locate_media, container, false);

        mediaType = MediaType.valueOf(getArguments().getString(ARG_MEDIA_TYPE));

        initUI();

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locateMediaCallback = (LocateMediaCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locateMediaCallback = null;
    }

    private void initUI() {
        binding.itemSample.setOnClickListener(v -> playSample());
        binding.itemLocal.setOnClickListener(v -> playLocal());
        binding.itemUrl.setOnClickListener(v -> playUrl());
    }

    private void playSample() {
        int resId = MEDIA_TYPE_ATTRIBUTES.get(mediaType).getSampleResId();
        locateMediaCallback.onMediaLocated(getRawUri(resId), mediaType);
    }

    public Uri getRawUri(@RawRes int resId) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getContext().getPackageName() + "/raw/" + resId);
    }

    private void playLocal() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(MEDIA_TYPE_ATTRIBUTES.get(mediaType).getTypeFilter());
        startActivityForResult(intent, REQUEST_OPEN_LOCAL);
    }

    private void playUrl() {
        EditText etInput = new EditText(getContext());
        etInput.setText("http://");
        new AlertDialog.Builder(getContext())
                .setTitle("Enter Media URL")
                .setView(etInput)
                .setPositiveButton("Ok", (dialog, whichButton) -> {
                    String url = etInput.getText().toString();
                    Uri uri = Uri.parse(url);
                    onMediaLocated(uri);
                })
                .setNegativeButton("Cancel", (dialog, whichButton) -> dialog.cancel())
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OPEN_LOCAL && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                onMediaLocated(uri);
            }
        }
    }

    private void onMediaLocated(Uri uri) {
        if (locateMediaCallback != null) {
            locateMediaCallback.onMediaLocated(uri, mediaType);
        }
    }

    public interface LocateMediaCallback {
        void onMediaLocated(Uri uri, MediaType mediaType);
    }

}
