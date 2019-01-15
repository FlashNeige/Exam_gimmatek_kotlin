package com.example.huangkl.exam_gimmatek_kotlin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

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
                Toast.makeText(application, R.string.signIn_test_ok, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(application, R.string.signIn_test_fail, Toast.LENGTH_LONG).show()
            }
        }

        mSignUpButton!!.setOnClickListener { startActivity(SignUpAccountActivity.newIntent(this@MainActivity)) }
    }
}
