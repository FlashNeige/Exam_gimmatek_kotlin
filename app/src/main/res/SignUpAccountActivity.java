package com.example.huangkl.exam_gimmatek;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpAccountActivity extends AppCompatActivity {

    private final static String REGULAR_ENGLISH_CHARCTER = "[a-zA-Z]";
    private final static String REGULAR_ID_GENDER = "[1-2]";
    private final static String REGULAR_ID_NUMBERS = "[0-9]";
    private final static int ID_LENGTH = 10;
    private Button mNextButton;
    private TextView mIdErrorTextView;
    private TextView mPasswordMatchErrorTextView;
    private TextView mPasswordLengthErrorTextView;
    private EditText mIdEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfirmEditText;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpAccountActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_account);
        initView();
        updateNextButton();
    }

    private void initView() {
        mNextButton = findViewById(R.id.next_button);
        mIdEditText = findViewById(R.id.edit_id);
        mPasswordEditText = findViewById(R.id.edit_password);
        mPasswordConfirmEditText = findViewById(R.id.edit_confirm);
        mIdErrorTextView = findViewById(R.id.error_id_text_view);
        mPasswordMatchErrorTextView = findViewById(R.id.error_confirm_password_text_view);
        mPasswordLengthErrorTextView = findViewById(R.id.error_password_format_text_view);

        mIdEditText.setOnFocusChangeListener(mOnFocusChangeListener);
        mPasswordEditText.setOnFocusChangeListener(mOnFocusChangeListener);
        mPasswordConfirmEditText.setOnFocusChangeListener(mOnFocusChangeListener);

        mIdEditText.addTextChangedListener(mTextWatcher);
        mPasswordEditText.addTextChangedListener(mTextWatcher);
        mPasswordConfirmEditText.addTextChangedListener(mTextWatcher);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.next_button,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateNextButton() {
        boolean isEnableConfirmButton = isInfoFilled() ;
        mNextButton.setClickable(isEnableConfirmButton);
        mNextButton.setBackgroundColor(getResources().getColor(isEnableConfirmButton ? R.color.chooseColor : R.color.backtoalbus));
    }

    private View.OnFocusChangeListener mOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                return;
            }
            int viewId = v.getId();

            if (viewId == mIdEditText.getId()) {
                handleIdFormat();
            } else if (viewId == mPasswordEditText.getId()) {
                if (TextUtils.isEmpty(getText(mPasswordEditText))) {
                    if (TextUtils.isEmpty(getText(mPasswordConfirmEditText))) {
                        setPasswordFieldStyle();
                        return;
                    }
                    updateAllStyle();
                }
                if (TextUtils.isEmpty(getText(mPasswordConfirmEditText))) {
                    setPasswordFieldStyle();
                    updatePasswordLengthError();
                    return;
                }
                updateAllStyle();
            } else if (viewId == mPasswordConfirmEditText.getId()) {
                if (TextUtils.isEmpty(getText(mPasswordEditText))) {
                    if (TextUtils.isEmpty(getText(mPasswordConfirmEditText))) {
                        setPasswordFieldStyle();
                        return;
                    }
                    updateAllStyle();
                }
                if (TextUtils.isEmpty(getText(mPasswordConfirmEditText))) {
                    setPasswordFieldStyle();
                    updatePasswordLengthError();
                    return;
                }
                updateAllStyle();
            }
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateNextButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
            updateNextButton();
        }
    };

    private void updateAllStyle() {
        setPasswordFieldStyle();
        updatePasswordLengthError();
        updateConfirmPasswordLengthErrorWhenConfirm();
        updateEditTextBackgroundResouceWhenConfirm();
    }

    private boolean isInfoFilled() {
        return isIdNumberValid() && isPasswordValid();
    }

    private boolean isIdNumberValid() {
        String idNumber = getText(mIdEditText);
        char[] charArray = idNumber.toCharArray();
        if (charArray.length != ID_LENGTH) {
            return false;
        }

        return Pattern.matches(REGULAR_ENGLISH_CHARCTER, String.valueOf(charArray[0])) &&
                Pattern.matches(REGULAR_ID_GENDER, String.valueOf(charArray[1])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[2])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[3])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[4])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[5])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[6])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[7])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[8])) &&
                Pattern.matches(REGULAR_ID_NUMBERS, String.valueOf(charArray[9]));
    }

    private void handleIdFormat() {
        if (TextUtils.isEmpty(getText(mIdEditText))) {
            updateIdEditTextStyle(false);
            return;
        }
        updateIdEditTextStyle(!isIdNumberValid());
    }

    private boolean isPasswordValid() {
        String password = getText(mPasswordEditText);
        String passwordConfirm = getText(mPasswordConfirmEditText);

        if (TextUtils.isEmpty(password)) {
            return false;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            return false;
        }
        return password.equals(passwordConfirm);
    }

    private void updatePasswordLengthError() {
        boolean isPasswordLengthError = isPasswordLengthValid();
        setEditTextBackgroundResource(mPasswordEditText, !isPasswordLengthError);
        mPasswordLengthErrorTextView.setVisibility(isPasswordLengthError ? View.GONE : View.VISIBLE);
    }

    private boolean isPasswordLengthValid() {
        return getText(mPasswordEditText).length() >= 6 && getText(mPasswordEditText).length() <= 12;
    }

    private boolean isConfirmPasswordLengthValid() {
        return getText(mPasswordConfirmEditText).length() >= 6 && getText(mPasswordConfirmEditText).length() <= 12;
    }

    private void updateIdEditTextStyle(boolean isIdError) {
        setEditTextBackgroundResource(mIdEditText, isIdError);
        mIdErrorTextView.setVisibility(isIdError ? View.VISIBLE : View.GONE);
    }

    private boolean isConfirmDifferentWithNewPassword() {
        return !getText(mPasswordEditText).equals(getText(mPasswordConfirmEditText));
    }

    private void setPasswordFieldStyle() {
        setEditTextBackgroundResource(mPasswordEditText, false);
        setEditTextBackgroundResource(mPasswordConfirmEditText, false);
        mPasswordMatchErrorTextView.setVisibility(View.GONE);
        mPasswordLengthErrorTextView.setVisibility(View.GONE);
    }

    private void setEditTextBackgroundResource(EditText editText, boolean isError) {
        editText.setBackgroundResource(isError ?
                R.drawable.red_five_radius_corner_rectangle_background : R.drawable.blue_five_radius_corner_rectangle_background);
    }

    private void updateConfirmPasswordLengthErrorWhenConfirm() {
        if (!isConfirmPasswordLengthValid()) {
            setEditTextBackgroundResource(mPasswordConfirmEditText, true);
            mPasswordLengthErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
    }

    private void updateEditTextBackgroundResouceWhenConfirm() {
        if (isConfirmDifferentWithNewPassword()) {
            setEditTextBackgroundResource(mPasswordEditText, true);
            setEditTextBackgroundResource(mPasswordConfirmEditText, true);
            mPasswordMatchErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

}