package com.beetrack.test.abarza.beelogin;

import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

  private static final String TAG = SplashActivity.class.getSimpleName();
  private static final int SLEEP_TIME = 3000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ProgressBar progressBar = (ProgressBar) findViewById(R.id.LoadingProgress);
    progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color
            .white),
        PorterDuff.Mode.SRC_IN);

  }
}
