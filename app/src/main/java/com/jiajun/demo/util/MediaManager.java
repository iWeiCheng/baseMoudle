package com.jiajun.demo.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

import java.io.FileDescriptor;
import java.io.IOException;

public class MediaManager {
    private MediaPlayer mediaPlayer;
    private boolean isPause;

    public static volatile MediaManager instance;

    public static MediaManager getInstance() {
        if (instance == null) {
            synchronized (MediaManager.class) {
                if (instance == null) {
                    instance = new MediaManager();
                }
            }
        }
        return instance;
    }

    private void playSound(OnCompletionListener onCompletionListener,Runnable r) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    return false;
                }
            });
        } else {
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (onCompletionListener != null) {
                mediaPlayer.setOnCompletionListener(onCompletionListener);
            }
            r.run();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IllegalArgumentException | SecurityException
                | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound(final String filePath, OnCompletionListener onCompletionListener) {
        playSound(onCompletionListener, new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.setDataSource(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void playSound(final FileDescriptor fd, OnCompletionListener onCompletionListener) {
        playSound(onCompletionListener, new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.setDataSource(fd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    public void resume() {
        if (mediaPlayer != null && isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
