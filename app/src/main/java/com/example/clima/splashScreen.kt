package com.example.clima

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class splashScreen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    var username:String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        firestore = FirebaseFirestore.getInstance()








        Handler(mainLooper).postDelayed({
            if (user != null) {
                if(user.isEmailVerified){
                    firebaseAuth.currentUser?.let {
                        firestore.collection("userinfo").document(it.uid).get().addOnSuccessListener {
                                documents->
                            if (documents.exists()){
                                username = documents.getString("username").toString()
                                constant.username = username
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()
                            }
                        }
                    }

                }
                else{
                    startActivity(Intent(this,signUpscreen::class.java))
                    finish()

                }
            }
            else{
                startActivity(Intent(this,signUpscreen::class.java))
                finish()
            }
        },2000)
    }
}