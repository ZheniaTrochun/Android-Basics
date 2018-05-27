package com.trochun.lab4;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.trochun.lab4.databinding.ActivityPlayerBinding;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {

    private enum MediaPlayerState {INITIALIZED, PLAYING, PAUSED, STOPPED, ERROR}

    public static String EXTRA_MEDIA_URI = "mediaUri";

    private ActivityPlayerBinding binding;

    private MediaPlayer mediaPlayer;
    private MediaPlayerState mediaPlayerState;
    private Uri mediaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player);

        mediaUri = getIntent().getParcelableExtra(EXTRA_MEDIA_URI);

        initMediaPlayerController();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMediaPlayer();
    }

    @Override
    protected void onPause() {
        releaseMediaPlayer();
        super.onPause();
    }

    private void initMediaPlayer() {
        try {
            SurfaceView svVideo = binding.svVideo;

            mediaPlayer = new MediaPlayer();

            AudioAttributes aa = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            mediaPlayer.setAudioAttributes(aa);

            mediaPlayer.setDataSource(PlayerActivity.this, mediaUri);

            mediaPlayerState = MediaPlayerState.INITIALIZED;

            svVideo.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mediaPlayer.setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mediaPlayer.setDisplay(null);
                }
            });

            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                mediaPlayerState = MediaPlayerState.PLAYING;
            });

            mediaPlayer.setOnVideoSizeChangedListener((mp, width, height) -> {
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(width, height);
                svVideo.setLayoutParams(lp);
                svVideo.requestLayout();
            });

            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                showAlert("Playback Error", "Media playback error occurred");
                mediaPlayerState = MediaPlayerState.ERROR;
                return false;
            });
        } catch (IOException e) {
            showAlert("Initialization Error", "Media player initialization error occurred");
            mediaPlayerState = MediaPlayerState.ERROR;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private void initMediaPlayerController() {
        binding.btnPlay.setOnClickListener(v -> {
            switch (mediaPlayerState) {
                case PAUSED:
                    mediaPlayer.start();
                    mediaPlayerState = MediaPlayerState.PLAYING;
                    break;
                case INITIALIZED:
                case STOPPED:
                    mediaPlayer.prepareAsync();
                    break;
            }
        });

        binding.btnPause.setOnClickListener(v -> {
            switch (mediaPlayerState) {
                case PLAYING:
                    mediaPlayer.pause();
                    mediaPlayerState = MediaPlayerState.PAUSED;
                    break;
            }
        });

        binding.btnStop.setOnClickListener(v -> {
            switch (mediaPlayerState) {
                case PLAYING:
                case PAUSED:
                 mediaPlayer.stop();
                 mediaPlayerState = MediaPlayerState.STOPPED;
                 break;
            }
        });
    }

    private void showAlert(String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("Ok", null);
        alert.show();
    }

    public static class IntentBuilder {

        private Context context;
        private Uri uri;

        private IntentBuilder(Context context) {
            this.context = context;
        }

        public static IntentBuilder create(Context context) {
            return new IntentBuilder(context);
        }

        public IntentBuilder withMediaUri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Intent build() {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra(EXTRA_MEDIA_URI, uri);
            return intent;
        }
    }
}
