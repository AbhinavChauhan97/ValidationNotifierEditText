package com.abhinav.chouhan.validationnotifieredittext

interface GroupValidationListener {
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

    fun onAnyBecomeValid(validationNotifierEditText: ValidationNotifierEditText)
}