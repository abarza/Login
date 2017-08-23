package com.beetrack.test.abarza.beelogin.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.beetrack.test.abarza.beelogin.R;

public class LoginActivity extends AppCompatActivity {

  private EditText mEditTextUserName;
  private EditText mEditTextPassword;
  private TextInputLayout mTextInputUserName;
  private TextInputLayout mTextInputPassword;
  private Button mLoginButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    bindUI();

    mLoginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        validate();
      }
    });

  }

  private void bindUI() {
    mEditTextUserName = (EditText) findViewById(R.id.userNameEditText);
    mTextInputUserName = (TextInputLayout) findViewById(R.id.userNameTextInputLayout);
    mTextInputPassword = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
    mEditTextPassword = (EditText) findViewById(R.id.passwordEditText);
    mLoginButton = (Button) findViewById(R.id.login_button);
  }

  public void validate() {
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
