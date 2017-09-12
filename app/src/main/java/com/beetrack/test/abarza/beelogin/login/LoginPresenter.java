package com.beetrack.test.abarza.beelogin.login;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by abarza on 11-09-17.
 */

public class LoginPresenter implements LoginContract.UserActionsListener {

  private final LoginContract.View mLoginView;

  public LoginPresenter(LoginContract.View view) {
    mLoginView = view;
  }

  /**
   * Hides the keyboard when the user validates their credentials
   * @param activity current Activity
   */
  @Override
  public void clearFocus(Activity activity) {
    View view = activity.getCurrentFocus();
    if (view != null && view instanceof EditText) {
      InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context
          .INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
      view.clearFocus();
    }
  }

  @Override
  public void processLogin() {
    mLoginView.toggleLoadingButton();
  }



}