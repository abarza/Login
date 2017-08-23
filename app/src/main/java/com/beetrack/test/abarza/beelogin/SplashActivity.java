package com.beetrack.test.abarza.beelogin;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.beetrack.test.abarza.beelogin.home.HomeActivity;
import com.beetrack.test.abarza.beelogin.login.LoginActivity;

import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

  private static final String TAG = SplashActivity.class.getSimpleName();
  private static final int SLEEP_TIME = 1000;
  // Just for testing purposes
  private static final boolean USER_LOGGED_IN = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    ProgressBar progressBar = (ProgressBar) findViewById(R.id.LoadingProgress);
    progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color
            .white),
        PorterDuff.Mode.SRC_IN);


    ShowNextScreen();

  }

  /**
   * Decide witch activity will be loaded before SplashActivity, if the user is logged in, we
   * will send it to the HomeActivity, if not, launch the LoginActivity.
   */
  private void ShowNextScreen() {
    Thread timer = new Thread() {
      public void run() {
        try {
          sleep(SLEEP_TIME);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          // Check if user is logged in
          if (USER_LOGGED_IN) {
            // Send the user to the HomeActivity
            goToActivity(HomeActivity.class);
            Timber.tag(TAG).d("User isn't logged in");
          } else {
            // Send user to LoginActivity
            goToActivity(LoginActivity.class);
            Timber.tag(TAG).d("User was logged in");
          }
        }
      }
    };
    timer.start();
  }

  /**
   * @param activity destination activity
   */
  private void goToActivity(Class activity) {
    Intent intent = new Intent(this, activity);
    startActivity(intent);

  }


}
