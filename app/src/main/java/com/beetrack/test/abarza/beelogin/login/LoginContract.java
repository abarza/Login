package com.beetrack.test.abarza.beelogin.login;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;


/**
 * Created by abarza on 11-09-17.
 */

public class LoginContract {

  interface View {

    void toggleLoadingButton();

    void clearFocus();

    void setAnimation();

    void toggleTextInputLayoutError(@NonNull TextInputLayout textInputLayout,
                                    String msg);

    void validateEmptyField(EditText editText, TextInputLayout textInputLayout, String errorText);

  }

  interface UserActionsListener {

    boolean isNetworkAvailable();

    boolean hasValidCredentials();

    boolean errorAtLogin();

  }

}
