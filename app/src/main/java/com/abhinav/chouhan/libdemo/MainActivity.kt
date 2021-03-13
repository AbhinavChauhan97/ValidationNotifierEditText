package com.abhinav.chouhan.libdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abhinav.chouhan.validationnotifieredittext.ValidationNotifierEditText
import com.abhinav.chouhan.validationnotifieredittext.ValidationNotifierEditTextGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val validationNotifierEditTextGroup = findViewById<ValidationNotifierEditTextGroup>(R.id.vneg)
        validationNotifierEditTextGroup.setOnGroupValidationListener(object : ValidationNotifierEditTextGroup.ValidationEditTextGroupValidationListener{
            override fun onAllBecomeValid(childValidationNotifierEditTexts: List<ValidationNotifierEditText>) {
                childValidationNotifierEditTexts.forEach {
                    Log.d("log",it.tag.toString() + "valid")
                }
            }

            override fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                Log.d("log",validationNotifierEditText.tag.toString() + "invalid")
            }

        })
    }
}