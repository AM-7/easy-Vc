package com.example.easyvc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.easyvc.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class signUp : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    lateinit var email : String
    lateinit var password : String
    lateinit var name: String
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseFirestore
    var emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)

        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)
        supportActionBar?.hide()


        auth= Firebase.auth
        database= FirebaseFirestore.getInstance()


        signUpBinding.btnSignUp.setOnClickListener{
            name=signUpBinding.edtSignUpName.text.toString()
            email=signUpBinding.edtSignUpEmail.text.toString()
            password=signUpBinding.edtSignUpPassword.text.toString()

            if(name.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(applicationContext, "Please fill up the credentials", Toast.LENGTH_SHORT).show()
            }else if(password.length<6){
                signUpBinding.edtSignUpPassword.setError("Password less than 6")
            }
            else {
                authentication_signUp(name, email, password)
            }
        }
        signUpBinding.txtSingUpAlready.setOnClickListener{
            startActivity(Intent(this,signIn::class.java))
            finish()
        }
    }
    fun authentication_signUp(name: String, email: String, password: String) {
        val user=user(name,email,password)
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                database.collection("Users")
                    .document()
                    .set(user)
                    .addOnCompleteListener{
                        Toast.makeText(applicationContext, "Successful Registration", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,signIn::class.java))
                        finish()
                    }

            }
            else{
                Toast.makeText(applicationContext, "Unsuccessful", Toast.LENGTH_SHORT).show()
            }

        }
    }
}