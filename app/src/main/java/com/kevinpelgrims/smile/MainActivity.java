package com.kevinpelgrims.smile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundManager = new SoundManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundManager.playHusky();
    }

    @Override
    protected void onPause() {
        soundManager.stopHusky();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            soundManager.increaseHuskyVolume();
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            soundManager.decreaseHuskyVolume();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
