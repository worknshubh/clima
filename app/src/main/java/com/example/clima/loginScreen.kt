package com.example.clima

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class loginScreen : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
            firebaseAuth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            var login_btn = findViewById<Button>(R.id.login_btn)
            var sign_up_redirect = findViewById<TextView>(R.id.signup_redirect)
            login_btn.setOnClickListener {
                var login_user_mail = findViewById<EditText>(R.id.useremail)
                var login_user_pass = findViewById<EditText>(R.id.userpassword)
                if(validateuser(login_user_mail,login_user_pass)){
                    firebaseAuth.signInWithEmailAndPassword(login_user_mail.text.toString(),login_user_pass.text.toString()).addOnSuccessListener {
                        if (firebaseAuth.currentUser?.isEmailVerified == true){
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Email not Verified", Toast.LENGTH_SHORT).show()
                        }
                    }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this,"Failed to Login due to $exception", Toast.LENGTH_SHORT).show()
                        }

                }
            }
            sign_up_redirect.setOnClickListener{
                startActivity(Intent(this,signUpscreen::class.java))
                finish()
            }

        }
        private fun validateuser(usermail: EditText, userpass: EditText):Boolean {
            if(!Patterns.EMAIL_ADDRESS.matcher(usermail.text.toString()).matches()){
                usermail.setError("Email is not valid Kindly enter valid email")
                return false
            }
            if(userpass.text.length<6){
                userpass.setError("Password Must be above 6 letter")
                return false
            }

            return true
        }


}