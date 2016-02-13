package com.kevinpelgrims.smile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
}
