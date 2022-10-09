package com.example.easyvc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.easyvc.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class signIn : AppCompatActivity() {
    private lateinit var signIn: ActivitySignInBinding
    lateinit var email: String
    lateinit var password: String
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signIn= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signIn.root)
        supportActionBar?.hide()

        auth= Firebase.auth
        signIn.btnSignIn.setOnClickListener{
            email=signIn.edtSignInEmail.text.toString()
            password=signIn.edtSignInPassword.text.toString()

            if(email.isBlank() || password.isBlank()){

                signIn.edtSignInEmail.setError("Blank")
                signIn.edtSignInPassword.setError("Blank")

                Toast.makeText(applicationContext, "Please fill up the credentials", Toast.LENGTH_SHORT).show()
            }

            else if(password.length<6){
                signIn.edtSignInPassword.setError("Password less than 6")
            }
            else{
                authentication_sign(email,password)
            }

            }
        signIn.txtNoAccount.setOnClickListener{
            startActivity(Intent(this,signUp::class.java))
            finish()
        }
    }



    fun authentication_sign(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(applicationContext, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }

    }
}