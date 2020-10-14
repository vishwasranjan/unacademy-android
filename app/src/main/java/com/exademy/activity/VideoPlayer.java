package com.exademy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.FitWindowsFrameLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Config;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.exademy.R;

public class VideoPlayer extends AppCompatActivity {

    VideoView videoView;
    LottieAnimationView lottieAnimationView;
    LinearLayout ll_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

//        hiding statusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toast.makeText(getApplicationContext(),"please wait your video is loading",Toast.LENGTH_LONG).show();

        videoView = (VideoView)findViewById(R.id.videoolayer);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.videoloading);
        ll_video = findViewById(R.id.ll_video);

        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                lottieAnimationView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"rotate your phone to get fullscreen view",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           ll_video.setVisibility(View.INVISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
           ll_video.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Switch to landscape for full screen", Toast.LENGTH_SHORT).show();
        }
    }

//    demoImage.getLayoutParams().height = 150;
//
//demoImage.getLayoutParams().width = 150;
//
//demoImage.setScaleType(ImageView.ScaleType.FIT_XY);

}