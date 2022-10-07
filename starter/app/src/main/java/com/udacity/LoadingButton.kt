package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }


    init {
        isClickable = true

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = context.getColor(R.color.colorPrimary)
        //val rect = Rect()
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(),paint)
      //  canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 50f, paint)

    }

    }