package com.example.nt118_project.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nt118_project.MainActivity
import com.example.nt118_project.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_fragment)

        val signup_button = findViewById<Button>(R.id.signup_button)

        signup_button.setOnClickListener {
            startActivity(Intent(this, SignupFragment::class.java))
        }
        val signin_button = findViewById<Button>(R.id.signin_button)
        signin_button.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email_  = findViewById<EditText>(R.id.email_signin)
        val pw_  = findViewById<EditText>(R.id.pw_signin)

        val email = email_.text.toString()
        val pw = pw_.text.toString()

        when{
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(pw) -> Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_LONG).show()

            else -> {
                val progressDialog = ProgressDialog(this@LoginFragment)
                progressDialog.setTitle("Đăng nhập")
                progressDialog.setMessage("Vui lòng đợi...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                val auth: FirebaseAuth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
                    if(task.isSuccessful)
                    {
                        val intent = Intent(this@LoginFragment, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("Pw", pw)
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()
                    }
                    else
                    {
                        val mess = task.exception!!.toString()
                        Toast.makeText(this, "Lỗi: $mess", Toast.LENGTH_LONG).show()
                        auth.signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(FirebaseAuth.getInstance().currentUser != null)
//        {
//            val intent = Intent(this@LoginFragment, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            finish()
//        }
//    }
}