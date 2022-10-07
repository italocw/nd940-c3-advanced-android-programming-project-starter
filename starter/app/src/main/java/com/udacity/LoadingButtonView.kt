package com.udacity

import android.content.Context
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton

private enum class Status(val label: Int) {
    ENABLE(R.string.download),
    LOADING(R.string.loading_button_label_disabled),
   }
