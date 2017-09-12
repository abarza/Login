package com.beetrack.test.abarza.beelogin.login;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beetrack.test.abarza.beelogin.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

  private static final String TAG = LoginActivity.class.getSimpleName();
  private LoginContract.UserActionsListener mUserActionsListener;
  private EditText mEditTextUserName;
  private EditText mEditTextPassword;
  private TextInputLayout mTextInputUserName;
  private TextInputLayout mTextInputPassword;
  private ProgressBar mLoadingLogin;
  private RelativeLayout mCheckLogin;
  private TextView mLoginText;
  private ScrollView mScrollView;

  private static final int SLEEP_TIME = 1000;

  // set fields for intDef
  private static final int IDLE = 0;
  private static final int SUCCESS = 1;
  private static final int FAILED = 2;
  private static final int NO_CONNECTION = 3;
  private static final int WRONG_CREDENTIALS = 4;

  private
  @LoginActivity.LoginMode
  int mLoginStatus = 0;

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({IDLE, SUCCESS, FAILED, NO_CONNECTION, WRONG_CREDENTIALS})
  @interface LoginMode {}

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mUserActionsListener = new LoginPresenter(this);
    setupComponents();
    if(mUserActionsListener.isNetworkAvailable()) {
      setListeners();
    } else {
      setConnectionErrorMode();
    }


  }

  private void setListeners() {
    mCheckLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        login();
      }
    });
  }

  private void setupComponents() {
    mScrollView = (ScrollView) findViewById(R.id.loginScreen);
    mEditTextUserName = (EditText) findViewById(R.id.userNameEditText);
    mTextInputUserName = (TextInputLayout) findViewById(R.id.userNameTextInputLayout);
    mTextInputPassword = (TextInputLayout) findViewById(R.id.passwordTextInputLayout);
    mEditTextPassword = (EditText) findViewById(R.id.passwordEditText);
    mLoadingLogin = (ProgressBar) findViewById(R.id.loadingLogin);
    mCheckLogin = (RelativeLayout) findViewById(R.id.checkLogin);
    mLoginText = (TextView) findViewById(R.id.textLogin);
    // Set a custom color for the indeterminated progressBar
    mLoadingLogin.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color
            .yellow),
        PorterDuff.Mode.SRC_IN);
  }

  private void login() {
    mCheckLogin.setEnabled(false);
    validate();
    updateUI();
  }

  @Override
  public void toggleLoadingButton() {
    if (mCheckLogin.getWidth() != 180) {
      setAnimation();
      mLoginText.setVisibility(View.GONE);
      mLoadingLogin.setVisibility(View.VISIBLE);
    } else {
      mCheckLogin.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
      mCheckLogin.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
      mLoginText.setVisibility(View.VISIBLE);
      mLoadingLogin.setVisibility(View.GONE);
    }
  }

  /**
   * Animate the view's width
   */
  @Override
  public void setAnimation() {
    int currentWidth = mCheckLogin.getWidth();

    ValueAnimator widthAnimator = ValueAnimator
        .ofInt(currentWidth, 180)
        .setDuration(600);
    widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {

        // get the value the interpolator is at
        mCheckLogin.getLayoutParams().width = (Integer) animation.getAnimatedValue();
        mCheckLogin.requestLayout();
      }
    });
    AnimatorSet set = new AnimatorSet();
    set.play(widthAnimator);
    // set the parabola which controls acceleration
    set.setInterpolator(new AccelerateDecelerateInterpolator());
    // start the animation
    set.start();
  }


  public void validate() {
    if (!mUserActionsListener.isNetworkAvailable()) {
      mLoginStatus = NO_CONNECTION;
    } else if (!mUserActionsListener.hasValidCredentials()) {
      mLoginStatus = WRONG_CREDENTIALS;
    } else if (mUserActionsListener.errorAtLogin()) {
      mLoginStatus = FAILED;
    } else {
      mLoginStatus = SUCCESS;
    }
  }

  public void updateUI() {
    switch (mLoginStatus) {
      case WRONG_CREDENTIALS:
        seWrongCredentialsMode();
      case NO_CONNECTION:
        setConnectionErrorMode();
        break;
      case FAILED:
        setFailedMode();
        break;
      case SUCCESS:
        setSuccessMode();
        break;
      case IDLE:
        setIdleMode();
    }
  }

  /**
   * Display/hides TextInputLayout error.
   *
   * @param msg the message, or null to hide
   */
  @Override
  public void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout, String msg) {
    textInputLayout.setError(msg);
    if (msg == null) {
      textInputLayout.setErrorEnabled(false);
    } else {
      textInputLayout.setErrorEnabled(true);
    }
  }

  /**
   * Validate if an editText is filled by the user
   *
   * @param editText        with the text
   * @param textInputLayout that contains the editText
   * @param errorText       message to guide the user
   */
  @Override
  public void validateEmptyField(EditText editText, TextInputLayout textInputLayout, String
      errorText) {
    String userNameError = null;
    if (TextUtils.isEmpty(editText.getText())) {
      userNameError = errorText;
    }
    toggleTextInputLayoutError(textInputLayout, userNameError);
  }

  private void checkMissingFields() {
    validateEmptyField(mEditTextUserName, mTextInputUserName, getString(R
        .string.required_field));
    validateEmptyField(mEditTextPassword, mTextInputPassword, getString(R
        .string.required_field));
  }

  private void seWrongCredentialsMode() {
    Toast.makeText(this, "Wrong credentials", Toast.LENGTH_SHORT).show();
    setIdleMode();
  }

  private void setConnectionErrorMode() {
    mEditTextUserName.setFocusable(false);
    mEditTextPassword.setFocusable(false);
    mUserActionsListener.clearFocus(this);
    mEditTextPassword.setEnabled(false);
    mEditTextUserName.setEnabled(false);
    mCheckLogin.setEnabled(false);
    Snackbar snackbar = Snackbar.make(mScrollView, R.string.no_internet,
        Snackbar
            .LENGTH_INDEFINITE);
    snackbar.show();
  }

  private void setSuccessMode() {
    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    setIdleMode();
  }

  private void setFailedMode() {
    Toast.makeText(this, "Fail login", Toast.LENGTH_SHORT).show();
    setIdleMode();
  }

  private void setIdleMode() {
    checkMissingFields();
    Thread timer = new Thread() {
      public void run() {
        try {
          sleep(SLEEP_TIME);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              mUserActionsListener.clearFocus(LoginActivity.this);
              mCheckLogin.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
              mCheckLogin.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
              mLoginText.setVisibility(View.VISIBLE);
              mLoadingLogin.setVisibility(View.GONE);
            }
          });
        }
      }
    };
    timer.start();
    mCheckLogin.setEnabled(true);
  }

}
