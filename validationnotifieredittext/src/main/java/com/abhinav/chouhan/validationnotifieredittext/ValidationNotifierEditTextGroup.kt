package com.abhinav.chouhan.validationnotifieredittext

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

/**
 * <h> A Mediator Helper Class to notify the validity of a group of ValidationNotifierEditText</h>
 *
 * A child class of ConstraintLayout
 * which notifies whether  or not all children ValidationNotifierEditText contains valid text or when any of them now contain invalid text but previously
 * contained valid text
 *
 * <b> use case </b> you may be creating a form to create account in when all editText should contain valid data
 * then only you want enable the sign up button
 * in that situation you can use this class and set up a listener on it then whenever all of the ValidationNotifierEditText
 * children of this ViewGroup are valid your listener will be called and when any of them becomes invalid then also your
 * listener is called so that you can tell user what was gone wrong
 *
 * @author Abhinav Chouhan
 * @author abhinavchouhan97@gmail.com
 * @since 13-march-2021
 */
open class ValidationNotifierEditTextGroup @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? = null,defStyle:Int = 0)
    : ConstraintLayout(context,attributeSet){

    //list which contains a list of validationNotifierEditTexts list
    var allAreValid:Boolean = false
        private set
        get() {
            validationNotifierEditTextList.forEach {
                if(!it.isValid){
                    return false
                }
            }
            return true
        }
    private val listener = object : ValidationNotifierEditText.ValidationChangeListener{

        override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
            validationEditTextGroupValidationListener?.onAnyBecomeValid(validationNotifierEditText)
            validationNotifierEditTextList.forEach {
                if(!it.isValid){
                    return
                }
            }
            allAreValid = true
            validationEditTextGroupValidationListener?.onAllBecomeValid(validationNotifierEditTextList)
        }

        override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
            allAreValid = false
            validationEditTextGroupValidationListener?.onAnyBecomeInvalid(validationNotifierEditText)
        }
    }
    val validationNotifierEditTextList = LinkedList<ValidationNotifierEditText>()

    private var validationEditTextGroupValidationListener: GroupValidationListener? = null

    /**
     * interface to use for the validation of child ValidationNotifierEditTexts of this Group
     */


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)

        if(child is ValidationNotifierEditText) {
            validationNotifierEditTextList.add(child)
            child.addValidationChangeListener(listener)
        }
    }



    fun setOnGroupValidationListener(validationEditTextGroupValidationListener: GroupValidationListener){
        this.validationEditTextGroupValidationListener = validationEditTextGroupValidationListener
    }
}
