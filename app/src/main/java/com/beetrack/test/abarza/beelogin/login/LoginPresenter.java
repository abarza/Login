package com.beetrack.test.abarza.beelogin.login;


/**
 * Created by abarza on 11-09-17.
 */

public class LoginPresenter implements LoginContract.UserActionsListener {

  private final LoginContract.View mLoginView;

  public LoginPresenter(LoginContract.View view) {
    mLoginView = view;
  }


  @Override
  public boolean isNetworkAvailable() {
    return true;
  }

  @Override
  public boolean hasValidCredentials() {
    return true;
  }

  @Override
  public boolean errorAtLogin() {
    return true;
  }
}