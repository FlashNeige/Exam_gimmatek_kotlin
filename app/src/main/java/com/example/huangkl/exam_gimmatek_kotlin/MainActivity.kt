package com.example.huangkl.exam_gimmatek_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.toast
import org.jetbrains.anko.longToast

class MainActivity : AppCompatActivity() {

    private var mSignInButton: Button? = null
    private var mSignUpButton: Button? = null
    private var mVersionTextView: TextView? = null
    private var mAccountEditText: EditText? = null
    private var mPasswordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mSignInButton = findViewById(R.id.sign_in_button)
        mSignUpButton = findViewById(R.id.sign_up_button)
        mVersionTextView = findViewById(R.id.version_text_view)
        mAccountEditText = findViewById(R.id.account_edit_text)
        mPasswordEditText = findViewById(R.id.password_edit_text)
        mVersionTextView!!.text = BuildConfig.VERSION_NAME

        mSignInButton!!.setOnClickListener {
            val isAccountCorrect = mAccountEditText!!.text.toString() == getString(R.string.test_id)
            val isPasswordCorrect = mPasswordEditText!!.text.toString() == getString(R.string.test_password)

            if (isAccountCorrect && isPasswordCorrect) {
                toast(R.string.signIn_test_ok)
            } else {
                toast(R.string.signIn_test_fail)
            }
        }

        mSignUpButton!!.setOnClickListener { startActivity(SignUpAccountActivity.newIntent(this@MainActivity)) }
    }
}
