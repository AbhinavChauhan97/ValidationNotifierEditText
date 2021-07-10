package com.abhinav.chouhan.validationnotifieredittext

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.lang.IllegalStateException
import java.util.*

class VneGroup(context: Context, attributeSet: AttributeSet) : View(context,attributeSet) {

    private val listener = object : ValidationNotifierEditText.ValidationChangeListener{

        override fun onBecomeValid(validationNotifierEditText: ValidationNotifierEditText) {
            referencedViews.forEach {
                if(!it.isValid){
                    return
                }
            }
            groupValidationListener?.onAllBecomeValid(referencedViews)
        }

        override fun onBecomeInvalid(validationNotifierEditText: ValidationNotifierEditText) {
            groupValidationListener?.onAnyBecomeInvalid(validationNotifierEditText)
        }
    }

    private var groupValidationListener: GroupValidationListener? = null
    private val referencedViews = LinkedList<ValidationNotifierEditText>()
    private lateinit var stringIds: List<String>

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.VneMediatorGroup, 0, 0)
        val allIdsString = ta.getString(R.styleable.VneMediatorGroup_reference_ids)
        if (allIdsString != null) {
            extractStringIds(allIdsString)
        }
        ta.recycle()
    }

    private fun extractStringIds(allIdsString: String) {
        stringIds  =  allIdsString.split(",")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        extractReferencedViews()
    }

    private fun extractReferencedViews() {
        stringIds.forEach {
            val id = resources.getIdentifier(it, "id", context.packageName)
            if (id == 0) {
                throw IllegalArgumentException("there is no view with id $it")
            }
            if (id == this.id) {
                throw  IllegalStateException("VneMediatorGroup can not have reference id of its own")
            }
            val view = findViewInParent(id)
            if(view !is ValidationNotifierEditText){
                throw IllegalStateException("VneMediator Group can only reference ids of  ValidationNotifierEditText")
            }
            view.addValidationChangeListener(listener)
            referencedViews.add(view)
        }
    }

    private fun findViewInParent(id: Int) = (parent as ViewGroup).findViewById<View>(id)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(0, 0) // this view always have zero size
    }

    override fun onDraw(canvas: Canvas?) {
        // optimize drawing by not calling super since this view has no size
    }


    fun setOnGroupValidationListener(groupValidationListener: GroupValidationListener){
        this.groupValidationListener = groupValidationListener
    }
}