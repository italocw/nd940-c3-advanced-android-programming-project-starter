package com.udacity

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes

class StatusTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr){


    private var failColor = 0
    private var successColor = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.StatusTextView) {
            failColor = getColor(R.styleable.failColor, 0)
            successColor = getColor(R.styleable.successColor, 0)
        }
    }
}