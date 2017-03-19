package com.edfora.sunny.edfora;

import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edfora.sunny.edfora.models.Track;
import com.edfora.sunny.edfora.utils.ImageUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.tv_track_title)
    TextView tvTrackTitle;
    @BindView(R.id.tv_track_details)
    TextView tvTrackDetails;
    @BindView(R.id.iv_track_previous)
    ImageView ivTrackPrevious;
    @BindView(R.id.iv_track_play)
    ImageView ivTrackPlay;
    @BindView(R.id.iv_track_next)
    ImageView ivTrackNext;
    @BindView(R.id.ll_player_controls)
    LinearLayout llPlayerControls;
    @BindView(R.id.iv_track_image)
    ImageView ivTrackImage;
    boolean isPlaying = false;
    Track track;
    private MediaPlayer mediaPlayer;
    private int position;
    private List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        tracks = TrackRepository.getInstance().getTracks();
        if (null != getIntent()) {
            position = getIntent().getIntExtra(EdforaConstans.EXTRA_TRACK, 0);
            setTrack(position);
        }


    }

    private void setTrack(int position) {
        track = tracks.get(position);
        ImageUtils.loadImage(this, ivTrackImage, track.getCoverImage());
        ObjectAnimator rotation = ObjectAnimator.ofFloat(ivTrackImage, "rotation", 0f, 360f);
        rotation.setDuration(2000);
        rotation.setStartDelay(200);
        rotation.start();
        tvTrackTitle.setText(track.getSong());
        tvTrackDetails.setText(track.getArtists());
    }

    @OnClick({R.id.iv_track_play})
    public void playSong() {
        if (!isPlaying) {
            try {
                startPlaying();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            pausePlaying();
        }
    }


    @OnClick({R.id.iv_track_previous})
    public void previousSong() {
        previousTrack();
    }

    @OnClick({R.id.iv_track_next})
    public void nextSong() {
        nextTrack();
    }


    private void nextTrack() {
        if (position < tracks.size()) {
            setTrack(++position);
            changeTrack();
        }

    }

    private void previousTrack() {
        if (position > 0) {
            setTrack(--position);
            changeTrack();
        }

    }

    private void startPlaying() throws IOException {

        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(track.getUrl());
            mediaPlayer.prepare();
        }
        mediaPlayer.start();
        isPlaying = true;
        ivTrackPlay.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMediaPlayer();
    }

    private void changeTrack() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(track.getUrl());
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopMediaPlayer() {
        if (null != mediaPlayer) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void pausePlaying() {
        if (null != mediaPlayer) {
            mediaPlayer.pause();
            ivTrackPlay.setImageResource(android.R.drawable.ic_media_play);
            isPlaying = false;
        }
    }

}
