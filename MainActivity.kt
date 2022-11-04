package com.example.sendsms

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
//    creating variable for edit text and button

//    variables for sms
    lateinit var phoneNo: EditText
    lateinit var smsBody: EditText
    lateinit var sendSms: Button

//    variables for e-mail
    lateinit var toRecipient: EditText
    lateinit var subjectEmail: EditText
    lateinit var bodyEmail: EditText
    lateinit var sendEmail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        SMS SECTION

//        initializing variables for sms
        phoneNo = findViewById(R.id.et_phone)
        smsBody = findViewById(R.id.et_sms)
        sendSms = findViewById(R.id.btnSendSMS)

        sendSms.setOnClickListener {
//            creating variables for phone and message
            val phoneNumber = phoneNo.text.toString()
            val message = smsBody.text.toString()

            try {
                val smsManager:SmsManager
                if (Build.VERSION.SDK_INT>=23) {
                    smsManager = this.getSystemService(SmsManager::class.java)
                }
                else{
                    smsManager = SmsManager.getDefault()
                }
                // sending sms
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                // for successfully sms send
                Toast.makeText(applicationContext, "SMS Sent Successfully !!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                // for error
                Toast.makeText(applicationContext, "SMS Sending Failed !!\nPlease Try Again Later.\n"+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

//        E-MAIL SECTION

//        initializing variables for e-mail
        toRecipient = findViewById(R.id.et_to)
        subjectEmail = findViewById(R.id.et_subject)
        bodyEmail = findViewById(R.id.et_message)
        sendEmail = findViewById(R.id.btnSendEmail)

        sendEmail.setOnClickListener{
//            creating variables for recipient, subject and compose email
            val recipient = toRecipient.text.toString()
            val subject = subjectEmail.text.toString()
            val composeEmail = bodyEmail.text.toString()

            sendEmail(recipient, subject, composeEmail)
        }
    }

    private fun sendEmail(recipient: String, subject: String, composeEmail: String) {

        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, composeEmail)

        try {
            // for successfully email send
            startActivity(Intent.createChooser(mIntent, "Sending E-mail ﹒﹒﹒"))
            Toast.makeText(this, "E-mail Redirecting to Gmail﹒﹒﹒", Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            // for error
            Toast.makeText(this,"E-mail Sending Failed !!"+e.message, Toast.LENGTH_LONG).show()
        }
    }
}


