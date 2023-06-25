package com.example.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chitchat.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpactivityBinding
    lateinit var auth: FirebaseAuth
    lateinit var verificationId:String
    lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=FirebaseAuth.getInstance()

        val builder=AlertDialog.Builder(this)
        builder.setMessage("Pleas Wait...")
        builder.setTitle("Loading")
        builder.setCancelable(false)

        dialog=builder.create()
        dialog.show()

        val phoneNumber="+91"+intent.getStringExtra("number")

        val option=PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                   Toast.makeText(this@OTPActivity,
                           "${p0}\n",Toast.LENGTH_LONG).show()

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId=p0
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(option)

        binding.btnLogin.setOnClickListener {


            if(binding.edOtp.text!!.isEmpty()){

                Toast.makeText(this,"Pleas Enter the OTP ",Toast.LENGTH_SHORT).show()
            }else{
                dialog.show()

                val credential=PhoneAuthProvider.getCredential(verificationId,binding.edOtp.text.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful){

                            dialog.dismiss()

                            startActivity(Intent(this,ProfileActivity::class.java))
                            finish()

                        }else{
                            Toast.makeText(this,"Error ${it.exception}",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

    }
}