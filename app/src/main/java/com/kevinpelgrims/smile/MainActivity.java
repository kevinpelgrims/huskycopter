package com.kevinpelgrims.smile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl("http://img-9gag-ftw.9cache.com/photo/a9M0NVL_460sa.gif");
        //TODO:
        // http://stackoverflow.com/questions/9494413/play-downloaded-gif-image-in-android
        // http://weavora.com/blog/2012/02/07/android-and-how-to-use-animated-gifs/
    }
}
