package com.yoonsoobin.insecuremoviereview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import com.yoonsoobin.insecuremoviereview.dao.UserDao
import com.yoonsoobin.insecuremoviereview.databinding.ActivityAccountBinding
import com.yoonsoobin.insecuremoviereview.entity.User
import com.yoonsoobin.insecuremoviereview.util.EncryptUtil
import com.yoonsoobin.insecuremoviereview.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountActivity : AppCompatActivity() {
    private val REMEMBER_ME: String = "remember-me"

    private lateinit var userSharedPref: SharedPreferences
    private lateinit var userDao: UserDao
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText
    private lateinit var rememberMe: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userSharedPref = getSharedPreferences(REMEMBER_ME, MODE_PRIVATE)
        userDao = (application as CustomApplication).database.userDao()

        usernameText = binding.editUsername
        passwordText = binding.editPassword
        rememberMe = binding.rememberMe

        if (userSharedPref.contains("username")) {
            val username = userSharedPref.getString("username", null)
            val password = userSharedPref.getString("password", null)
            if (username != null && password != null) signIn(this, username, password)
        }

        binding.signInBtn.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            signIn(this, username, password)
        }
        binding.signUpBtn.setOnClickListener { signUp(this) }
    }

    private fun isUsernameOrPasswordEmpty(username: String, password: String): Boolean {
        return username.isEmpty() || password.isEmpty()
    }

    private fun rememberMe(editor: Editor, username: String, password: String) {
        if (rememberMe.isChecked) {
            editor.putString("username", username)
            editor.putString("password", password)
            editor.commit()
        }
    }

    private fun signIn(context: Context, username: String, password: String) {
        if (isUsernameOrPasswordEmpty(username, password)) {
            ToastUtil.makeToast(context, "아이디와 비밀번호를 입력해주세요").show()
            return
        }

        CoroutineScope(IO).launch {
            val currentUser = userDao.findByUsername(username)

            withContext(Main) {
                val encryptedPassword = EncryptUtil.encrypt(password)
                if (currentUser != null && currentUser.password == encryptedPassword) {
                    rememberMe(userSharedPref.edit(), username, password)

                    val nextIntent = Intent(context, ReviewListActivity::class.java)
                    nextIntent.putExtra("username", username)
                    startActivity(nextIntent)
                } else {
                    ToastUtil.makeToast(context, "로그인 정보가 올바르지 않습니다").show()
                }
            }
        }
    }

    private fun signUp(context: Context) {
        val username = usernameText.text.toString()
        val password = EncryptUtil.encrypt(passwordText.text.toString())

        if (isUsernameOrPasswordEmpty(username, password)) {
            ToastUtil.makeToast(context, "아이디와 비밀번호를 입력해 주세요").show()
            return
        }

        CoroutineScope(IO).launch {
            val user = User(username, password)
            userDao.create(user)

            withContext(Main) {
                ToastUtil.makeToast(context, "가입 되었습니다. 로그인해 주세요").show()
            }
        }
    }
}