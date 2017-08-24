package com.beetrack.test.abarza.beelogin.login;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beetrack.test.abarza.beelogin.R;



public class LoginActivity extends AppCompatActivity {

  private static final String TAG = LoginActivity.class.getSimpleName();
  private EditText mEditTextUserName;
  private EditText mEditTextPassword;
  private TextInputLayout mTextInputUserName;
  private TextInputLayout mTextInputPassword;
  private Button mLoginButton;
  private ProgressBar mLoadingLogin;
  private RelativeLayout mCheckLogin;
  private TextView mLoginText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    bindUI();


    mLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        login();
      }
    });

  }

  private void bindUI() {
    mEditTextUserName = (EditText) findViewById(R.id.userNameEditText);
    mTextInputUserName = (TextInputLayout) findViewById(R.id.userNameTextInputLayout);
    mTextInputPassword = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
    mEditTextPassword = (EditText) findViewById(R.id.passwordEditText);
    mLoginButton = (Button) findViewById(R.id.login_button);
    mLoadingLogin = (ProgressBar) findViewById(R.id.loadingLogin);
    mCheckLogin = (RelativeLayout) findViewById(R.id.checkLogin);
    mLoginText = (TextView) findViewById(R.id.textLogin);

  }

  private void login() {
    validate();
    loading();
  }

  private void setAnimation(final View view, int currentWidth, int newWidth) {

    ValueAnimator widthAnimator = ValueAnimator
        .ofInt(currentWidth, newWidth)
        .setDuration(1000);

    widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {

        // get the value the interpolator is at
        Integer value = (Integer) animation.getAnimatedValue();

        view.getLayoutParams().width = value;
        view.requestLayout();
      }
    });

    AnimatorSet set = new AnimatorSet();
    set.play(widthAnimator);
    // this is how you set the parabola which controls acceleration
    set.setInterpolator(new AccelerateDecelerateInterpolator());
    // start the animation
    set.start();

  }

  private void loading() {
    setAnimation(mCheckLogin,500, 200);
    mLoginText.setVisibility(View.GONE);
    mLoadingLogin.setVisibility(View.VISIBLE);

  }

  public void validate() {

    // Check if fields are empty
    String userNameError = null;
    if (TextUtils.isEmpty(mEditTextUserName.getText())) {
      userNameError = getString(R.string.required_field);
    }
    toggleTextInputLayoutError(mTextInputUserName, userNameError);

    String passError = null;
    if (TextUtils.isEmpty(mEditTextPassword.getText())) {
      passError = getString(R.string.required_field);
    }
    toggleTextInputLayoutError(mTextInputPassword, passError);


    // hide keyboard
    clearFocus();
  }

  /**
   * Display/hides TextInputLayout error.
   *
   * @param msg the message, or null to hide
   */
  private static void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                                 String msg) {
    textInputLayout.setError(msg);
    if (msg == null) {
      textInputLayout.setErrorEnabled(false);
    } else {
      textInputLayout.setErrorEnabled(true);
    }
  }

  /**
   * Hides the keyboard when the user validates their credentials
   */
  private void clearFocus() {
    View view = this.getCurrentFocus();
    if (view != null && view instanceof EditText) {
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context
          .INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
      view.clearFocus();
    }
  }

}
