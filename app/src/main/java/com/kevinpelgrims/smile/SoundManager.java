package com.kevinpelgrims.smile;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;

public class SoundManager {
    private static final String TAG = "SoundManager";

    private static final int SOUND_POOL_MAX_STREAMS = 1;
    private static final int SOUND_PRIORITY = 1;
    private static final int SOUND_LOOP_INFINITE = -1;
    private static final int SOUND_LOOP_NONE = 0;
    private static final float SOUND_RATE = 1f;

    private Context context;
    private SoundPool soundPool;
    private float volume = 0.5f;

    private SparseArrayCompat<Boolean> preLoadedSounds = new SparseArrayCompat<>(1);

    private int huskySoundId;
    private int huskyPlayId;

    public SoundManager(Context context) {
        this.context = context;
        initializeSoundPool();
        initializeVolume();
        preLoadSounds();
    }

    private void initializeSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initializeSoundPoolLollipopAndUp();
        }
        else {
            initializeSoundPoolPreLollipop();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initializeSoundPoolLollipopAndUp() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(SOUND_POOL_MAX_STREAMS)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void initializeSoundPoolPreLollipop() {
        soundPool = new SoundPool(SOUND_POOL_MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    private void initializeVolume() {
        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    private void preLoadSounds() {
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (preLoadedSounds.get(sampleId) != null) {
                    preLoadedSounds.remove(sampleId);
                    preLoadedSounds.put(sampleId, true);
                }
            }
        });

        huskySoundId = soundPool.load(context, R.raw.dog_pant, 1);
        huskyPlayId = huskySoundId;
        preLoadedSounds.put(huskySoundId, false);
    }

    private int playSound(int soundId, boolean loop) {
        if (preLoadedSounds.get(soundId)) {
            int loopInteger = loop ? SOUND_LOOP_INFINITE : SOUND_LOOP_NONE;
            return soundPool.play(soundId, volume, volume, SOUND_PRIORITY, loopInteger, SOUND_RATE);
        }
        else {
            // Sound is not loaded yet
        }
        return -1;
    }

    private void stopSound(int playId) {
        soundPool.stop(playId);
    }

    public void playHusky() {
        Log.d(TAG, "Play husky sound");
        int playId = playSound(huskySoundId, true);
        if (playId != -1) {
            huskyPlayId = playId;
        }
        else {
            Log.d(TAG, "Husky sound was not loaded yet");
            // Yes, I know, I risk an infinite loop here. But let's just assume the sound will be loaded soon.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playHusky();
                }
            }, 1000);
        }
    }

    public void stopHusky() {
        Log.d(TAG, "Stop playing husky sound");
        stopSound(huskyPlayId);
    }
}
