package com.tidemoon.daughter;

import android.app.Activity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;

public final class MainActivity extends Activity {
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(6, 27, 43));
        getWindow().setNavigationBarColor(Color.rgb(6, 27, 43));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(6, 27, 43));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String target = request.getUrl().toString();
                return !target.startsWith("file:///android_asset/");
            }
        });
        webView.addJavascriptInterface(new SaveBridge(), "MermaidBridge");
        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

    private final class SaveBridge {
        @JavascriptInterface
        public String loadV6() {
            return getSharedPreferences("daughter_save", MODE_PRIVATE).getString("state_chapter1_v06", "");
        }

        @JavascriptInterface
        public void saveV6(String data) {
            if (data != null && data.length() <= 250000) {
                getSharedPreferences("daughter_save", MODE_PRIVATE)
                    .edit().putString("state_chapter1_v06", data).apply();
            }
        }

        @JavascriptInterface
        public String loadV5() {
            return getSharedPreferences("daughter_save", MODE_PRIVATE).getString("state_chapter1_v05", "");
        }

        @JavascriptInterface
        public void saveV5(String data) {
            if (data != null && data.length() <= 100000) {
                getSharedPreferences("daughter_save", MODE_PRIVATE)
                    .edit().putString("state_chapter1_v05", data).apply();
            }
        }

        @JavascriptInterface
        public String load() {
            return getSharedPreferences("daughter_save", MODE_PRIVATE).getString("state", "");
        }

        @JavascriptInterface
        public void save(String data) {
            if (data != null && data.length() <= 100000) {
                getSharedPreferences("daughter_save", MODE_PRIVATE)
                    .edit().putString("state", data).apply();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null) {
            webView.evaluateJavascript("window.onAndroidBack && window.onAndroidBack()", null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
