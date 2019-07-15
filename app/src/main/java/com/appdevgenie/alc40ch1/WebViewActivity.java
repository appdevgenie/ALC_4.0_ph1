package com.appdevgenie.alc40ch1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class WebViewActivity extends AppCompatActivity {

    private static final String URL = "https://andela.com/alc/";

    private WebView webView;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        if(CheckNetworkConnection.isNetworkConnected(this)) {
            webView.loadUrl(URL);
        }else{
            Toast.makeText(WebViewActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

            String message;

            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "Certificate is untrusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "Certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "Certificate ID is mismatched.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "Certificate is not yet valid.";
                    break;
                default:
                    message = "Certificate error.";
                    break;
            }

            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(message);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                    finish();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.setTitle("SSL Certificate Error");
            dialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
