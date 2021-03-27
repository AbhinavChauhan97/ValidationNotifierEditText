package com.abhinav.chouhan.libdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.abhinav.chouhan.validationnotifieredittext.ValidationNotifierEditText
import com.abhinav.chouhan.validationnotifieredittext.ValidationNotifierEditTextGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val validationNotifierEditTextGroup = findViewById<ValidationNotifierEditTextGroup>(R.id.vneg)
        validationNotifierEditTextGroup.findViewById<ValidationNotifierEditText>(R.id.vne1)
            .validatorRegex = "[A-Z]+"

        val okButton = findViewById<Button>(R.id.ok_button)
        validationNotifierEditTextGroup.setOnGroupValidationListener(object : ValidationNotifierEditTextGroup.ValidationEditTextGroupValidationListener{
            override fun onAllBecomeValid(childValidationNotifierEditTexts: List<ValidationNotifierEditText>) {
                childValidationNotifierEditTexts.forEach { _ ->
                    okButton.isEnabled = true
                }
            }

            override fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                  okButton.isEnabled = false
            }

        })
    }
}