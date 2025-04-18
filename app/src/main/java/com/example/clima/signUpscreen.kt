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

class signUpscreen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_upscreen)

            firebaseAuth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            var login_redirect = findViewById<TextView>(R.id.login_redirect)
            var sign_up_btn = findViewById<Button>(R.id.signup_btn)
            sign_up_btn.setOnClickListener {
                var username = findViewById<EditText>(R.id.username)
                var usermail = findViewById<EditText>(R.id.useremail)
                var userpass = findViewById<EditText>(R.id.userpassword)

                if(validateuser(username,usermail,userpass)){
                    firebaseAuth.createUserWithEmailAndPassword(usermail.text.toString(),userpass.text.toString()).addOnSuccessListener {
                        var userdata = mapOf("username" to username.text.toString(),"usermail" to usermail.text.toString())

                        firebaseAuth.currentUser?.sendEmailVerification()
                        firebaseAuth.currentUser?.let { it1 ->
                            firestore.collection("userinfo").document(
                                it1.uid).set(userdata)
                        }
                        Toast.makeText(this,"Account Created Successfully Kindly Verify your Email",
                            Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this,loginScreen::class.java))
                        finish()
                    }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this,"Failed to create Account due to $exception", Toast.LENGTH_SHORT).show()
                        }

                }
            }

            login_redirect.setOnClickListener{
                startActivity(Intent(this,loginScreen::class.java))
                finish()
            }

        }

        private fun validateuser(username: EditText, usermail: EditText, userpass: EditText):Boolean {
            if(username.text.length<6){
                username.setError("UserName Must be above 6 letter")
                return false
            }
            if(userpass.text.length<6){
                userpass.setError("Password Must be above 6 letter")
                return false
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(usermail.text.toString()).matches()){
                usermail.setError("Email is not valid Kindly enter valid email")
                return false
            }
            return true
        }
    }
