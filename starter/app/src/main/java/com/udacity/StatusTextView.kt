package com.udacity

import android.app.DownloadManager
import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes

class StatusTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {


     var status: Int = 0
        set(value) {
            field = value
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                setTextColor(successColor)
                text = context.getString(R.string.success)
            } else {
                setTextColor(failColor)
                text = context.getString(R.string.fail)

            }
        }

    private var failColor = 0
    private var successColor = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.StatusTextView) {
            failColor = getColor(R.styleable.StatusTextView_failColor, 0)
            successColor = getColor(R.styleable.StatusTextView_successColor, 0)
        }
    }


}