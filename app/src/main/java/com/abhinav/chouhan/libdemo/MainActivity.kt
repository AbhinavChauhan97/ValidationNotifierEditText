package com.abhinav.chouhan.libdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.abhinav.chouhan.validationnotifieredittext.GroupValidationListener
import com.abhinav.chouhan.validationnotifieredittext.ValidationNotifierEditText
import com.abhinav.chouhan.validationnotifieredittext.VneGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vne1 = findViewById<ValidationNotifierEditText>(R.id.vne1)
        val vne2 = findViewById<ValidationNotifierEditText>(R.id.vne2)
        val vne3 = findViewById<ValidationNotifierEditText>(R.id.vne3)
        val okButton = findViewById<Button>(R.id.ok_button)

        val group = findViewById<VneGroup>(R.id.group)
        group.setOnGroupValidationListener(object : GroupValidationListener{
            override fun onAllBecomeValid(childValidationNotifierEditTexts: List<ValidationNotifierEditText>) {
                okButton.isEnabled = true
            }

            override fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
                okButton.isEnabled = false
            }

        })
    }
}