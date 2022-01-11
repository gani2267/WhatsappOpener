package com.ganilabs.whatsappopener

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var num:String = ""

        if(intent.action == Intent.ACTION_PROCESS_TEXT){
            num = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
        }

        var str : String = ""
        for(i in num){
            if(i == ' '){

            }else{
                str += i
            }
        }

        num = str
        val phnum = giveNum(num)
        tt.text = num
        if(phnum.isDigitsOnly()){
            startWhatsapp(phnum)
        }else{
            Toast.makeText(this,"Please check the number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun startWhatsapp(num: String) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        intent.data = Uri.parse("https://wa.me/$num")

        //check whatsapp exit in device or not
        if(packageManager.resolveActivity(intent,0) == null){
            startActivity(intent)
        }else{
            Toast.makeText(this,"Please install Whatsapp",Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun giveNum(num: String): String {
        //just setting num in valid form
        //valid form is  "91"+10digitnum for india

        if(num[0] == '+' || num[0] == '0'){
            return num.substring(1)
        }else if(num.length == 10){
            return "91"+num
        }
        return num
    }
}