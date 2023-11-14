package com.example.nt118_project.Fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.nt118_project.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val iVReturn = findViewById<ImageView>(R.id.iVBack)
        iVReturn.setOnClickListener {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }

        val SaveBtn = findViewById<Button>(R.id.Savebutton)
        val tVEmail = findViewById<EditText>(R.id.eTEmail)
        val tVOlePw = findViewById<EditText>(R.id.eTOldPw)
        val tVNewPw_1 = findViewById<EditText>(R.id.eTNewPw_1)
        val tVNewPw_2 = findViewById<EditText>(R.id.eTNewPw_2)
        val firebaseAuth = FirebaseAuth.getInstance()
        val currUser: FirebaseUser? = firebaseAuth.currentUser
        val receivedPw = intent.getStringExtra("Pw").toString()
        val newPw  =tVNewPw_1.text.toString()
        SaveBtn.setOnClickListener {
            if (tVEmail.text.toString() == "" || tVOlePw.text.toString() == "" || tVNewPw_1.text.toString() == "" || tVNewPw_2.text.toString() == "")
            {
                Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show()
            }
            else if(currUser!!.email.toString() != tVEmail.text.toString())
            {
                Toast.makeText(this,"Bạn nhập sai Email", Toast.LENGTH_LONG).show()
            }
            else if(tVNewPw_1.text.toString() != tVNewPw_2.text.toString())
            {
                Toast.makeText(this,"Bạn xác minh lại không đúng password", Toast.LENGTH_LONG).show()
            }
            else{
                val firebaseAuth = FirebaseAuth.getInstance()
                val curr: FirebaseUser? = firebaseAuth.currentUser
                //val credential = EmailAuthProvider.getCredential(curr!!.email.toString(), tVOlePw.text.toString())
                firebaseAuth.signInWithEmailAndPassword(curr!!.email.toString(), tVOlePw.text.toString()).addOnCompleteListener {
                    curr!!.verifyBeforeUpdateEmail(curr!!.email.toString()).addOnCompleteListener { task ->
                        if(task.isSuccessful)
                        {
                            curr!!.updatePassword(newPw).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Email Changed" + " Current Email is 1 " + newPw,
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Không cập nhật được email: ${task.exception}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(
                            this,
                            "Fail",
                            Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}