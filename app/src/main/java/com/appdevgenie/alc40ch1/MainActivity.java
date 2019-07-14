package com.appdevgenie.alc40ch1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bAbout;
    private Button bProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
    }

    private void initVariables() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bAbout = findViewById(R.id.bAboutALC);
        bProfile = findViewById(R.id.bMyProfile);
        bAbout.setOnClickListener(this);
        bProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bAboutALC:

                Intent intentA = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intentA);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;

            case R.id.bMyProfile:

                Intent intentB = new Intent(MainActivity.this, StudentInfoActivity.class);
                startActivity(intentB);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                break;
        }
    }
}
