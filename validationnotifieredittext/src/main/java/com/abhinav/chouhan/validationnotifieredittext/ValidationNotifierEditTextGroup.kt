package com.abhinav.chouhan.validationnotifieredittext

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.IllegalArgumentException
import java.util.*

/**
 * <h> A Mediator Helper Class to notify the validity of a group of ValidationNotifierEditText</h>
 *
 * A child class of LinearLayout can only contain ValidationNotifierEditText as child views
 * and notifies all of those ValidationNotifierEditText contains valid text or when any of them now contain invalid text but previously
 * contained valid text
 *
 * <b> use case </b> you may be creating a form to create account in when all editText should contain valid data
 * then only you want enable the sign up button
 * in that situation you can use this class and set up a listener on it then whenever all of the ValidationNotifierEditText
 * children of this ViewGroup are valid your listener will be called and when any of them becomes invalid then also your
 * listener is called so that you can tell user what was gone wrong
 */
class ValidationNotifierEditTextGroup @JvmOverloads constructor(context: Context,attributeSet: AttributeSet? = null,defStyle:Int = 0)
    : LinearLayout(context,attributeSet,defStyle){

    //list which contains a list of validationNotifierEditTexts list
    private val listener = object : ValidationNotifierEditText.ValidationChangeListener{

        override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
            validationNotifierEditTextList.forEach {
                if(!it.isValid){
                    return
                }
            }
            validationEditTextGroupValidationListener?.onAllBecomeValid(validationNotifierEditTextList)
        }

        override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
            validationEditTextGroupValidationListener?.onAnyBecomeInvalid(validationNotifierEditText)
        }

    }
    private val validationNotifierEditTextList = LinkedList<ValidationNotifierEditText>()

    private var validationEditTextGroupValidationListener: ValidationEditTextGroupValidationListener? = null

    /**
     * interface to use for the validation of child ValidationNotifierEditTexts of this Group
     */
    interface ValidationEditTextGroupValidationListener {
        /**
         * called when all ValidationNotifierEdiText children of this viewgroup contains valid text
         * @param childValidationNotifierEditTexts list of all children ValidationNotifierEditText that are children of this viewgroup
         */
        fun onAllBecomeValid(childValidationNotifierEditTexts: List<ValidationNotifierEditText>)

        /**
         * called when any ValidationNotifierEditText child of this viewgroup now contains a invalid text but previously
         * contained valid text
         * @param validationNotifierEditText the ValidationNotifierEditText which now contains invalid text
         */
        fun onAnyBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        super.addView(child, index, params)

        if(child !is ValidationNotifierEditText){
            throw IllegalArgumentException("child views of ValidationNotifierEditTextGroup should only be ValidationNotifierEditText")
        }
        validationNotifierEditTextList.add(child)
        child.addValidationChangeListener(listener)
    }



    fun setOnGroupValidationListener(validationEditTextGroupValidationListener: ValidationEditTextGroupValidationListener){
        this.validationEditTextGroupValidationListener = validationEditTextGroupValidationListener
    }
}