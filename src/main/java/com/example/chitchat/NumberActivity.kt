package com.example.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chitchat.databinding.ActivityNumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class NumberActivity : AppCompatActivity() {

    lateinit var binding:ActivityNumberBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth=FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.btnContinue.setOnClickListener {
            if (binding.edNumber.text!!.isEmpty()){
                Toast.makeText(this,"Pleas enter the Phone Number",Toast.LENGTH_SHORT).show()
            }else{
                val intent =Intent(this,OTPActivity::class.java)
                intent.putExtra("number",binding.edNumber.text.toString())
                startActivity(intent)
            }
        }

    }
}