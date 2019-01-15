package com.example.huangkl.exam_gimmatek_kotlin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import java.util.regex.Pattern

class SignUpAccountActivity : AppCompatActivity() {
    private var mNextButton: Button? = null
    private var mIdErrorTextView: TextView? = null
    private var mPasswordMatchErrorTextView: TextView? = null
    private var mPasswordLengthErrorTextView: TextView? = null
    private var mIdEditText: EditText? = null
    private var mPasswordEditText: EditText? = null
    private var mPasswordConfirmEditText: EditText? = null

    private val mOnFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            return@OnFocusChangeListener
        }
        val viewId = v.id

        if (viewId == mIdEditText!!.id) {
            handleIdFormat()
        } else if (viewId == mPasswordEditText!!.id) {
            if (TextUtils.isEmpty(getText(mPasswordEditText!!))) {
                if (TextUtils.isEmpty(getText(mPasswordConfirmEditText!!))) {
                    setPasswordFieldStyle()
                    return@OnFocusChangeListener
                }
                updateAllStyle()
            }
            if (TextUtils.isEmpty(getText(mPasswordConfirmEditText!!))) {
                setPasswordFieldStyle()
                updatePasswordLengthError()
                return@OnFocusChangeListener
            }
            updateAllStyle()
        } else if (viewId == mPasswordConfirmEditText!!.id) {
            if (TextUtils.isEmpty(getText(mPasswordEditText!!))) {
                if (TextUtils.isEmpty(getText(mPasswordConfirmEditText!!))) {
                    setPasswordFieldStyle()
                    return@OnFocusChangeListener
                }
                updateAllStyle()
            }
            if (TextUtils.isEmpty(getText(mPasswordConfirmEditText!!))) {
                setPasswordFieldStyle()
                updatePasswordLengthError()
                return@OnFocusChangeListener
            }
            updateAllStyle()
        }
    }

    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            updateNextButton()
        }

        override fun afterTextChanged(s: Editable) {
            updateNextButton()
        }
    }

    private val isInfoFilled: Boolean
        get() = isIdNumberValid && isPasswordValid

    private val isIdNumberValid: Boolean
        get() {
            val idNumber = getText(mIdEditText!!)
            val charArray = idNumber.toCharArray()
            return if (charArray.size != ID_LENGTH) {
                false
            } else Pattern.matches(REGULAR_ENGLISH_CHARCTER, charArray[0].toString()) &&
                    Pattern.matches(REGULAR_ID_GENDER, charArray[1].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[2].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[3].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[4].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[5].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[6].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[7].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[8].toString()) &&
                    Pattern.matches(REGULAR_ID_NUMBERS, charArray[9].toString())

        }

    private val isPasswordValid: Boolean
        get() {
            val password = getText(mPasswordEditText!!)
            val passwordConfirm = getText(mPasswordConfirmEditText!!)

            if (TextUtils.isEmpty(password)) {
                return false
            }
            return if (TextUtils.isEmpty(passwordConfirm)) {
                false
            } else password == passwordConfirm
        }

    private val isPasswordLengthValid: Boolean
        get() = getText(mPasswordEditText!!).length >= 6 && getText(mPasswordEditText!!).length <= 12

    private val isConfirmPasswordLengthValid: Boolean
        get() = getText(mPasswordConfirmEditText!!).length >= 6 && getText(mPasswordConfirmEditText!!).length <= 12

    private val isConfirmDifferentWithNewPassword: Boolean
        get() = getText(mPasswordEditText!!) != getText(mPasswordConfirmEditText!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_account)
        initView()
        updateNextButton()
    }

    private fun initView() {
        mNextButton = findViewById(R.id.next_button)
        mIdEditText = findViewById(R.id.edit_id)
        mPasswordEditText = findViewById(R.id.edit_password)
        mPasswordConfirmEditText = findViewById(R.id.edit_confirm)
        mIdErrorTextView = findViewById(R.id.error_id_text_view)
        mPasswordMatchErrorTextView = findViewById(R.id.error_confirm_password_text_view)
        mPasswordLengthErrorTextView = findViewById(R.id.error_password_format_text_view)

        mIdEditText!!.onFocusChangeListener = mOnFocusChangeListener
        mPasswordEditText!!.onFocusChangeListener = mOnFocusChangeListener
        mPasswordConfirmEditText!!.onFocusChangeListener = mOnFocusChangeListener

        mIdEditText!!.addTextChangedListener(mTextWatcher)
        mPasswordEditText!!.addTextChangedListener(mTextWatcher)
        mPasswordConfirmEditText!!.addTextChangedListener(mTextWatcher)

        mNextButton!!.setOnClickListener { Toast.makeText(applicationContext, R.string.next_button, Toast.LENGTH_SHORT).show() }

    }

    private fun updateNextButton() {
        val isEnableConfirmButton = isInfoFilled
        mNextButton!!.isClickable = isEnableConfirmButton
        mNextButton!!.setBackgroundColor(resources.getColor(if (isEnableConfirmButton) R.color.chooseColor else R.color.backtoalbus))
    }

    private fun updateAllStyle() {
        setPasswordFieldStyle()
        updatePasswordLengthError()
        updateConfirmPasswordLengthErrorWhenConfirm()
        updateEditTextBackgroundResouceWhenConfirm()
    }

    private fun handleIdFormat() {
        if (TextUtils.isEmpty(getText(mIdEditText!!))) {
            updateIdEditTextStyle(false)
            return
        }
        updateIdEditTextStyle(!isIdNumberValid)
    }

    private fun updatePasswordLengthError() {
        val isPasswordLengthError = isPasswordLengthValid
        setEditTextBackgroundResource(mPasswordEditText!!, !isPasswordLengthError)
        mPasswordLengthErrorTextView!!.visibility = if (isPasswordLengthError) View.GONE else View.VISIBLE
    }

    private fun updateIdEditTextStyle(isIdError: Boolean) {
        setEditTextBackgroundResource(mIdEditText!!, isIdError)
        mIdErrorTextView!!.visibility = if (isIdError) View.VISIBLE else View.GONE
    }

    private fun setPasswordFieldStyle() {
        setEditTextBackgroundResource(mPasswordEditText!!, false)
        setEditTextBackgroundResource(mPasswordConfirmEditText!!, false)
        mPasswordMatchErrorTextView!!.visibility = View.GONE
        mPasswordLengthErrorTextView!!.visibility = View.GONE
    }

    private fun setEditTextBackgroundResource(editText: EditText, isError: Boolean) {
        editText.setBackgroundResource(if (isError)
            R.drawable.red_five_radius_corner_rectangle_background
        else
            R.drawable.blue_five_radius_corner_rectangle_background)
    }

    private fun updateConfirmPasswordLengthErrorWhenConfirm() {
        if (!isConfirmPasswordLengthValid) {
            setEditTextBackgroundResource(mPasswordConfirmEditText!!, true)
            mPasswordLengthErrorTextView!!.visibility = View.VISIBLE
            return
        }
    }

    private fun updateEditTextBackgroundResouceWhenConfirm() {
        if (isConfirmDifferentWithNewPassword) {
            setEditTextBackgroundResource(mPasswordEditText!!, true)
            setEditTextBackgroundResource(mPasswordConfirmEditText!!, true)
            mPasswordMatchErrorTextView!!.visibility = View.VISIBLE
            return
        }
    }

    private fun getText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    companion object {

        private val REGULAR_ENGLISH_CHARCTER = "[a-zA-Z]"
        private val REGULAR_ID_GENDER = "[1-2]"
        private val REGULAR_ID_NUMBERS = "[0-9]"
        private val ID_LENGTH = 10

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SignUpAccountActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            return intent
        }
    }

}