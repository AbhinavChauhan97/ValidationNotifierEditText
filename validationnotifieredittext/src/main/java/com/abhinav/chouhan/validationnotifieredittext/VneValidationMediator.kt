package com.abhinav.chouhan.validationnotifieredittext

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.util.*

class VneValidationMediator{

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

    companion object{

        private val validationNotifierEditTextList = LinkedList<ValidationNotifierEditText>()

        private var validationEditTextGroupValidationListener: ValidationEditTextGroupValidationListener? = null
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

        fun setOnGroupValidationListener(validationEditTextGroupValidationListener: VneValidationMediator.ValidationEditTextGroupValidationListener,vararg vne:ValidationNotifierEditText){
            this.validationEditTextGroupValidationListener = validationEditTextGroupValidationListener
            vne.forEach {
                validationNotifierEditTextList.add(it)
                it.addValidationChangeListener(listener)
                return@forEach
            }
        }
     }
}